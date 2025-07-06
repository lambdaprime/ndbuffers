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

import id.ndbuffers.DoubleNdBuffer;
import id.ndbuffers.NdBuffersFactory;
import id.ndbuffers.NdBuffersJsonUtils;
import id.ndbuffers.Shape;
import id.ndbuffers.Slice;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class NdBuffersCopyToTest {
    private static final NdBuffersFactory ndFactory = new NdBuffersFactory();
    private static final NdBuffersJsonUtils jsonUtils = new NdBuffersJsonUtils();

    record TestCase(DoubleNdBuffer from, DoubleNdBuffer to, int[] at, String expected) {}

    static Stream<TestCase> dataProvider() {
        return Stream.of(
                new TestCase(
                        ndFactory.ndBuffer(new Shape(3), new double[] {10, 11, 12}),
                        ndFactory.ndBuffer(
                                new Shape(10), new double[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
                        new int[] {5},
                        """
                        { "data" : [
                         [0, 1, 2, 3, 4, 10, 11, 12, 8, 9]
                        ] }"""),
                new TestCase(
                        ndFactory.ndBuffer(
                                new Shape(3, 3),
                                new double[] {
                                    1, 2, 3,
                                    4, 5, 6,
                                    7, 8, 9
                                }),
                        ndFactory.ndBuffer(
                                new Shape(3, 3),
                                new double[] {
                                    10, 20, 30,
                                    40, 50, 60,
                                    70, 80, 90
                                }),
                        new int[] {0, 0},
                        """
                        { "data" : [
                         [1, 2, 3],
                         [4, 5, 6],
                         [7, 8, 9]
                        ] }"""),
                new TestCase(
                        ndFactory.ndBuffer(
                                new Shape(2, 3),
                                new double[] {
                                    1, 2, 3,
                                    4, 5, 6
                                }),
                        ndFactory.ndBuffer(
                                new Shape(4, 3),
                                new double[] {
                                    10, 20, 30,
                                    40, 50, 60,
                                    70, 80, 90,
                                    100, 120, 130
                                }),
                        new int[] {0, 0},
                        """
                        { "data" : [
                         [1, 2, 3],
                         [4, 5, 6],
                         [70, 80, 90],
                         [100, 120, 130]
                        ] }"""),
                new TestCase(
                        ndFactory.ndBuffer(
                                new Shape(2, 3),
                                new double[] {
                                    1, 2, 3,
                                    4, 5, 6
                                }),
                        ndFactory.ndBuffer(
                                new Shape(4, 3),
                                new double[] {
                                    10, 20, 30,
                                    40, 50, 60,
                                    70, 80, 90,
                                    100, 120, 130
                                }),
                        new int[] {1, 0},
                        """
                        { "data" : [
                         [10, 20, 30],
                         [1, 2, 3],
                         [4, 5, 6],
                         [100, 120, 130]
                        ] }"""),
                new TestCase(
                        ndFactory.ndBuffer(
                                new Shape(2, 3),
                                new double[] {
                                    1, 2, 3,
                                    4, 5, 6
                                }),
                        ndFactory.ndBuffer(
                                new Shape(4, 3),
                                new double[] {
                                    10, 20, 30,
                                    40, 50, 60,
                                    70, 80, 90,
                                    100, 120, 130
                                }),
                        new int[] {2, 0},
                        """
                        { "data" : [
                         [10, 20, 30],
                         [40, 50, 60],
                         [1, 2, 3],
                         [4, 5, 6]
                        ] }"""),
                new TestCase(
                        ndFactory.ndBuffer(
                                new Shape(2, 3),
                                new double[] {
                                    1, 2, 3,
                                    4, 5, 6,
                                    7, 8, 9
                                }),
                        ndFactory.ndBuffer(
                                new Shape(4, 5),
                                new double[] {
                                    10, 11, 12, 13, 14,
                                    20, 21, 22, 23, 24,
                                    30, 31, 32, 33, 34,
                                    40, 41, 42, 43, 44
                                }),
                        new int[] {0, 0},
                        """
                        { "data" : [
                         [1, 2, 3, 13, 14],
                         [4, 5, 6, 23, 24],
                         [30, 31, 32, 33, 34],
                         [40, 41, 42, 43, 44]
                        ] }"""),
                new TestCase(
                        ndFactory.ndBuffer(
                                new Shape(3, 3),
                                new double[] {
                                    1, 2, 3,
                                    4, 5, 6,
                                    7, 8, 9
                                }),
                        ndFactory.ndBuffer(
                                new Shape(4, 5),
                                new double[] {
                                    10, 11, 12, 13, 14,
                                    20, 21, 22, 23, 24,
                                    30, 31, 32, 33, 34,
                                    40, 41, 42, 43, 44
                                }),
                        new int[] {1, 1},
                        """
                        { "data" : [
                         [10, 11, 12, 13, 14],
                         [20, 1, 2, 3, 24],
                         [30, 4, 5, 6, 34],
                         [40, 7, 8, 9, 44]
                        ] }"""),
                new TestCase(
                        ndFactory.ndBuffer(new Shape(3, 1), new double[] {1, 2, 3}),
                        ndFactory.matrix4d(
                                new double[] {
                                    10, 11, 12, 13,
                                    20, 21, 22, 23,
                                    30, 31, 32, 33,
                                    40, 41, 42, 43,
                                }),
                        new int[] {0, 3},
                        """
                        { "data" : [
                         [10, 11, 12, 1],
                         [20, 21, 22, 2],
                         [30, 31, 32, 3],
                         [40, 41, 42, 43]
                        ] }"""),
                new TestCase(
                        ndFactory.matrix3d(
                                new double[] {
                                    1, 2, 3,
                                    4, 5, 6,
                                    7, 8, 9
                                }),
                        ndFactory.ndBuffer(
                                new Shape(4, 5),
                                new double[] {
                                    10, 11, 12, 13, 14,
                                    20, 21, 22, 23, 24,
                                    30, 31, 32, 33, 34,
                                    40, 41, 42, 43, 44
                                }),
                        new int[] {1, 1},
                        """
                        { "data" : [
                         [10, 11, 12, 13, 14],
                         [20, 1, 2, 3, 24],
                         [30, 4, 5, 6, 34],
                         [40, 7, 8, 9, 44]
                        ] }"""),
                // Test copy from the view over the base ndbuffer with a larger size than the copyTo
                // destination
                // Copy at the beginning of the destination ndbuffer.
                new TestCase(
                        ndFactory.matrix3d(
                                new Slice(2, Slice.MAX_INDEX, 1),
                                new Slice(0, 3, 1),
                                ndFactory.matrixN3d(
                                        new double[] {
                                            10, 11, 12,
                                            20, 21, 22,
                                            30, 31, 32,
                                            40, 41, 42,
                                            50, 51, 52,
                                            60, 61, 62
                                        })),
                        ndFactory.matrix3d(),
                        new int[] {0, 0},
                        """
                        { "data" : [
                         [30, 31, 32],
                         [40, 41, 42],
                         [50, 51, 52]
                        ] }"""),
                // Test copy from the view over the base ndbuffer with a larger size than the copyTo
                // destination
                // Copy at random index inside destination ndbuffer.
                new TestCase(
                        ndFactory.matrix3d(
                                new Slice(2, Slice.MAX_INDEX, 1),
                                new Slice(0, 3, 1),
                                ndFactory.matrixN3d(
                                        new double[] {
                                            10, 11, 12,
                                            20, 21, 22,
                                            30, 31, 32,
                                            40, 41, 42,
                                            50, 51, 52,
                                            60, 61, 62
                                        })),
                        ndFactory.matrixN3d(5),
                        new int[] {2, 0},
                        """
                        { "data" : [
                         [0, 0, 0],
                         [0, 0, 0],
                         [30, 31, 32],
                         [40, 41, 42],
                         [50, 51, 52]
                        ] }"""));
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void test(TestCase tc) {
        tc.from.copyTo(tc.to, tc.at);
        assertEquals(tc.expected, jsonUtils.dumpAsJson(tc.to));
    }
}
