/*
 *
 * Copyright (C) 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lyeung.elwood.web.controller.buildstats;

/**
 * Created by lyeung on 10/01/2016.
 */
public class BuildResultStats {

    public static final BuildResultStats NONE = new BuildResultStats(-1, -1, -1);

    private final int successCount;

    private final int failedCount;

    private final int ignoredCount;

    public BuildResultStats(int successCount, int failedCount, int ignoredCount) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.ignoredCount = ignoredCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public int getIgnoredCount() {
        return ignoredCount;
    }
}
