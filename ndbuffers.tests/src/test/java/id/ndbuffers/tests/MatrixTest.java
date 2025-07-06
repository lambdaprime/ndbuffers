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

import id.ndbuffers.NdBuffersFactory;
import id.ndbuffers.NdBuffersJsonUtils;
import id.ndbuffers.matrix.Vector3d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class MatrixTest {
    private static final NdBuffersFactory ndFactory = new NdBuffersFactory();
    private static final NdBuffersJsonUtils jsonUtils = new NdBuffersJsonUtils();

    @Test
    public void test_of() {
        Assertions.assertEquals(
                """
{ "data" : [
 [1, 2, 3],
 [4, 5, 6]
] }""",
                jsonUtils.dumpAsJson(
                        ndFactory.matrixN3d(new Vector3d(1, 2, 3), new Vector3d(4, 5, 6))));
    }
}
