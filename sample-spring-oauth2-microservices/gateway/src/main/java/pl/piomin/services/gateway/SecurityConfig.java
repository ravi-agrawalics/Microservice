package pl.piomin.services.gateway;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
//@EnableWebSecurity
//@EnableOAuth2Sso
@EnableZuulProxy
@EnableOAuth2Sso
//@Order(-10)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	    protected void configure(HttpSecurity http) throws Exception {
    	/*http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and().httpBasic().disable();*/
		http.logout().and().antMatcher("/**").authorizeRequests()
		.antMatchers("/login","/auth/**").permitAll()            
		.anyRequest().authenticated().and().csrf().disable();
	    }

/*	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.jdbcAuthentication().dataSource(dataSource);
		//auth.inMemoryAuthentication().withUser("root").password("password").roles("USER");
		auth.jdbcAuthentication().dataSource(dataSource);
	}
	
    @Bean
    protected OAuth2RestTemplate OAuth2RestTemplate(
            OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        return new OAuth2RestTemplate(resource, context);
    }

	
	private AuthenticationDetailsSource<HttpServletRequest, OAuth2AuthenticationDetails> authenticationDetailsSource() {

	        return new AuthenticationDetailsSource<HttpServletRequest, OAuth2AuthenticationDetails>() {

	            @Override
	            public OAuth2AuthenticationDetails buildDetails(
	                    HttpServletRequest request) {
	                return new OAuth2AuthenticationDetails(request);
	            }

	        };
	    }*/
	
}
