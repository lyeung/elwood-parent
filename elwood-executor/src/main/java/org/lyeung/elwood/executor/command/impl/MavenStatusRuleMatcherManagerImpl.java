package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.common.rulematch.RuleMatcher;
import org.lyeung.elwood.executor.command.MavenStatusRuleMatcherManager;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyeung on 1/01/2016.
 */
public class MavenStatusRuleMatcherManagerImpl implements MavenStatusRuleMatcherManager {

    private static final List<RuleMatcher<String, Tuple<MavenStatusType, Integer>>> RULE_MATCHERS =
            Arrays.asList(new MavenStatusRuleMatcherImpl(MavenStatusType.SUCCESS),
                    new MavenStatusRuleMatcherImpl(MavenStatusType.FAILED),
                    new MavenStatusRuleMatcherImpl(MavenStatusType.IGNORED));


    public Map<MavenStatusType, Tuple<MavenStatusType, Integer>> processLines(List<String> lines) {
        final Map<MavenStatusType, Tuple<MavenStatusType, Integer>> result = createResult();
        lines.forEach(line -> RULE_MATCHERS.forEach(ruleMatcher -> {
            if (ruleMatcher.isMatch(line)) {
                Tuple<MavenStatusType, Integer> tuple = ruleMatcher.applyRule(line);
                result.put(tuple.getValue1(), new Tuple<>(tuple.getValue1(),
                        result.get(tuple.getValue1()).getValue2() + tuple.getValue2()));
            }
        }));

        return result;
    }

    private Map<MavenStatusType, Tuple<MavenStatusType, Integer>> createResult() {
        final Map<MavenStatusType, Tuple<MavenStatusType, Integer>> result = new HashMap<>();
        result.put(MavenStatusType.SUCCESS, new Tuple<>(MavenStatusType.SUCCESS, 0));
        result.put(MavenStatusType.IGNORED, new Tuple<>(MavenStatusType.IGNORED, 0));
        result.put(MavenStatusType.FAILED, new Tuple<>(MavenStatusType.FAILED, 0));

        return result;
    }
}
