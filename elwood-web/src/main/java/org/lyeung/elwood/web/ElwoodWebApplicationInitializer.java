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
