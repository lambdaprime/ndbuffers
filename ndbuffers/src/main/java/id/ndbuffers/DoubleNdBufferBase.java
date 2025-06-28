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

import id.ndbuffers.impl.AbstractNdBuffer;
import id.ndbuffers.impl.NdTo1dMapper;
import java.nio.DoubleBuffer;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class DoubleNdBufferBase extends AbstractNdBuffer implements DoubleNdBuffer {
    private final DoubleBuffer data;
    private final NdTo1dMapper mapper;

    public DoubleNdBufferBase(Shape sourceShape, DoubleBuffer data) {
        super(sourceShape);
        this.data = data.duplicate();
        this.data.limit(shape.size());
        this.mapper = new NdTo1dMapper(sourceShape);
    }

    @Override
    public double get(int... indices) {
        return data.get(mapTo1d(indices));
    }

    @Override
    public void set(double v, int... indices) {
        data.put(mapTo1d(indices), v);
    }

    @Override
    public DoubleBuffer duplicate() {
        return data.duplicate();
    }

    private int mapTo1d(int... indices) {
        return mapper.map(indices);
    }
}
