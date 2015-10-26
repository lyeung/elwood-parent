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

import org.lyeung.elwood.web.controller.ValidationException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 23/10/2015.
 */
@ControllerAdvice(annotations = RestController.class)
public class ValidationExceptionHandler extends AbstractVndErrorExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<VndErrors> validationException(ValidationException exception) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(createMediaType());

        final List<VndErrors.VndError> list = exception.getViolations().stream()
                .map(e -> new VndErrors.VndError(createLogRef(e), e.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new VndErrors(list), headers, HttpStatus.BAD_REQUEST);
    }

    private String createLogRef(ObjectError err) {
        if (err instanceof FieldError) {
            return renameObjectName(err) + "." + (((FieldError) err).getField());
        }

        return renameObjectName(err);
    }

    private String renameObjectName(ObjectError objectError) {
        return objectError.getObjectName().replace("Model", "");
    }
}
