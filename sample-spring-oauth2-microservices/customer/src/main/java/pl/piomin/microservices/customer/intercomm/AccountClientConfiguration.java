package pl.piomin.microservices.customer.intercomm;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

//import feign.Logger;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;

public class AccountClientConfiguration {

	@Value("${security.oauth2.client.access-token-uri}")
	private String accessTokenUri;
	@Value("${security.oauth2.client.user-authorization-uri}")
	private String authorizeUrl;
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	@Value("${security.oauth2.client.scope}")
	private String scope;
	
	
	@Autowired
	private OAuth2ProtectedResourceDetails resource;

	/*@Bean
	RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
	}*/

/*	@Bean
	public RequestInterceptor requestTokenBearerInterceptor() {

	    return new RequestInterceptor() {
	        @Override
	        public void apply(RequestTemplate requestTemplate) {
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
	            requestTemplate.header("Authorization", "Bearer " + details.getTokenValue());                   
	        }


	    };
	}*/
/*	
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}*/

	private OAuth2ProtectedResourceDetails resource() {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		//resourceDetails.setUsername("piomin");
		//resourceDetails.setPassword("piot123");
		resourceDetails.setAccessTokenUri(accessTokenUri);
		resourceDetails.setClientId(clientId);
		resourceDetails.setClientSecret(clientSecret);
		resourceDetails.setGrantType("password");
		resourceDetails.setScope(Arrays.asList(scope));
		return resourceDetails;
		/*ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setGrantType("password");
		resource.setScope(Arrays.asList(scope));
		return resource;
		
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		resource.setUserAuthorizationUri(authorizeUrl);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		//resource.setScope(Arrays.asList(scope));
		resource.setGrantType("password");
		return resource ;
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setId("account");
		resource.setGrantType("password");
		resource.setScope(Arrays.asList(scope));
		return resource;
		*/
	}
	
/*	
	@Bean
	@Primary
	public OAuth2RestOperations restTemplate(OAuth2ClientContext clientContext) {
	    return new OAuth2RestTemplate(resource, clientContext);
	}
*/
}
