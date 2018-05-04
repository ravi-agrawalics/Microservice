package pl.piomin.services.auth;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class AuthServer {

	public static void main(String[] args) {
		SpringApplication.run(AuthServer.class, args);
	}

	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	   @GetMapping("/users")
	    public ResponseEntity<String> getUsers(){
	    
	        return ResponseEntity.ok("You can access this page");
	        
	    }
	

}