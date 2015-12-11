package org.lyeung.elwood.maven;

import org.lyeung.elwood.maven.runlistener.ElwoodRunListener;

/**
 * Created by lyeung on 23/11/2015.
 */
public final class ElwoodMavenConstants {

    public static final String MAVEN_PLUGINS_GROUP_ID = "org.apache.maven.plugins";

    public static final String SUREFIRE_PLUGIN_ARTIFACT_ID = "maven-surefire-plugin";

    public static final String FAILSAFE_PLUGIN_ARTIFACT_ID = "maven-failsafe-plugin";

    public static final String SUREFIRE_PLUGIN = MAVEN_PLUGINS_GROUP_ID + ":"
            + SUREFIRE_PLUGIN_ARTIFACT_ID;

    public static final String FAILSAFE_PLUGIN = MAVEN_PLUGINS_GROUP_ID + ":"
            + FAILSAFE_PLUGIN_ARTIFACT_ID;

    public static final String RUN_LISTENER_CLASS = ElwoodRunListener.class.getCanonicalName();

    private ElwoodMavenConstants() {
        // do-nothing
    }
}
