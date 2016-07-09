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

package org.lyeung.elwood.web.controller.buildresult;

import com.github.lyeung.common.test.SlowTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.web.controller.buildresult.command.GetBuildResultCommand;
import org.lyeung.elwood.web.controller.runbuild.KeyTuple;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 6/10/2015.
 */
@Category(value = SlowTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildResultControllerTest {

    private static final String KEY = "ELWP";

    @InjectMocks
    private BuildResultController controller;

    @Mock
    private GetBuildResultCommand command;

    @Mock
    private GetBuildResultsResponse response;

    @Test
    public void testGetBuildResult() {
        when(command.execute(new KeyTuple(KEY))).thenReturn(response);
        final GetBuildResultsResponse result = controller.getBuildResult("ELWP");

        assertEquals(result, response);

        verify(command).execute(eq(new KeyTuple(KEY)));
        verifyNoMoreInteractions(command);
    }
}