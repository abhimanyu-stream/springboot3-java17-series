package com.stream.authentication.configuration;


import com.stream.authentication.filter.AuthorizedOrUnauthorizedCheckingEntryPoint;
import com.stream.authentication.filter.JwtTokenSecurityFilter;
import com.stream.authentication.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.concurrent.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)

// prePostEnabled = true) // by default
public class WebSecurity {
	// Note:-  (WebSecurityConfigurerAdapter is deprecated from Spring 2.7.0)
	// so do not use public class WebSecurityConfig extends WebSecurityConfigurerAdapter
	@Autowired
	CustomUserDetailsService customUserDetailsMyImpl;



	private AuthorizedOrUnauthorizedCheckingEntryPoint unauthorizedHandler;

	@Autowired
	public WebSecurity(CustomUserDetailsService customUserDetailsMyImpl, AuthorizedOrUnauthorizedCheckingEntryPoint unauthorizedHandler) {
		super();
		this.customUserDetailsMyImpl = customUserDetailsMyImpl;
		this.unauthorizedHandler = unauthorizedHandler;}

	@Bean
	public JwtTokenSecurityFilter authenticationJwtTokenFilter() {
		return new JwtTokenSecurityFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(customUserDetailsMyImpl);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.debug(false).ignoring().requestMatchers("/css/**", "/js/**", "/lib/**", "/favicon.ico", "/images/**", "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// http://localhost:9595/userapp/users/signup

	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}




	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//note:-   http.cors() where 'cors()' is deprecated and marked for removal
		http.csrf(AbstractHttpConfigurer :: disable)
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
								.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")//"equivalent to ROLE_ADMIN, since ROLE_ WILL BE Pre-appended ROLE_ADMIN"
								.requestMatchers("/admin/**").hasAnyRole("ADMIN")
								.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
								.requestMatchers("/mod/**").hasAnyRole("MODERATOR")
								//.requestMatchers().hasAnyRole("USER", "ADMIN","MODERATOR")
								.requestMatchers("/api/test/**", "/users/signup", "/users/login","/users/refresh/token","/swagger-ui.html", "/swagger-ui/**", "/swagger-ui/index.html", "/v3/api-docs").permitAll()

								.anyRequest().authenticated());


		//Note:- below headers setting is for H2 DB
		http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig :: sameOrigin));



		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
		.logout(logout -> logout
		.logoutUrl("/users/logout")
		//.addLogoutHandler(logoutHandler)
		.logoutSuccessHandler((request, response, authentication) -> {
					SecurityContextHolder.clearContext();
		}));
		return http.build();
	}



	/*public ExecutorService executorService(){
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		ExecutorService executorServiceScheduled = Executors.newSingleThreadScheduledExecutor();
		ExecutorService executorServiceScheduledCorePoolSize = Executors.newScheduledThreadPool(6);

		ExecutorService executorServiceCached = Executors.newCachedThreadPool();

		ExecutorService executorServiceFixed = Executors.newFixedThreadPool(8);
		ExecutorService executorServiceWorkStealing = Executors.newWorkStealingPool();

		return executorService;

	}*/

	@Bean("executorService")
	public ExecutorService executorService(){
		ExecutorService executorService = new ThreadPoolExecutor(
				1,  // Core pool size
				8,  // Maximum pool size
				0L, // Keep-alive time
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>() // Work queue, it can keep unlimited tasks
		);
		return executorService;

	}
	@Bean("executor")
	public Executor executor(){
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(1);
		threadPoolTaskExecutor.setMaxPoolSize(8);
		threadPoolTaskExecutor.setKeepAliveSeconds(0);
		threadPoolTaskExecutor.setPrestartAllCoreThreads(false);// No core thread will be created and sitting idle for any task to be assigned
		threadPoolTaskExecutor.setQueueCapacity(300);// max 300 tasks will be Queue.
		threadPoolTaskExecutor.setThreadGroupName("GroupName-Executor");
		threadPoolTaskExecutor.setThreadNamePrefix("Executor-");
		threadPoolTaskExecutor.setThreadPriority(5);
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;

	}

}

/***
 * spring.datasource.url: jdbc:h2:mem:[database-name] for In-memory database and jdbc:h2:file:[path/database-name] for disk-based database.
 * spring.datasource.username & spring.datasource.password properties are the same as your database installation.
 * Spring Boot uses Hibernate for JPA implementation, we configure H2Dialect for H2 Database
 * spring.jpa.hibernate.ddl-auto is used for database initialization. We set the value to update value so that a table will be created in the database automatically corresponding to defined data model. Any change to the model will also trigger an update to the table. For production, this property should be validate.
 * spring.h2.console.enabled=true tells the Spring to start H2 Database administration tool and you can access this tool on the browser: http://localhost:8080/h2-console.
 * spring.h2.console.path=/h2-ui is for H2 console’s url, so the default url http://localhost:8080/h2-console will change to http://localhost:8080/h2-ui.
 * */