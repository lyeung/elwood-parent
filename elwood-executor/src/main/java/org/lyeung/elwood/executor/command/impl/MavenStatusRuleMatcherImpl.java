package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

/**
 * Created by lyeung on 27/12/2015.
 */
public class MavenStatusRuleMatcherImpl extends AbstractMavenStatusRuleMatcher {

    public MavenStatusRuleMatcherImpl(MavenStatusType type) {
        super(type);
    }

    @Override
    public Tuple<MavenStatusType, Integer> applyRule(String value) {
        final String[] tokens = value.split(":");
        return new Tuple<>(getType(), count(tokens[1].trim()));
    }

    private int count(String line) {
        final String content = line.substring(1, line.length() - 1);
        if ("".equals(content)) {
            return 0;
        }

        return content.split(",\\s?").length;
    }
}
