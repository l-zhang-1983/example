package org.oneicy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.oneicy.exception.SysSimpleMappingExceptionResolver;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
@Import({MVCConfig.class})
public class WebAppConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppConfig.class);

	private static final String P_DB_DRIVER = "jdbc.driver";
	private static final String P_DB_PASSWORD = "jdbc.password";
	private static final String P_DB_URL = "jdbc.url";
	private static final String P_DB_USERNAME = "jdbc.username";

	private static final String P_DB_MAX_ACTIVE = "db.maxActive";
	private static final String P_DB_INIT_SIZE = "db.initialSize";
	private static final String P_DB_MAX_WAIT = "db.maxWait";
	private static final String P_DB_MIN_IDLE = "db.minIdle";
	private static final String P_DB_TIME_BETWEEN_EVICTION_RUNS_MLIILIS = "db.timeBetweenEvictionRunsMillis";
	private static final String P_DB_MIM_EVICTABLE_IDLE_TIME_MILLIS = "db.minEvictableIdleTimeMillis";
	private static final String P_DB_VALIDATION_QUERY = "db.validationQuery";
	private static final String P_DB_TEST_WHILE_IDLE = "db.testWhileIdle";
	private static final String P_DB_TEST_ON_BORROW = "db.testOnBorrow";
	private static final String P_DB_TEST_ON_RETURN = "db.testOnReturn";
	private static final String P_DB_FILTERS = "db.filters";


	private static final String P_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String P_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String P_ENTITY_MANAGER_PACKAGES_TO_SCAN = "entity_manager.packages.to.scan";
	private static final String P_HIBERNATE_MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
	private static final String P_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
	private static final String P_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
	private static final String P_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String P_HIBERNATE_CACHE_P_CLASS = "hibernate.cache.provider_class";
	private static final String P_PERSISTENCE_VALI_MODE = "javax.persistence.validation.mode";
	private static final String P_HIBERNATE_EJB_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";

	@Resource
	private Environment env;

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(P_DB_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(P_DB_URL));
		dataSource.setUsername(env.getRequiredProperty(P_DB_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(P_DB_PASSWORD));

		dataSource.setMaxActive(env.getRequiredProperty(P_DB_MAX_ACTIVE, Integer.class));
		dataSource.setInitialSize(env.getRequiredProperty(P_DB_INIT_SIZE, Integer.class));
		dataSource.setMaxWait(env.getRequiredProperty(P_DB_MAX_WAIT, Integer.class));
		dataSource.setMinIdle(env.getRequiredProperty(P_DB_MIN_IDLE, Integer.class));
		dataSource.setTimeBetweenEvictionRunsMillis(env.getRequiredProperty(P_DB_TIME_BETWEEN_EVICTION_RUNS_MLIILIS, Integer.class));
		dataSource.setMinEvictableIdleTimeMillis(env.getRequiredProperty(P_DB_MIM_EVICTABLE_IDLE_TIME_MILLIS, Long.class));
		dataSource.setValidationQuery(env.getRequiredProperty(P_DB_VALIDATION_QUERY));
		dataSource.setTestWhileIdle(env.getRequiredProperty(P_DB_TEST_WHILE_IDLE, Boolean.class));
		dataSource.setTestOnBorrow(env.getRequiredProperty(P_DB_TEST_ON_BORROW, Boolean.class));
		dataSource.setTestOnReturn(env.getRequiredProperty(P_DB_TEST_ON_RETURN, Boolean.class));

		try {
			dataSource.setFilters(env.getRequiredProperty(P_DB_FILTERS));
		} catch (SQLException e) {
			LOGGER.error("Create DataSource filters failed.", e);
		}
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan(env.getRequiredProperty(P_ENTITY_MANAGER_PACKAGES_TO_SCAN));
		bean.setPersistenceUnitName(env.getProperty("jpa.persistenceUnitName"));
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(env.getRequiredProperty(P_HIBERNATE_SHOW_SQL, Boolean.class));
		adapter.setDatabasePlatform(env.getRequiredProperty(P_HIBERNATE_DIALECT));
		bean.setJpaVendorAdapter(adapter);
		bean.setJpaProperties(jpaProperties());
		return bean;
	}

	private Properties jpaProperties() {
		Properties prop = new Properties();
		prop.put(P_HIBERNATE_MAX_FETCH_DEPTH, env.getRequiredProperty(P_HIBERNATE_MAX_FETCH_DEPTH, Integer.class));
		prop.put(P_HIBERNATE_JDBC_FETCH_SIZE, env.getRequiredProperty(P_HIBERNATE_JDBC_FETCH_SIZE, Integer.class));
		prop.put(P_HIBERNATE_JDBC_BATCH_SIZE, env.getRequiredProperty(P_HIBERNATE_JDBC_BATCH_SIZE, Integer.class));
		prop.put(P_HIBERNATE_SHOW_SQL, env.getRequiredProperty(P_HIBERNATE_SHOW_SQL, Boolean.class));
		prop.put(P_HIBERNATE_FORMAT_SQL, env.getRequiredProperty(P_HIBERNATE_FORMAT_SQL, Boolean.class));
		prop.put(P_HIBERNATE_CACHE_P_CLASS, env.getRequiredProperty(P_HIBERNATE_CACHE_P_CLASS));
		prop.put(P_PERSISTENCE_VALI_MODE, env.getRequiredProperty(P_PERSISTENCE_VALI_MODE));
		prop.put(P_HIBERNATE_EJB_NAMING_STRATEGY, env.getRequiredProperty(P_HIBERNATE_EJB_NAMING_STRATEGY));

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
		resolver.setMaxUploadSize(10 * 1024 * 1024);
		return resolver;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public HandlerExceptionResolver handlerExceptionResolver() {
		SysSimpleMappingExceptionResolver resolver = new SysSimpleMappingExceptionResolver();
		resolver.setDefaultErrorView("error");
		resolver.setExceptionAttribute("ex");
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
