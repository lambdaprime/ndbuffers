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

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @param dims dimension sizes
 * @author lambdaprime intid@protonmail.com
 */
public record Shape(int... dims) {

    public Shape {
        if (Arrays.stream(dims).filter(i -> i == 0).findAny().isPresent())
            throw new IllegalArgumentException("0 size dimension");
    }

    /** Calculate shape described by N-dimensional slice */
    public static Shape of(NSlice slice) {
        return new Shape(Arrays.stream(slice.slices()).mapToInt(Slice::size).toArray());
    }

    public int[] dims() {
        return dims;
    }

    /** Total number of items in all dimensions of the shape */
    public int size() {
        return Arrays.stream(dims).reduce((a, b) -> a * b).getAsInt();
    }

    /**
     * Iterate over all dimensions of the shape by generating valid indices in the increasing order
     */
    public Stream<int[]> iterate() {
        var max = Arrays.stream(dims).map(i -> i - 1).toArray();
        var stream =
                Stream.iterate(
                        new int[dims.length],
                        indices -> !Arrays.equals(indices, max),
                        indices -> {
                            for (int i = indices.length - 1; i >= 0; i--) {
                                if (indices[i] < dims[i] - 1) {
                                    indices[i]++;
                                    break;
                                } else {
                                    indices[i] = 0;
                                }
                            }
                            return indices;
                        });
        return Stream.concat(stream, Stream.of(max));
    }

    @Override
    public String toString() {
        return "Shape=" + Arrays.toString(dims);
    }
}
