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
package id.ndbuffers.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import id.ndbuffers.NdIndexUtils;
import id.ndbuffers.Shape;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class NdIndexUtilsTest {

    private static final NdIndexUtils indexUtils = new NdIndexUtils();

    record TestCase(Shape shape, int[] from, int[] to) {}

    static Stream<TestCase> dataProvider() {
        return Stream.of(
                new TestCase(new Shape(5, 5, 5), new int[] {3, 4, 4}, new int[] {4, 0, 0}),
                new TestCase(new Shape(5, 5, 5), new int[] {3, 4, 4}, new int[] {3, 4, 4}),
                new TestCase(new Shape(5, 5, 5), new int[] {3, 4, 4}, new int[] {4, 4, 0}),
                new TestCase(new Shape(5, 5, 5, 5), new int[] {0, 3, 4, 4}, new int[] {0, 4, 4, 0}),
                new TestCase(new Shape(5, 5, 5), new int[] {3, 2, 1}, new int[] {4, 0, 0}),
                new TestCase(new Shape(3, 4, 5), new int[] {1, 2, 3}, new int[] {2, 0, 0}));
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void test(TestCase tc) {
        assertEquals(
                distanceBruteForce(tc.shape, tc.from, tc.to),
                indexUtils.distance(tc.shape, tc.from, tc.to));
    }

    private int distanceBruteForce(Shape shape, int[] from, int[] to) {
        return distanceBruteForce(shape, 0, Arrays.copyOf(from, from.length), to, false) - 1;
    }

    private int distanceBruteForce(Shape shape, int dim, int[] from, int[] to, boolean roll) {
        if (dim >= shape.dims().length) {
            System.out.println(Arrays.toString(from));
            return 1;
        }
        var sum = 0;
        var max = roll ? shape.dims()[dim] : to[dim] + 1;
        while (from[dim] < max) {
            sum += distanceBruteForce(shape, dim + 1, from, to, roll ? true : from[dim] != to[dim]);
            from[dim]++;
        }
        if (from[dim] >= shape.dims()[dim]) from[dim] = 0;
        return sum;
    }
}
