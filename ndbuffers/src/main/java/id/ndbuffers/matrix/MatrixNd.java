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
package id.ndbuffers.matrix;

import id.ndbuffers.DoubleNdBuffer;
import id.ndbuffers.DoubleNdBufferBase;
import id.ndbuffers.DoubleNdBufferView;
import id.ndbuffers.NSlice;
import id.ndbuffers.Shape;
import id.ndbuffers.Slice;
import java.nio.DoubleBuffer;

/**
 * Matrix NxN
 *
 * @author lambdaprime intid@protonmail.com
 */
public class MatrixNd extends DoubleNdBufferView implements DoubleNdBuffer {

    public MatrixNd(int rows, int cols, double[] data) {
        this(rows, cols, DoubleBuffer.wrap(data));
    }

    public MatrixNd(int rows, int cols, DoubleBuffer data) {
        this(new NSlice(new Slice(0, rows, 1), new Slice(0, cols, 1)), data);
    }

    public MatrixNd(Slice rows, Slice cols, DoubleNdBuffer data) {
        this(new NSlice(rows, cols), data);
    }

    public MatrixNd(Slice rows, Slice cols, DoubleBuffer data) {
        this(new NSlice(rows, cols), data);
    }

    public MatrixNd(NSlice nslice, DoubleBuffer data) {
        this(nslice, new DoubleNdBufferBase(Shape.ofLength(nslice), data));
    }

    public MatrixNd(Slice rows, Slice cols, double[] data) {
        this(rows, cols, DoubleBuffer.wrap(data));
    }

    public MatrixNd(NSlice nslice, DoubleNdBuffer data) {
        super(
                Shape.ofSize(nslice, nslice.slices().length - 2, nslice.slices().length),
                nslice,
                data);
        var slices = nslice.slices();
        for (int i = 0; i < slices.length - 2; i++) {
            if (slices[i].size() > 1)
                throw new IllegalArgumentException(
                        "Matrix requires 2 slices instead of " + shape.dims().length);
        }
    }

    public int getRows() {
        return shape.dims()[0];
    }

    public int getCols() {
        return shape.dims()[1];
    }

    @Override
    public double get(int... indices) {
        return data.get(nslice.map(indices));
    }

    @Override
    public void set(double v, int... indices) {
        data.set(v, nslice.map(indices));
    }

    @Override
    public DoubleBuffer duplicate() {
        return data.duplicate();
    }
}
