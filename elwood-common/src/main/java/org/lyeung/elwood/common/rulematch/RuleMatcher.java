package org.lyeung.elwood.common.rulematch;

/**
 * Created by lyeung on 27/12/2015.
 */
public interface RuleMatcher<T, R> {

    boolean isMatch(T value);

    R applyRule(T value);
}
