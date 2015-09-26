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

package org.lyeung.elwood.web.controller.runbuild;

import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.web.controller.runbuild.enums.ContentResponseStatus;

import java.net.URL;

/**
 * Created by lyeung on 13/08/2015.
 */
public class ContentResponse {

    private final KeyCountTuple keyCount;

    private final ContentResponseStatus status;

    private String content;

    private URL redirectUrl;

    public ContentResponse(KeyCountTuple keyCount, ContentResponseStatus status,
        String content) {

        this.keyCount = keyCount;
        this.status = status;
        this.content = content;
    }

    public ContentResponse(KeyCountTuple keyCount, ContentResponseStatus status,
        URL redirectUrl) {

        this.keyCount = keyCount;
        this.status = status;
        this.redirectUrl = redirectUrl;
    }

    public String getContent() {
        return content;
    }

    public ContentResponseStatus getStatus() {
        return status;
    }

    public URL getRedirectUrl() {
        return redirectUrl;
    }

    public KeyCountTuple getKeyCount() {
        return keyCount;
    }
}
