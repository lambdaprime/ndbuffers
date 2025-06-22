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

/**
 * @author lambdaprime intid@protonmail.com
 */
public abstract class NdBufferView extends NdBuffer {

    protected final NSlice nslice;

    protected NdBufferView(NSlice nslice) {
        this(Shape.ofSize(nslice), nslice);
    }

    protected NdBufferView(Shape shape, NSlice nslice) {
        super(shape);
        this.nslice = nslice;
    }

    @Override
    public String toString() {
        return "NdBufferView[shape=%s, nslice=%s]".formatted(shape, nslice);
    }
}
