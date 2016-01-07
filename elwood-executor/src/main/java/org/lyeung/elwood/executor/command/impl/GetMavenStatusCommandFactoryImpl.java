package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.executor.command.GetMavenStatusCommand;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandFactory;
import org.lyeung.elwood.executor.command.MavenStatusRuleMatcherManager;

/**
 * Created by lyeung on 3/01/2016.
 */
public class GetMavenStatusCommandFactoryImpl implements GetMavenStatusCommandFactory {

    private final MavenStatusRuleMatcherManager matcherManager;

    public GetMavenStatusCommandFactoryImpl(MavenStatusRuleMatcherManager matcherManager) {
        this.matcherManager = matcherManager;
    }

    @Override
    public GetMavenStatusCommand makeCommand() {
        return new GetMavenStatusCommandImpl(matcherManager);
    }
}
