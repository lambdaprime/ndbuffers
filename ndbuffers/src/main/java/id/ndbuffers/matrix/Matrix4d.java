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

import id.ndbuffers.NSlice;
import id.ndbuffers.Shape;
import id.ndbuffers.Slice;
import java.nio.DoubleBuffer;

/**
 * Matrix 4x4
 *
 * @author lambdaprime intid@protonmail.com
 */
public class Matrix4d extends MatrixNd {
    private static final Shape SHAPE = new Shape(4, 4);

    public Matrix4d(double[] data) {
        super(4, 4, data);
    }

    public Matrix4d(DoubleBuffer data) {
        super(4, 4, data);
    }

    public Matrix4d(Slice rows, Slice cols, DoubleBuffer data) {
        super(new NSlice(rows, cols).trim(SHAPE), data);
    }
}
