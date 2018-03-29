package org.oneicy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MVCConfig extends WebMvcConfigurationSupport {

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		configurer.mediaType("json", MediaType.valueOf("application/json"));
//		configurer.mediaType("xml", MediaType.valueOf("application/xml"));
		configurer.mediaType("txt", MediaType.valueOf("text/plain"));
		configurer.mediaType("*", MediaType.valueOf("*/*"));
		super.configureContentNegotiation(configurer);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		List<MediaType> jsonMediaTypes = new ArrayList<>();
		jsonMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//		supportedMediaTypes.add(MediaType.TEXT_HTML);

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new HibernateAwareObjectMapper());
		converter.setPrettyPrint(true);
		converter.setSupportedMediaTypes(jsonMediaTypes);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		converters.add(converter);

		ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
		List<MediaType> textMediaTypes = new ArrayList<>();
		textMediaTypes.add(MediaType.TEXT_PLAIN);
		textMediaTypes.add(MediaType.TEXT_HTML);
		byteArrayHttpMessageConverter.setSupportedMediaTypes(textMediaTypes);
		converters.add(byteArrayHttpMessageConverter);

		super.configureMessageConverters(converters);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
		resourceHandlerRegistry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}
