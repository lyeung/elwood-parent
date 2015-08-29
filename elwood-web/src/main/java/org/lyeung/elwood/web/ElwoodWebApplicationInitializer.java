/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.web;

import org.lyeung.elwood.web.config.ElwoodConfiguration;
import org.lyeung.elwood.web.config.ElwoodExecutorConfiguration;
import org.lyeung.elwood.web.config.ElwoodRepositoryConfiguraiton;
import org.lyeung.elwood.web.config.ElwoodSharedConfiguration;
import org.lyeung.elwood.web.filter.SimpleCorsFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ElwoodWebApplicationInitializer
        implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {

        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(ElwoodConfiguration.class,
                ElwoodRepositoryConfiguraiton.class,
                ElwoodExecutorConfiguration.class,
                ElwoodSharedConfiguration.class);
        applicationContext.setServletContext(servletContext);

        servletContext.addFilter("corsFilter", SimpleCorsFilter.class)
                .addMappingForUrlPatterns(null, false, "/*");

        ServletRegistration.Dynamic registration =
                servletContext.addServlet("dispatcher",
                        new DispatcherServlet(applicationContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }
}
