package pl.piomin.microservices.customer.intercomm;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.piomin.microservices.customer.model.Account;

@FeignClient(name = "account-service", configuration = AccountClientConfiguration.class)
public interface AccountClient {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	List<Account> findAccounts();
	
}