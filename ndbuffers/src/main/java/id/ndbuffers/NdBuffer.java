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

import java.text.DecimalFormat;

/**
 * @author lambdaprime intid@protonmail.com
 */
public abstract class NdBuffer {
    public static final DecimalFormat formatter = new DecimalFormat();

    static {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
    }

    protected NSlice nslice;
    protected Shape shape;

    protected NdBuffer(Shape sourceShape, NSlice nslice) {
        this.nslice = nslice;
        this.shape = Shape.of(nslice);
    }

    public Shape shape() {
        return shape;
    }

    @Override
    public String toString() {
        return "NdBuffer[shape=%s, nslice=%s]".formatted(shape, nslice);
    }
}
