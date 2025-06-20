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
public class DoubleNdBufferDirect extends NdBuffer {
    private final DoubleBuffer data;
    private final NdTo1dMapper mapper;

    public DoubleNdBufferDirect(Shape sourceShape, DoubleBuffer data) {
        super(sourceShape);
        this.data = data.duplicate();
        this.data.limit(shape.size());
        this.mapper = new NdTo1dMapper(sourceShape);
    }

    public DoubleNdBufferDirect(Shape sourceShape, double[] data) {
        this(sourceShape, DoubleBuffer.wrap(data));
    }

    @Override
    public double get(int... indices) {
        return data.get(mapper.map(indices));
    }

    @Override
    public void set(double v, int... indices) {
        data.put(mapper.map(indices), v);
    }

    @Override
    public DoubleBuffer duplicate() {
        return data.duplicate();
    }
}
