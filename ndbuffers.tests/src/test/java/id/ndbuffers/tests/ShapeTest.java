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

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

import id.ndbuffers.NSlice;
import id.ndbuffers.Shape;
import id.ndbuffers.Slice;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class ShapeTest {
    @Test
    public void test_iterate() {
        assertEquals("[0]", new Shape(1).iterate().map(Arrays::toString).collect(joining("\n")));
        assertEquals(
                """
                [0, 0, 0]
                [0, 0, 1]
                [0, 0, 2]
                [0, 1, 0]
                [0, 1, 1]
                [0, 1, 2]""",
                new Shape(1, 2, 3).iterate().map(Arrays::toString).collect(joining("\n")));
    }

    @Test
    public void test_ofSize() {
        assertEquals(
                "Shape=[2, 1]",
                Shape.ofSize(new NSlice(new Slice(0, 4, 2), new Slice(0, 3, 3))).toString());
        assertEquals(
                "Shape=[2, 2]",
                Shape.ofSize(new NSlice(new Slice(0, 3, 2), new Slice(0, 3, 2))).toString());
    }

    @Test
    public void test_ofLength() {
        assertEquals("Shape=[4]", Shape.ofLength(new NSlice(new Slice(4, 8, 2))).toString());
    }
}
