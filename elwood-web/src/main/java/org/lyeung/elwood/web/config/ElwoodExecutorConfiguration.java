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

package org.lyeung.elwood.web.config;

import org.lyeung.elwood.builder.command.impl.ProcessBuilderCommandFactoryImpl;
import org.lyeung.elwood.builder.command.impl.ProjectBuilderCommandFactoryImpl;
import org.lyeung.elwood.common.command.impl.MkDirCommandFactoryImpl;
import org.lyeung.elwood.common.command.impl.WriteFileCommandFactoryImpl;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.BuildJobCommandFactory;
import org.lyeung.elwood.executor.command.impl.AddSurefirePluginRunListenerCommandFactoryImpl;
import org.lyeung.elwood.executor.command.impl.AttachRunListenerCommandFactoryImpl;
import org.lyeung.elwood.executor.command.impl.BuildJobCommandFactoryImpl;
import org.lyeung.elwood.executor.command.impl.BuildJobCommandImpl;
import org.lyeung.elwood.executor.command.impl.CheckoutDirCreatorCommandFactoryImpl;
import org.lyeung.elwood.executor.command.impl.ElwoodLogFileCreatorCommandFactoryImpl;
import org.lyeung.elwood.executor.command.impl.GetMavenStatusCommandFactoryImpl;
import org.lyeung.elwood.executor.command.impl.MavenStatusRuleMatcherManagerImpl;
import org.lyeung.elwood.executor.command.impl.SaveBuildResultMavenStatsCommandFactoryImpl;
import org.lyeung.elwood.executor.impl.BuildExecutorImpl;
import org.lyeung.elwood.maven.impl.PomModelManagerImpl;
import org.lyeung.elwood.vcs.command.impl.GitCloneCommandFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyeung on 15/08/2015.
 */
@Configuration
public class ElwoodExecutorConfiguration {

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 6;
    private static final int KEEP_ALIVE_IN_SECONDS = 60;
    private static final int QUEUE_CAPACITY = 10;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private BuildResultMavenStatsRepository buildResultMavenStatsRepository;

    @Autowired
    private BuildResultRepository buildResultRepository;

    @Autowired
    private BuildMapLog buildMapLog;

    @Bean
    public BuildExecutor buildExecutor() {
        return new BuildExecutorImpl(buildJobCommandFactory(), executorService());
    }

    private BuildJobCommandFactory buildJobCommandFactory() {
        return new BuildJobCommandFactoryImpl(new BuildJobCommandImpl.Param()
                .buildRepository(buildRepository)
                .projectRepository(projectRepository)
                .buildResultRepository(buildResultRepository)
                .buildMapLog(buildMapLog)
                .mkDirCommandFactory(new MkDirCommandFactoryImpl())
                .checkOutDirCreatorCommandFactory(new CheckoutDirCreatorCommandFactoryImpl())
                .elwoodLogFileCreatorCommandFactory(new ElwoodLogFileCreatorCommandFactoryImpl())
                .cloneCommandFactory(new GitCloneCommandFactoryImpl())
                .attachRunListenerCommandFactory(new AttachRunListenerCommandFactoryImpl(
                        new AddSurefirePluginRunListenerCommandFactoryImpl(
                                new PomModelManagerImpl())))
                .writeFileCommandFactory(new WriteFileCommandFactoryImpl())
                .processBuilderCommandFactory(new ProcessBuilderCommandFactoryImpl())
                .projectBuilderCommandFactory(new ProjectBuilderCommandFactoryImpl())
                .getMavenStatusCommandFactory(new GetMavenStatusCommandFactoryImpl(
                        new MavenStatusRuleMatcherManagerImpl()))
                .saveBuildResultMavenStatsCommandFactory(
                        new SaveBuildResultMavenStatsCommandFactoryImpl(
                                buildResultMavenStatsRepository)));
    }

    private ExecutorService executorService() {
        return new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE, KEEP_ALIVE_IN_SECONDS,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
