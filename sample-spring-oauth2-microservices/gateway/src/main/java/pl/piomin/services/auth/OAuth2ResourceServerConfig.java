package pl.piomin.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configuration
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter  {
 
	private ResourceServerProperties resourceServerProperties;

	@Autowired
	public OAuth2ResourceServerConfig(ResourceServerProperties resourceServerProperties) {
		this.resourceServerProperties = resourceServerProperties;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceServerProperties.getId());
		resources.stateless(false);
		super.configure(resources);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		  http.csrf().disable()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	      .and().authorizeRequests()
	      .antMatchers("/users/**", "/login", "/uaa/**").permitAll();
	}

    
}