package org.lyeung.elwood.web;

import org.lyeung.elwood.web.config.ElwoodConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ElwoodWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ElwoodConfiguration.class);
        applicationContext.setServletContext(servletContext);

        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher",
                new DispatcherServlet(applicationContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
