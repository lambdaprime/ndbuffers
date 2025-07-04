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

import id.ndbuffers.NSlice;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class NSliceTest {
    @Test
    public void test_of() {
        Assertions.assertEquals(
                """
NSlice=[Slice[start=0, stop=2147483647, step=1], Slice[start=1, stop=7, step=2], Slice[start=5, stop=2147483647, step=1]]""",
                NSlice.of(":", "1:7:2", "5:").toString());
    }

    @Test
    public void test_map() {
        var nslice = NSlice.of(":", "1:7:2", "5:8");
        Assertions.assertEquals("[0, 1, 6]", Arrays.toString(nslice.map(1)));
        Assertions.assertEquals("[0, 1, 7]", Arrays.toString(nslice.map(2)));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> nslice.map(3));
        Assertions.assertEquals("[0, 3, 6]", Arrays.toString(nslice.map(1, 1)));
        Assertions.assertEquals("[0, 3, 7]", Arrays.toString(nslice.map(1, 2)));
    }
}
