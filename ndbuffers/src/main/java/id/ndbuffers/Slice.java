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

import java.util.stream.IntStream;

/**
 * @param start index of the first item. Default is 0.
 * @param stop index of the last item of the slice. Default is {@link Slice#MAX_INDEX} which means
 *     slicing continues up to the last item inside current dimension.
 * @param step step size between consecutive items of the slice. Default is 1.
 * @author lambdaprime intid@protonmail.com
 */
public record Slice(int start, int stop, int step) {

    public static final int MAX_INDEX = Integer.MAX_VALUE;

    public Slice {
        if (stop < start) throw new IllegalArgumentException("'stop' cannot be less then 'start'");
    }

    /**
     * @see <a
     *     href="https://numpy.org/doc/stable/user/basics.indexing.html#slicing-and-striding">Slicing
     *     and striding in NumPy</a>
     */
    public static Slice of(String expr) {
        if (expr.indexOf(':') < 0) throw new IllegalArgumentException("Slice expression is empty");
        var start = 0;
        var stop = MAX_INDEX;
        var step = 1;
        var tokens = expr.split(":");
        if (tokens.length != 0) {
            if (tokens.length >= 1)
                start = tokens[0].isBlank() ? start : Integer.parseInt(tokens[0]);
            if (tokens.length >= 2)
                stop = tokens[1].isBlank() ? start : Integer.parseInt(tokens[1]);
            if (tokens.length == 3)
                step = tokens[2].isBlank() ? start : Integer.parseInt(tokens[2]);
            if (tokens.length > 3)
                new IllegalArgumentException("Not valid slice expression: " + expr);
        }
        return new Slice(start, stop, step);
    }

    /** Iterate over all indices of the slice */
    public IntStream iterate() {
        var count = new int[1];
        count[0] = start;
        return IntStream.iterate(start, pos -> pos < stop, pos -> pos + step);
    }

    /** Index of ith item of the slice */
    public int index(int i) {
        if (i < 0) throw new ArrayIndexOutOfBoundsException("Index cannot be negative");
        var res = start + i * step;
        if (res >= stop)
            throw new ArrayIndexOutOfBoundsException("stop=%d index=%d".formatted(stop, res));
        return res;
    }

    /** Total number of items */
    public int size() {
        var len = stop - start;
        var size = len / step;
        if (len % step != 0) size++;
        return size;
    }

    public Object trimStop(int maxStop) {
        return stop <= maxStop ? this : new Slice(start, maxStop, step);
    }
}
