package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.common.rulematch.RuleMatcher;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

/**
 * Created by lyeung on 27/12/2015.
 */
public abstract class AbstractMavenStatusRuleMatcher
        implements RuleMatcher<String, Tuple<MavenStatusType, Integer>> {

    private final MavenStatusType type;

    public AbstractMavenStatusRuleMatcher(MavenStatusType type) {
        this.type = type;
    }

    MavenStatusType getType() {
        return type;
    }

    @Override
    public boolean isMatch(String value) {
        final String[] tokens = value.split(":");
        return type.name().toLowerCase().equals(tokens[0].trim());
    }
}
