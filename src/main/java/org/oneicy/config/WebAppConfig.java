package org.oneicy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.oneicy.exception.CustomizedSimpleMappingExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.oneicy")
@EnableWebMvc
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource({"classpath:db.properties", "classpath:application.properties"})
@EnableJpaRepositories(basePackages = {"org.oneicy.repository"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({MVCConfig.class, ShiroConfig.class})
public class WebAppConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppConfig.class);

	@Resource
	private Environment env;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));

		dataSource.setMaxActive(env.getRequiredProperty("db.maxActive", Integer.class));
		dataSource.setInitialSize(env.getRequiredProperty("db.initialSize", Integer.class));
		dataSource.setMaxWait(env.getRequiredProperty("db.maxWait", Integer.class));
		dataSource.setMinIdle(env.getRequiredProperty("db.minIdle", Integer.class));
		dataSource.setTimeBetweenEvictionRunsMillis(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis", Integer.class));
		dataSource.setMinEvictableIdleTimeMillis(env.getRequiredProperty("db.minEvictableIdleTimeMillis", Long.class));
		dataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));
		dataSource.setTestWhileIdle(env.getRequiredProperty("db.testWhileIdle", Boolean.class));
		dataSource.setTestOnBorrow(env.getRequiredProperty("db.testOnBorrow", Boolean.class));
		dataSource.setTestOnReturn(env.getRequiredProperty("db.testOnReturn", Boolean.class));
		try {
			dataSource.setFilters(env.getRequiredProperty("db.filters"));
		} catch (SQLException e) {
			LOGGER.error("Error in setting filters: ", e);
		}

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan(env.getRequiredProperty("entity_manager.packages.to.scan"));
		bean.setPersistenceUnitName(env.getProperty("jpa.persistenceUnitName"));
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(env.getRequiredProperty("hibernate.show_sql", Boolean.class));
		adapter.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));
		bean.setJpaVendorAdapter(adapter);
		bean.setJpaProperties(jpaProperties());
		return bean;
	}

	private Properties jpaProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.max_fetch_depth", env.getRequiredProperty("hibernate.max_fetch_depth", Integer.class));
		prop.put("hibernate.jdbc.fetch_size", env.getRequiredProperty("hibernate.jdbc.fetch_size", Integer.class));
		prop.put("hibernate.jdbc.batch_size", env.getRequiredProperty("hibernate.jdbc.batch_size", Integer.class));
		prop.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql", Boolean.class));
		prop.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql", Boolean.class));
		prop.put("hibernate.cache.provider_class", env.getRequiredProperty("hibernate.cache.provider_class"));
		prop.put("javax.persistence.validation.mode", env.getRequiredProperty("javax.persistence.validation.mode"));
		prop.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy"));

		return prop;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		return new SchedulerFactoryBean();
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding(env.getRequiredProperty("project.encoding"));
		int fileSize = 10 * 1024 * 1024;
		// 10MB
		resolver.setMaxUploadSize(fileSize);
		return resolver;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public HandlerExceptionResolver handlerExceptionResolver() {
		CustomizedSimpleMappingExceptionResolver resolver = new CustomizedSimpleMappingExceptionResolver();
		resolver.setDefaultErrorView("error");
		resolver.setExceptionAttribute("exception");
		Properties mappings = new Properties();
		mappings.setProperty("java.lang.Exception", "exception");
		resolver.setExceptionMappings(mappings);
//		Properties statusCode = new Properties();
//		statusCode.setProperty("", "");
//		statusCode.setProperty("", "");
//		statusCode.setProperty("", "");
//		resolver.setStatusCodes(statusCode);
		resolver.setDefaultStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		resolver.setWarnLogCategory("WARN");

		return resolver;
	}
}
