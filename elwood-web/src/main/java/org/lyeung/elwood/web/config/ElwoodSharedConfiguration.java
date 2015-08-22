package org.lyeung.elwood.web.config;

import org.lyeung.elwood.executor.BuildMapLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lyeung on 15/08/2015.
 */
@Configuration
public class ElwoodSharedConfiguration {

    @Bean
    public BuildMapLog buildMapLog() {
        return new BuildMapLog(500);
    }
}
