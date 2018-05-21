package pl.piomin.services.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;

@SuppressWarnings("deprecation")
@Configuration
//@EnableWebSecurity
@Order(-20)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder(256);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());
    }
   
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	//web.ignoring().antMatchers("/auth/**");
    	//web.ignoring().antMatchers("/user/**");
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() 
      throws Exception {
       return super.authenticationManagerBean();
    	//return null;
    }

   @Override
    protected void configure(HttpSecurity http) throws Exception {
    	/* http
         .logout().permitAll()
         .and().authorizeRequests()
         .mvcMatchers("/login/**").permitAll()
                   .anyRequest().authenticated();
                   */

	   
	   http                
	   .formLogin().loginPage("/login").permitAll() 
	   
	   //.and().httpBasic().and()                    
	   .and()
	   .requestMatchers()                    
	   //specify urls handled                    
	   .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")                    
	   .antMatchers("/fonts/**", "/js/**", "/css/**")                    
	   .and()                    
	   .authorizeRequests()                    
	   //.antMatchers("/fonts/**", "/js/**", "/css/**").permitAll()                    
	   .anyRequest().authenticated();

    }
    

   
    private AuthenticationDetailsSource<HttpServletRequest, OAuth2AuthenticationDetails> authenticationDetailsSource() {

        return new AuthenticationDetailsSource<HttpServletRequest, OAuth2AuthenticationDetails>() {

            @Override
            public OAuth2AuthenticationDetails buildDetails(
                    HttpServletRequest request) {
                return new OAuth2AuthenticationDetails(request);
            }

        };
    }

    //@Bean
    RememberMeServices rememberMeServices() {
        RememberMeServices rememberMeServices = new RememberMeServices() {

            //@Override
            public void loginSuccess(HttpServletRequest arg0,
                    HttpServletResponse arg1, Authentication arg2) {

            }

            //@Override
            public void loginFail(HttpServletRequest arg0,
                    HttpServletResponse arg1) {

            }

            //@Override
            public Authentication autoLogin(HttpServletRequest arg0,
                    HttpServletResponse arg1) {
                return null;
            }
        };
        return rememberMeServices;
    }
    
    //@Bean
    AuthenticationFailureHandler failureHandler() {

        return new AuthenticationFailureHandler() {

            //@Override
            public void onAuthenticationFailure(HttpServletRequest req,
                    HttpServletResponse res, AuthenticationException arg2)
                    throws IOException, ServletException {
                req.setAttribute("error", "forward");
                req.getRequestDispatcher("/homedefault").forward(req, res);
            }

        };
    }

    //@Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest req,
                    HttpServletResponse res, Authentication arg2)
                    throws IOException, ServletException {
                res.sendRedirect("homedefault");
            }
        };
    }



}