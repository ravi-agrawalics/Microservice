package pl.piomin.services.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private AuthenticationManager authenticationManager;

	//@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
/*		endpoints.authenticationManager(this.authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(accessTokenConverter());*/
		/*endpoints.authenticationManager(authenticationManager);*/
/*		 endpoints.authorizationCodeServices(authorizationCodeServices())
         .authenticationManager(authenticationManager).tokenStore(tokenStore())
         .approvalStoreDisabled();*/
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
		    }




	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		// oauthServer.allowFormAuthenticationForClients();
		oauthServer.tokenKeyAccess("permitAll()")
		  .checkTokenAccess("isAuthenticated()");
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		return new JwtAccessTokenConverter();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
/*		clients.inMemory()
		.withClient("customer-service")
		.secret("secret")
		.authorizedGrantTypes("password")
		.scopes("read")
		.autoApprove(true);*/
		
/*		clients.inMemory()
		.withClient("customer-service")
		.secret("secret")
		.authorizedGrantTypes("password")
		.scopes("read");*/
		
		//clients.jdbc(dataSource);
		/*
	        clients.inMemory()
	        .withClient("customer-service")
	        .secret("secret")
	        .scopes("read")
	        .autoApprove(true)
	        .accessTokenValiditySeconds(600)
	        .refreshTokenValiditySeconds(600)
	        .authorizedGrantTypes("implicit", "refresh_token","authorization_code","password");
		*/
		
		clients.inMemory()
        .withClient("customer-service")
         .autoApprove(true)
            //.authorizedGrantTypes("password", "authorization_code", "access_token", "implicit")
        .authorizedGrantTypes("authorization_code")
            .authorities("ROLE_CLIENT")
            .scopes("read", "write")
            .accessTokenValiditySeconds(6000000)
  
        .refreshTokenValiditySeconds(6000000)
        .and()
                .withClient("account-service")
                 .autoApprove(true)
                //.secret("secret")
                .scopes("read","write")
                .authorizedGrantTypes("authorization_code")
                .authorities("ROLE_CLIENT")
            .accessTokenValiditySeconds(6000000)
  
        .refreshTokenValiditySeconds(6000000);
    
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(jwtTokenEnhancer());
	}


/*    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }*/
/*    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }*/
    
    @Bean        
    protected JwtAccessTokenConverter jwtTokenEnhancer() {            
    	KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "mySecretKey".toCharArray());
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    	converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
    	return converter;
    	}
}