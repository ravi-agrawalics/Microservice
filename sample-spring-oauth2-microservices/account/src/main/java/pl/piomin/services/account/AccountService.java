package pl.piomin.services.account;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableResourceServer
public class AccountService {

	public static void main(String[] args) {
		SpringApplication.run(AccountService.class, args);
	}

/*	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/oauth2?useSSL=false")
				.username("root").password("root").driverClassName("com.mysql.jdbc.Driver").build();
	}
	*/
}
