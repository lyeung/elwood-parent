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

package org.lyeung.elwood.web.controller;

import org.lyeung.elwood.common.SystemException;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by lyeung on 22/10/2015.
 */
public class ValidationException extends SystemException {

    private final List<ObjectError> violations;

    public ValidationException(String message, List<ObjectError> violations) {
        super(message);
        this.violations = violations;
    }

    public List<ObjectError> getViolations() {
        return violations;
    }
}
