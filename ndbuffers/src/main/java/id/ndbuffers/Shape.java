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

import id.ndbuffers.impl.NdIndexIterator;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
    public static Shape ofSize(NSlice slice) {
        return ofSize(slice, 0, slice.slices().length);
    }

    /**
     * Calculate shape described by dimensions between [from, to) inside given N-dimensional slice
     */
    public static Shape ofSize(NSlice nslice, int from, int to) {
        return new Shape(IntStream.range(from, to).map(i -> nslice.slices()[i].size()).toArray());
    }

    /**
     * Calculate actual shape described by the N-dimensional slice
     *
     * <p>For example Slice(4, 8, 2) has size of 2 but its length is 4.
     *
     * @see Slice#length()
     */
    public static Shape ofLength(NSlice slice) {
        return new Shape(Arrays.stream(slice.slices()).mapToInt(Slice::length).toArray());
    }

    public int[] dims() {
        return dims;
    }

    /** Total number of items in all dimensions of the shape */
    public int size() {
        return subsize(0);
    }

    /**
     * Iterate over all dimensions of the shape by generating valid indices in the increasing order
     *
     * @return sequential ordered {@link Stream} which consist of the same instance of the array
     *     which values are updated to represent indices of the current shape
     */
    public Stream<int[]> iterate() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(new NdIndexIterator(this), Spliterator.ORDERED),
                false);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dims);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Shape other) return Arrays.equals(dims, other.dims);
        return false;
    }

    @Override
    public String toString() {
        return "Shape=" + Arrays.toString(dims);
    }

    public Shape subshape(int from, int to) {
        return new Shape(Arrays.copyOfRange(dims, from, to));
    }

    public int[] lastIndex() {
        return Arrays.stream(dims).map(i -> i - 1).toArray();
    }

    public int subsize(int from) {
        return Arrays.stream(dims).reduce((a, b) -> a * b).getAsInt();
    }
}
