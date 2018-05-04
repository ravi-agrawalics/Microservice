/**
 * 
 */
package pl.piomin.microservices.customer.api;

/**
 * @author 271157
 *
 */
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.microservices.customer.intercomm.AccountClient;
import pl.piomin.microservices.customer.model.Customer;


@RefreshScope
@RestController
public class CustomerController {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	AccountClient accountClient;
	
	@GetMapping("/{id}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public Customer findCustomer(@PathVariable("id") Integer id,HttpServletRequest req) {
		Customer c = new Customer(id, "John Smith", 33);
		LOGGER.info("Customer found: id={}", c.getId());
		Enumeration<String> headers = req.getHeaders("Authorization");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		c.setAccounts(accountClient.findAccounts());
		LOGGER.info("Accounts found: size={}", c.getAccounts().size());
		return c;
	}
	
	
	

}
