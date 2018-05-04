package pl.piomin.microservices.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.boot.web.client.RestTemplateBuilder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	   @Bean
	   public RestTemplate restTemplate(RestTemplateBuilder builder) {
	      return builder.build();
	   }
}
