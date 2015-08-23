package org.lyeung.elwood.executor.command.impl;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 20/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildJobCommandFactoryImplTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private BuildMapLog buildMapLog;

    @Test
    public void testMakeCommand() {
        assertNotNull(new BuildJobCommandFactoryImpl(
                projectRepository, buildRepository, buildMapLog).makeCommand());
    }
}