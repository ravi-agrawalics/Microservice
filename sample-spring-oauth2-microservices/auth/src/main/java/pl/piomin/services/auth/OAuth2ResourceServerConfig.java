package pl.piomin.services.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter  {
	
	   private static final String RESOURCE_ID = "users";
 
	    @Autowired
	      private JwtAccessTokenConverter jwtAccessTokenConverter;
	    
		@Autowired
		private DataSource dataSource;
	   
	private ResourceServerProperties resourceServerProperties;

	@Autowired
	public OAuth2ResourceServerConfig(ResourceServerProperties resourceServerProperties) {
		this.resourceServerProperties = resourceServerProperties;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceServerProperties.getId()).tokenStore(tokenStore());
		resources.stateless(false);
		super.configure(resources);
	}

    @Override
    public void configure(final HttpSecurity http) throws Exception {
       http
             .csrf().disable()
             .authorizeRequests()
             .antMatchers("/user/**").authenticated();
    }

	@Bean
	public JdbcTokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
    
}