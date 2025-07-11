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
import id.ndbuffers.impl.AbstractNdBuffer;
import java.nio.DoubleBuffer;

public class Vector2d extends MatrixNd {

    public Vector2d(double x, double y) {
        this(DoubleBuffer.wrap(new double[] {x, y}));
    }

    public Vector2d(DoubleBuffer data) {
        super(1, 2, data);
    }

    public Vector2d(NSlice nslice, DoubleNdBuffer data) {
        super(nslice.trim(new Shape(1, 2)), data);
    }

    public double getX() {
        return get(0, 0);
    }

    public double getY() {
        return get(0, 1);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(getX() - other.getX(), getY() - other.getY());
    }

    public double distance(Vector2d other) {
        return Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(other.getX() + getX(), other.getY() + getY());
    }

    @Override
    public String toString() {
        return """
               { "x": %s, "y": %s }"""
                .formatted(
                        AbstractNdBuffer.formatter.format(getX()),
                        AbstractNdBuffer.formatter.format(getY()));
    }
}
