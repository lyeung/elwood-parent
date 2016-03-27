/*
 *
 *  Copyright (C) 2015-2016 the original author or authors.
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

package org.lyeung.elwood.web.controller.build;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.web.controller.ValidationException;
import org.lyeung.elwood.web.controller.build.command.DeleteBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.GetBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.SaveBuildJobCommand;
import org.lyeung.elwood.web.model.BuildJob;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 26/10/2015.
 */
@Category(value = SlowTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildJobControllerTest {

    private static final String KEY = "key";

    @InjectMocks
    private BuildJobController buildJobController;

    @Mock
    private GetBuildJobCommand getBuildJobCommand;

    @Mock
    private SaveBuildJobCommand saveBuildJobCommand;

    @Mock
    private DeleteBuildJobCommand deleteBuildJobCommand;

    @Test
    public void testGetByKey() {
        final BuildJob buildJob = new BuildJob();
        when(getBuildJobCommand.execute(KEY)).thenReturn(buildJob);

        final ResponseEntity<BuildJob> response = buildJobController.getByKey(KEY);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(buildJob, response.getBody());

        verify(getBuildJobCommand).execute(eq(KEY));
        verifyNoMoreInteractions(getBuildJobCommand);
        verifyZeroInteractions(saveBuildJobCommand);
        verifyZeroInteractions(deleteBuildJobCommand);
    }

    @Test
    public void testSaveBuildJob() {
        final BuildJob buildJob = new BuildJob();
        final BindingResult bindingResult = new BeanPropertyBindingResult(buildJob, "buildJob");

        final BuildJob updatedBuildJob = new BuildJob();
        when(saveBuildJobCommand.execute(buildJob)).thenReturn(updatedBuildJob);

        final ResponseEntity<BuildJob> response = buildJobController.saveBuildJob(
                buildJob, bindingResult);
        assertNotNull(response);
        assertEquals(updatedBuildJob, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(saveBuildJobCommand).execute(buildJob);
        verifyNoMoreInteractions(getBuildJobCommand);
        verifyZeroInteractions(saveBuildJobCommand);
        verifyZeroInteractions(deleteBuildJobCommand);
    }

    @Test
    public void testSaveBuildJobWithErrors() {
        final BuildJob buildJob = new BuildJob();
        final BindingResult bindingResult = new BeanPropertyBindingResult(buildJob, "buildJob");
        bindingResult.addError(new ObjectError("buildJob", "mocked error"));

        final BuildJob updatedBuildJob = new BuildJob();
        when(saveBuildJobCommand.execute(buildJob)).thenReturn(updatedBuildJob);

        try {
            buildJobController.saveBuildJob(buildJob, bindingResult);
            fail("test should fail due to mocked error");
        } catch (ValidationException e) {
            assertEquals(1, e.getViolations().size());
            assertEquals("buildJob", e.getViolations().get(0).getObjectName());
            assertEquals("mocked error", e.getViolations().get(0).getDefaultMessage());
        }

        verifyZeroInteractions(getBuildJobCommand);
        verifyZeroInteractions(saveBuildJobCommand);
        verifyZeroInteractions(deleteBuildJobCommand);
    }

    @Test
    public void testRemoveBuildJob() {
        buildJobController.removeBuildJob(KEY);

        verify(deleteBuildJobCommand).execute(eq(new BuildKey(KEY)));
        verifyNoMoreInteractions(deleteBuildJobCommand);
        verifyZeroInteractions(saveBuildJobCommand);
        verifyZeroInteractions(getBuildJobCommand);
    }
}