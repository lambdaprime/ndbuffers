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
import id.ndbuffers.Slice;
import java.nio.DoubleBuffer;

/**
 * Matrix Nx2
 *
 * @author lambdaprime intid@protonmail.com
 */
public class MatrixN2d extends MatrixNd {
    public MatrixN2d(double[] data) {
        super(data.length / 2, 2, data);
    }

    public MatrixN2d(DoubleBuffer data) {
        super(data.capacity() / 2, 2, data);
    }

    public MatrixN2d(Vector2d... vecs) {
        this(new double[vecs.length * 2]);
        var data = duplicate();
        for (var v : vecs) {
            data.put(v.duplicate());
        }
    }

    public MatrixN2d(Slice rows, Slice cols, DoubleNdBuffer data) {
        super(new NSlice(rows, cols), data);
    }

    public Vector2d getVectorView(int row) {
        return new Vector2d(new NSlice(new Slice(row, row + 1, 1), new Slice(0, 2, 1)), data);
    }
}
