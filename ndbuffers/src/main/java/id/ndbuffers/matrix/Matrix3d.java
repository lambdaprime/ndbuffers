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
import id.ndbuffers.NSlice;
import id.ndbuffers.Shape;
import id.ndbuffers.Slice;
import java.nio.DoubleBuffer;

/**
 * Matrix 3x3
 *
 * @author lambdaprime intid@protonmail.com
 */
public class Matrix3d extends MatrixN3d {
    private static final Shape SHAPE = new Shape(3, 3);

    public Matrix3d() {
        this(new double[SHAPE.size()]);
    }

    public Matrix3d(double[] data) {
        super(3, 3, DoubleBuffer.wrap(data));
    }

    public Matrix3d(DoubleBuffer data) {
        super(3, 3, data);
    }

    public Matrix3d(Slice rows, Slice cols, DoubleBuffer data) {
        super(rows, cols, data);
    }

    public Matrix3d(Slice rows, Slice cols, DoubleNdBuffer data) {
        super(new NSlice(rows, cols).trim(SHAPE), data);
        if (getRows() != 3)
            throw new IllegalArgumentException("Number of rows mismatch 3 !=" + rows.size());
        if (getCols() != 3)
            throw new IllegalArgumentException("Number of cols mismatch 3 !=" + cols.size());
    }
}
