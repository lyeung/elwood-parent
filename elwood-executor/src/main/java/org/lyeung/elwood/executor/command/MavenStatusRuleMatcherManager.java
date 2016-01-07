package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

import java.util.List;
import java.util.Map;

/**
 * Created by lyeung on 3/01/2016.
 */
public interface MavenStatusRuleMatcherManager {

    Map<MavenStatusType, Tuple<MavenStatusType, Integer>> processLines(List<String> lines);

}
