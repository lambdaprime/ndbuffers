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
import static org.junit.jupiter.api.Assertions.assertThrows;

import id.ndbuffers.DoubleNdBufferDirect;
import id.ndbuffers.NSlice;
import id.ndbuffers.Shape;
import id.ndbuffers.Slice;
import id.ndbuffers.matrix.Matrix3d;
import id.ndbuffers.matrix.Matrix4d;
import id.ndbuffers.matrix.MatrixNd;
import id.ndbuffers.matrix.Vector3d;
import org.junit.jupiter.api.Test;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class NdBuffersExamplesTest {

    @Test
    public void test() {
        var oneDimensionalArray =
                new double[] {
                    1, 2, 3, 4,
                    5, 6, 7, 8,
                    9, 10, 11, 12,
                    13, 14, 15, 16
                };
        var mx4d = new Matrix4d(oneDimensionalArray);
        assertEquals(8, mx4d.get(1, 3));
        mx4d.set(-1, 1, 3);
        assertEquals(-1, oneDimensionalArray[7]);

        assertEquals(
                """
                { "data" : [
                 [1, 2, 3, 4],
                 [5, 6, 7, -1],
                 [9, 10, 11, 12],
                 [13, 14, 15, 16]
                ] }""",
                mx4d.dumpAsJson());
        var mx3d = new Matrix3d(Slice.of("1:4"), Slice.of("1:4"), mx4d);
        assertEquals(
                """
                { "data" : [
                 [6, 7, -1],
                 [10, 11, 12],
                 [14, 15, 16]
                ] }""",
                mx3d.dumpAsJson());
        var mx2d = new MatrixNd(Slice.of("0:3:2"), Slice.of("0:3:2"), mx3d);
        assertEquals(
                """
                { "data" : [
                 [6, -1],
                 [14, 16]
                ] }""",
                mx2d.dumpAsJson());
        mx2d.set(-16, 1, 1);
        assertEquals(
                """
                { "data" : [
                 [1, 2, 3, 4],
                 [5, 6, 7, -1],
                 [9, 10, 11, 12],
                 [13, 14, 15, -16]
                ] }""",
                mx4d.dumpAsJson());
        var vector3d = new Vector3d(NSlice.of("1:", ":"), mx3d);
        assertEquals(
                """
                { "data" : [
                 [10, 11, 12]
                ] }""",
                vector3d.dumpAsJson());

        var mxNd =
                new MatrixNd(
                        new Slice(0, 4, 2),
                        new Slice(0, 3, 1),
                        new double[] {
                            0, 1, 2,
                            3, 4, 5,
                            6, 7, 8,
                            9, 10, 11
                        });
        assertEquals(0, mxNd.get(0, 0));
        assertEquals(6, mxNd.get(1, 0));
    }

    @Test
    public void test_index_over_missing_dims() {
        var vector3d = new DoubleNdBufferDirect(new Shape(5), new double[] {1, 2, 3, 4, 5});
        var mx1 = new MatrixNd(NSlice.of("0:1:1", "0:3:1"), vector3d);
        assertEquals(1, mx1.get(0, 0));

        var mx2 = new MatrixNd(NSlice.of("1:2:1", "0:3:1"), vector3d);
        assertThrows(IllegalArgumentException.class, () -> mx2.get(0, 0));
    }
}
