package org.lyeung.elwood.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by lyeung on 12/07/2015.
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.lyeung.elwood.web.controller")
public class ElwoodConfiguration {


}
