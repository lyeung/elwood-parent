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
 * Created by lyeung on 11/07/2015.
 */
public final class ModelStereotypeUtil {

    private ModelStereotypeUtil() {
        // do-nothing
    }

    public static BuildModel createBuildModel(String command, ProjectModel projectModel) {
        return new BuildModelBuilder()
                .buildCommand(command)
                .environmentVars("TEST_VAR=" + projectModel.getBuildFile())
                .projectModel(projectModel)
                .workingDirectory(".")
                .build();
    }

    public static ProjectModel createProjectModel() {
        return new ProjectModelBuilder()
                .buildFile("pom.xml")
                .description("test maven project")
                .name("test maven")
                .build();
    }
}
