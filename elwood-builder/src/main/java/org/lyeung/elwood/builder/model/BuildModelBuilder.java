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
public class BuildModelBuilder {

    private ProjectModel projectModel;

    private String buildCommand;

    private String environmentVars;

    private String workingDirectory;

    public BuildModelBuilder projectModel(ProjectModel projectModel) {
        this.projectModel = projectModel;
        return this;
    }

    public BuildModelBuilder buildCommand(String buildCommand) {
        this.buildCommand = buildCommand;
        return this;
    }

    public BuildModelBuilder environmentVars(String environmentVars) {
        this.environmentVars = environmentVars;
        return this;
    }

    public BuildModelBuilder workingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
        return this;
    }

    public BuildModel build() {
        BuildModel buildModel = new BuildModel();

        buildModel.setProjectModel(projectModel);
        buildModel.setBuildCommand(buildCommand);
        buildModel.setEnvironmentVars(environmentVars);
        buildModel.setWorkingDirectory(workingDirectory);
        return buildModel;
    }
}
