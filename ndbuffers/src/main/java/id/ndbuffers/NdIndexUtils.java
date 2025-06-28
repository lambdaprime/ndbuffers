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
package id.ndbuffers;

import java.nio.Buffer;
import java.util.stream.IntStream;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class NdIndexUtils {

    /**
     * Check if items between [from, to) are stored inside continuous block storage of {@link
     * Buffer} to which given ndbuffer points
     */
    public boolean isConsecutive(DoubleNdBuffer ndbuffer, int[] from, int[] to) {
        return ndbuffer.dataBufferIndex(to) - ndbuffer.dataBufferIndex(from)
                == distance(ndbuffer.shape(), from, to);
    }

    public int distance(Shape shape, int[] from, int[] to) {
        return Math.abs(flatten(shape, to) - flatten(shape, from));
    }

    /** Convert N-dimensional index to 1-dimensional */
    public int flatten(Shape shape, int... indices) {
        var prefixProd = new int[shape.dims().length];
        prefixProd[prefixProd.length - 1] = 1;
        for (int i = prefixProd.length - 2; i >= 0; i--) {
            prefixProd[i] = shape.dims()[i + 1] * prefixProd[i + 1];
        }
        var offset = shape.dims().length - indices.length;
        return IntStream.range(0, indices.length)
                .map(i -> prefixProd[i + offset] * indices[i])
                .sum();
    }
}
