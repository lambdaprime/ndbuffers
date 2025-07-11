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

import id.ndbuffers.matrix.Matrix3d;
import id.ndbuffers.matrix.Matrix4d;
import id.ndbuffers.matrix.MatrixN2d;
import id.ndbuffers.matrix.MatrixN3d;
import id.ndbuffers.matrix.MatrixNd;
import id.ndbuffers.matrix.Vector2d;
import id.ndbuffers.matrix.Vector3d;
import java.nio.Buffer;
import java.nio.DoubleBuffer;

/**
 * See module documentation about difference between base and view ndbuffers.
 *
 * @author lambdaprime intid@protonmail.com
 */
public class NdBuffersFactory {

    /** New 1-dimensional base ndbuffer wrapped around data array */
    public DoubleNdBuffer ndBuffer(double[] data) {
        return ndBuffer(new Shape(data.length), data);
    }

    /** New base ndbuffer pointing to {@link DoubleBuffer} */
    public DoubleNdBuffer ndBuffer(Shape sourceShape, DoubleBuffer data) {
        return new DoubleNdBufferBase(sourceShape, data);
    }

    /** New base ndbuffer wrapped around data array */
    public DoubleNdBuffer ndBuffer(Shape sourceShape, double[] data) {
        return new DoubleNdBufferBase(sourceShape, DoubleBuffer.wrap(data));
    }

    /** New view ndbuffer which will point to a new base ndbuffer wrapped around data array */
    public Matrix4d matrix4d(double[] data) {
        return new Matrix4d(data);
    }

    /** New view ndbuffer pointing to other ndbuffer */
    public Matrix3d matrix3d(Slice rows, Slice cols, DoubleNdBuffer data) {
        return new Matrix3d(rows, cols, data);
    }

    /** New view ndbuffer which will point to a new base ndbuffer */
    public Matrix3d matrix3d() {
        return new Matrix3d();
    }

    /**
     * New view ndbuffer which will point to a new base ndbuffer wrapped around data {@link Buffer}
     */
    public Matrix3d matrix3d(DoubleBuffer data) {
        return new Matrix3d(data);
    }

    /** New view ndbuffer which will point to a new base ndbuffer wrapped around data array */
    public Matrix3d matrix3d(double[] data) {
        return new Matrix3d(data);
    }

    /** New view ndbuffer which will point to a new base ndbuffer wrapped around data array */
    public MatrixN3d matrixN3d(double[] data) {
        return new MatrixN3d(data);
    }

    /** New view ndbuffer which will point to a new base ndbuffer initialized with provided data */
    public MatrixN3d matrixN3d(Vector3d... vecs) {
        return new MatrixN3d(vecs);
    }

    /** New view ndbuffer which will point to a new base ndbuffer */
    public MatrixN3d matrixN3d(int n) {
        return new MatrixN3d(new double[n * 3]);
    }

    /**
     * New view ndbuffer which will point to a new base ndbuffer wrapped around data {@link Buffer}
     */
    public MatrixN3d matrixN3d(DoubleBuffer data) {
        return new MatrixN3d(data);
    }

    /** New view ndbuffer pointing to other ndbuffer */
    public MatrixN3d matrixN3d(Slice rows, Slice cols, DoubleNdBuffer data) {
        return new MatrixN3d(rows, cols, data);
    }

    /** New view ndbuffer pointing to other ndbuffer */
    public MatrixN2d matrixN2d(Slice rows, Slice cols, DoubleNdBuffer data) {
        return new MatrixN2d(rows, cols, data);
    }

    /** New view ndbuffer pointing to other ndbuffer */
    public MatrixNd matrixNd(Slice rows, Slice cols, Matrix3d data) {
        return new MatrixNd(rows, cols, data);
    }

    /** New view ndbuffer which will point to a new base ndbuffer wrapped around data array */
    public MatrixNd matrixNd(Slice rows, Slice cols, double[] data) {
        return new MatrixNd(rows, cols, data);
    }

    /** New view ndbuffer pointing to other ndbuffer */
    public MatrixNd matrixNd(NSlice nslice, DoubleNdBuffer data) {
        return new MatrixNd(nslice, data);
    }

    /** New view ndbuffer pointing to other ndbuffer */
    public Vector3d vector3d(NSlice nslice, DoubleNdBuffer data) {
        return new Vector3d(nslice, data);
    }

    /**
     * New view ndbuffer which will point to a new direct ndbuffer initialized with provided data
     */
    public Vector2d vector2d(double x, double y) {
        return new Vector2d(x, y);
    }
}
