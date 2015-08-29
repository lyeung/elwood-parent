/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.common.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.command.MkDirCommandException;
import org.lyeung.elwood.common.command.MkDirCommandParam;
import org.lyeung.elwood.common.command.MkDirCommandParamBuilder;
import org.lyeung.elwood.common.test.SlowTest;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 19/07/2015.
 */
@Category(SlowTest.class)
public class MkDirCommandImplTest {

    private static final String LOCAL_DIR = "target/mk-dir-command/subdir1";

    @Before
    public void setUp() throws Exception {
        final File directory = new File(LOCAL_DIR);
        if (directory.exists()) {
            FileUtils.forceDelete(directory);
        }
    }

    @Test
    public void testExecute() throws Exception {
        final MkDirCommandParam param = new MkDirCommandParamBuilder().directory(LOCAL_DIR).build();
        final File directory = new MkDirCommandImpl().execute(param);
        assertTrue(directory.isDirectory());
    }

    @Test(expected = MkDirCommandException.class)
    public void testExecuteExistingDirectory() {
        final MkDirCommandParam param = new MkDirCommandParamBuilder().directory(LOCAL_DIR).build();
        new MkDirCommandImpl().execute(param);
        new MkDirCommandImpl().execute(param);
    }
}