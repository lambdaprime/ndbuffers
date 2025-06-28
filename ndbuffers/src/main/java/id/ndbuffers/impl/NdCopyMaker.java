/*
 * Copyright 2025 ndbuffers
 * 
 * Website: https://github.com/lambdaprime/ndbuffers
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package id.ndbuffers.impl;

import id.ndbuffers.DoubleNdBuffer;
import id.ndbuffers.NdIndexUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Copy implementation which prioritizes bulk copy ({@link ByteBuffer#put(ByteBuffer)}) when it is
 * possible instead of copying one item at a time. Bulk copy is optional operation and when it is
 * supported by the system it gives better performance.
 *
 * @author lambdaprime intid@protonmail.com
 */
public class NdCopyMaker {
    private final NdIndexUtils indexUtils = new NdIndexUtils();

    public void copy(DoubleNdBuffer from, int[] fromAt, DoubleNdBuffer to, int[] toAt) {
        if (!indexUtils.isConsecutive(from, fromAt, from.shape().lastIndex()))
            throw new UnsupportedOperationException();
        var fromLen = from.shape().dims().length;
        var destinationDim = to.shape().dims().length - fromLen;
        if (destinationDim < 0) throw new IllegalArgumentException();
        copyToInternal(from, 0, fromAt, to, destinationDim, toAt);
    }

    private void copyToInternal(
            DoubleNdBuffer src,
            int srcDim,
            int[] srcStart,
            DoubleNdBuffer dst,
            int dstDim,
            int[] dstStart) {
        var sdims = src.shape().dims();
        var dstEnd =
                IntStream.range(0, dstStart.length)
                        .map(i -> dstStart[i] + (i < dstDim ? 0 : sdims[srcDim + i - dstDim] - 1))
                        .toArray();
        if (indexUtils.isConsecutive(dst, dstStart, dstEnd)) {
            if (srcDim == sdims.length - 1) {
                copyBulk(src, srcStart, sdims[srcDim], dst, dstStart);
                return;
            }
            var ddims = dst.shape().dims();
            if (arrayEquals(sdims, srcDim + 1, ddims, dstDim + 1)) {
                if (src.shape().dims()[srcDim] > ddims[dstDim])
                    throw new RuntimeException(
                            "Shape of source NdBuffer should be less or equal to shape of its"
                                    + " destination");
                copyBulk(src, srcStart, -1, dst, dstStart);
                return;
            }
            var newSrcStart = Arrays.copyOf(srcStart, srcStart.length);
            var newDstStart = Arrays.copyOf(dstStart, dstStart.length);
            while (newSrcStart[srcDim] < sdims[srcDim]) {
                copyToInternal(src, srcDim + 1, newSrcStart, dst, dstDim + 1, newDstStart);
                newSrcStart[srcDim]++;
                newDstStart[dstDim]++;
            }
        } else {
            throw new RuntimeException();
        }
    }

    private boolean arrayEquals(int[] a, int fromA, int[] b, int fromB) {
        return IntStream.range(0, a.length - fromA)
                .filter(i -> a[fromA + i] != b[fromB + i])
                .findAny()
                .isEmpty();
    }

    private void copyBulk(
            DoubleNdBuffer src, int[] srcStart, int srcLength, DoubleNdBuffer dst, int[] dstStart) {
        var dstBuf = dst.duplicate();
        var srcBuf = src.duplicate();
        srcBuf.position(src.dataBufferIndex(srcStart));
        dstBuf.position(dst.dataBufferIndex(dstStart));
        if (srcLength >= 0) srcBuf.limit(srcBuf.position() + srcLength);
        dstBuf.put(srcBuf);
    }
}
