package org.oneicy.config;

import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.util.IntrospectorCleanupListener;

import javax.servlet.*;
import java.util.EnumSet;

public class Initializer implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebAppConfig.class);
		servletContext.addListener(new ContextLoaderListener(ctx));
		servletContext.addListener(new IntrospectorCleanupListener());

		ctx.setServletContext(servletContext);

		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);

		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		FilterRegistration.Dynamic encodingServlet = servletContext.addFilter("encodingFilter", encodingFilter);
		encodingServlet.addMappingForUrlPatterns(null,true,"/*");

		FilterRegistration.Dynamic openEntityManagerInViewFilter = servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);
		openEntityManagerInViewFilter.setInitParameter("entityManagerFactoryBeanName","entityManagerFactory");
		openEntityManagerInViewFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}
