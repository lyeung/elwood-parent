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

import org.springframework.http.MediaType;

/**
 * Created by lyeung on 23/10/2015.
 */
public class AbstractVndErrorExceptionHandler {

    private static final String APPLICATION_VND_ERROR = "application/vnd.error";

    MediaType createMediaType() {
        return MediaType.parseMediaType(APPLICATION_VND_ERROR);
    }
}
