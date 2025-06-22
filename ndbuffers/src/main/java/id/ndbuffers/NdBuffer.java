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

import id.ndbuffers.NdIndexIterator.DimensionChangeListener;
import id.ndbuffers.matrix.MatrixNd;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author lambdaprime intid@protonmail.com
 */
public abstract class NdBuffer implements DoubleNdBuffer {
    public static final DecimalFormat formatter = new DecimalFormat();

    static {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
    }

    protected final Shape shape;

    protected NdBuffer(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Shape shape() {
        return shape;
    }

    @Override
    public String dumpAsJson() {
        var numOfDims = shape.dims().length;
        if (numOfDims == 1) {
            return new MatrixNd(
                            new NSlice(new Slice(0, 1, 1), new Slice(0, shape.dims()[0], 1)), this)
                    .dumpAsJson();
        } else if (numOfDims == 2) {
            return new MatrixNd(
                            new NSlice(
                                    new Slice(0, shape.dims()[0], 1),
                                    new Slice(0, shape.dims()[1], 1)),
                            this)
                    .dumpAsJson();
        }
        var l = new ArrayList<String>();
        var iter =
                new NdIndexIterator(
                        shape.subshape(0, numOfDims - 2),
                        new DimensionChangeListener() {
                            @Override
                            public void onStart() {
                                l.add("[");
                            }

                            @Override
                            public void onEnd() {
                                l.add("]");
                            }
                        });
        var last2dims = new int[] {shape.dims()[numOfDims - 2], shape.dims()[numOfDims - 1]};
        Stream.generate(() -> "[").limit(numOfDims - 2).forEach(l::add);
        while (iter.hasNext()) {
            var nslice =
                    new NSlice(
                            Stream.concat(
                                            Arrays.stream(iter.next())
                                                    .mapToObj(i -> new Slice(i, i + 1, 1)),
                                            Stream.of(
                                                    new Slice(0, last2dims[0], 1),
                                                    new Slice(0, last2dims[1], 1)))
                                    .toArray(i -> new Slice[i]));
            l.add("[");
            new MatrixNd(nslice, this).dumpAsString().lines().forEach(l::add);
            l.add("]");
        }
        Stream.generate(() -> "]").limit(numOfDims - 2).forEach(l::add);
        return "{ \"data\" : %s }".formatted(prettyPrint(l));
    }

    @Override
    public String toString() {
        return "NdBuffer[shape=%s]".formatted(shape);
    }

    private String prettyPrint(List<String> lines) {
        if (lines.isEmpty()) return "";
        var len = 0;
        var buf = new StringBuilder();
        var iter = lines.iterator();
        var prevLine = iter.next();
        while (iter.hasNext()) {
            var line = iter.next();
            if (prevLine.equals("[")) len++;
            buf.append(" ".repeat(len)).append(prevLine);
            if (line.equals("[") && prevLine.equals("]")) {
                buf.append(',');
            }
            buf.append('\n');
            if (prevLine.equals("]")) len--;
            prevLine = line;
        }
        buf.append(" ".repeat(len)).append(prevLine);
        return buf.toString().trim();
    }
}
