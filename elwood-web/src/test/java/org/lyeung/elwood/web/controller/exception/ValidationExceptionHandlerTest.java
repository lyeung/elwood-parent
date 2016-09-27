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

package org.lyeung.elwood.web.controller.exception;

import com.github.lyeung.common.test.SlowTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.web.controller.ValidationException;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 26/10/2015.
 */
@Category(SlowTest.class)
@RunWith(MockitoJUnitRunner.class)
public class ValidationExceptionHandlerTest {

    @InjectMocks
    private ValidationExceptionHandler handler;

    @Test
    public void testValidationException() {
        final ValidationException exception = new ValidationException("mocked exception",
                Arrays.asList(new ObjectError("objName", "error1"),
                        new ObjectError("objName", "error2")));

        final ResponseEntity<VndErrors> response = handler.validationException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getHeaders().size());
        assertEquals("application/vnd.error", response.getHeaders().getContentType().toString());

    }
}