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

import java.nio.DoubleBuffer;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class DoubleNdBufferView extends NdBuffer implements DoubleNdBuffer {
    private DoubleNdBuffer data;

    public DoubleNdBufferView(NSlice nslice, DoubleNdBuffer data) {
        super(nslice);
        this.data = data;
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
