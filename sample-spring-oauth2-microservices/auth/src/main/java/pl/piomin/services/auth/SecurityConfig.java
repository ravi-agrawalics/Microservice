package pl.piomin.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
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
    	web.ignoring().antMatchers("/auth/**");
    	web.ignoring().antMatchers("/user/**");
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() 
      throws Exception {
        return super.authenticationManagerBean();
    }
    
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
    httpSecurity.requiresChannel().anyRequest().requiresSecure()
    // Configures url based authorization
    .and().authorizeRequests()
    // Anyone can access the urls
    .antMatchers("/auth/**", "/login", "/signup", "/forgotPassword").permitAll()
    // The rest of the our application is protected.
    .anyRequest().hasAnyRole("ANONYMOUS, USER")
    // Configures form login
    .and().formLogin().loginPage("/login").failureUrl("/login?error=bad_credentials")
    // Configures the logout function
    .and().rememberMe().tokenValiditySeconds(1209600);
    }

}