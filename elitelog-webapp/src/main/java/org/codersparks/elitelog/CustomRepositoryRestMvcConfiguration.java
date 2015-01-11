package org.codersparks.elitelog;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;

@Configuration
public class CustomRepositoryRestMvcConfiguration extends
		RepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.setBaseUri(URI.create("/api"));
	}

	@Override
	@Bean
	public HateoasPageableHandlerMethodArgumentResolver pageableResolver() {

		HateoasPageableHandlerMethodArgumentResolver resolver = super
				.pageableResolver();
		resolver.setOneIndexedParameters(true);
		return resolver;
	}
}
