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
package id.ndbuffers.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import id.ndbuffers.Shape;
import id.ndbuffers.impl.NdTo1dMapper;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class NdTo1dMapperTest {

    @Test
    public void test() {
        var mapper1 = new NdTo1dMapper(new Shape(17));
        assertArrayEquals(
                new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16},
                IntStream.range(0, 17).map(mapper1::map).toArray());

        // indices:
        // 0 1  2
        // 3 4  5
        // 6 7  8
        // 9 10 11
        var mapper2 = new NdTo1dMapper(new Shape(4, 3));
        assertEquals(0, mapper2.map(0, 0));
        assertEquals(3, mapper2.map(1, 0));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> mapper2.map(4, 1));

        var shape = new Shape(3, 3);
        var mapper3 = new NdTo1dMapper(shape);
        assertArrayEquals(
                new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8},
                shape.iterate().mapToInt(id -> mapper3.map(id)).toArray());
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> mapper3.map(0, 3));
    }
}
