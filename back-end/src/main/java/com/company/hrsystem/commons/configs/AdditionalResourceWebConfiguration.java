package com.company.hrsystem.commons.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/" + SystemProperties.PATH_SAVE_EMPLOYEE_IMAGE + "/**")
				.addResourceLocations("file:" + SystemProperties.PATH_SAVE_EMPLOYEE_IMAGE + "\\");
	}

}
