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

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class NdIndexIterator implements Iterator<int[]> {

    private final DimensionChangeListener dimensionListener;
    private final int[] lastNdIndex;
    private final int[] currentNdIndex;
    private final int[] dims;

    public static interface DimensionChangeListener {
        void onStart();

        void onEnd();
    }

    private static final DimensionChangeListener NOOP_LISTENER =
            new DimensionChangeListener() {
                @Override
                public void onStart() {}

                @Override
                public void onEnd() {}
            };

    public NdIndexIterator(Shape shape) {
        this(shape, NOOP_LISTENER);
    }

    public NdIndexIterator(Shape shape, DimensionChangeListener dimensionListener) {
        this.dimensionListener = dimensionListener;
        this.dims = shape.dims();
        this.currentNdIndex = new int[dims.length];
        this.lastNdIndex = Arrays.stream(dims).map(i -> i - 1).toArray();
        currentNdIndex[currentNdIndex.length - 1]--;
    }

    @Override
    public boolean hasNext() {
        return !Arrays.equals(currentNdIndex, lastNdIndex);
    }

    @Override
    public int[] next() {
        for (int i = currentNdIndex.length - 1; i >= 0; i--) {
            if (currentNdIndex[i] < dims[i] - 1) {
                currentNdIndex[i]++;
                break;
            } else {
                dimensionListener.onEnd();
                currentNdIndex[i] = 0;
                dimensionListener.onStart();
            }
        }
        return currentNdIndex;
    }
}
