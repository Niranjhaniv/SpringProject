package net.codejava.spring.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MultiPartInitializer implements WebApplicationInitializer {
	
	private int MAX_UPLOAD_SIZE = 5*1024 *1024;
	
	@Override
	public void onStartup(ServletContext sc) {
		
		ServletRegistration.Dynamic appservlet = sc.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
		appservlet.setLoadOnStartup(1);
		
		MultipartConfigElement multipartConfigElement =  new MultipartConfigElement("/tmp",MAX_UPLOAD_SIZE,MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE/2);
		appservlet.setMultipartConfig(multipartConfigElement);
	
	}

}
