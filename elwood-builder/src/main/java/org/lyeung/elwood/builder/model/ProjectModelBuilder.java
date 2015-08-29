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

package org.lyeung.elwood.builder.model;

/**
 * Created by lyeung on 9/07/15.
 */
public class ProjectModelBuilder {

    private String name;

    private String description;

    private String buildFile;

    public ProjectModelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProjectModelBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProjectModelBuilder buildFile(String buildFile) {
        this.buildFile = buildFile;
        return this;
    }

    public ProjectModel build() {
        ProjectModel model = new ProjectModel();

        model.setName(name);
        model.setDescription(description);
        model.setBuildFile(buildFile);
        return model;
    }
}
