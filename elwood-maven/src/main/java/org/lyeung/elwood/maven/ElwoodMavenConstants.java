package org.lyeung.elwood.maven;

import org.lyeung.elwood.maven.impl.ElwoodRunListener;

/**
 * Created by lyeung on 23/11/2015.
 */
public final class ElwoodMavenConstants {

    public static final String SUREFIRE_PLUGIN = "org.apache.maven.plugins:maven-surefire-plugin";

    public static final String RUN_LISTENER_CLASS = ElwoodRunListener.class.getCanonicalName();

    private ElwoodMavenConstants() {
        // do-nothing
    }
}
