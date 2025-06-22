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

import id.ndbuffers.NSlice;
import id.ndbuffers.Shape;
import java.util.Arrays;

/**
 * Maps N-dimensional shape to continuous set of numbers starting from 0.
 *
 * <p>This mapper is meant to be used with base ndbuffers only and so it does not work with {@link
 * NSlice}
 *
 * @author lambdaprime intid@protonmail.com
 */
public class NdTo1dMapper {
    private final Shape shape;
    private final int[] prefixSizes;

    public NdTo1dMapper(Shape sourceShape) {
        this.shape = sourceShape;
        this.prefixSizes = calcPrefixSizes(shape.dims());
    }

    public int map(int... indices) {
        var index1d = 0;
        indices = filter(indices);
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] >= shape.dims()[i])
                throw new ArrayIndexOutOfBoundsException(
                        "Indexing %s does not match the shape %s"
                                .formatted(Arrays.toString(indices), shape));
            var stride = 1;
            if (i + 1 < indices.length) {
                stride = prefixSizes[i + 1];
            }
            index1d += indices[i] * stride;
        }
        if (index1d >= shape.size()) throw new ArrayIndexOutOfBoundsException(index1d);
        return index1d;
    }

    /**
     * Allow to map views with N dimensions to base ndbuffers with K dimensions when K < N
     *
     * <p>If sourceShape has less dimensions comparing to the number of dimensions specified in the
     * queried indices then we ignore all higher order dimensions and keep only those which are part
     * of the sourceShape. This is allowed only when higher order indices are equal to 0, otherwise
     * we treat it as an error and throw an exception. For example given base buffer of 1D vector
     * "1, 2, 3, 4, 5" we allow to map a 2D matrix (view) into it with a {@link NSlice} which first
     * dimension is "0:1:1", any other slice (ex. "1:2:1") will result in error.
     */
    private int[] filter(int[] indices) {
        if (shape.dims().length == indices.length) return indices;
        var c = 0;
        while (shape.dims().length + c < indices.length) {
            if (indices[c++] != 0) throw new IllegalArgumentException();
        }
        return Arrays.copyOfRange(indices, c, indices.length);
    }

    private static int[] calcPrefixSizes(int[] dims) {
        var prefixSizes = new int[dims.length];
        prefixSizes[prefixSizes.length - 1] = dims[dims.length - 1];
        for (int i = prefixSizes.length - 2; i >= 0; i--) {
            prefixSizes[i] = dims[i] * prefixSizes[i + 1];
        }
        return prefixSizes;
    }
}
