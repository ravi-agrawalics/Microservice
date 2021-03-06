package pl.piomin.services.account.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.account.model.Account;

@RefreshScope
@RestController
public class AccountController {


	@RequestMapping("/{id}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public Account findAccount(@PathVariable("id") Integer id) {
		System.out.println("FindAccount success");
		return new Account(id, 1, "123456789", 1234);
	}

	@RequestMapping("/")
	@PreAuthorize("#oauth2.hasScope('read')")
	public List<Account> findAccounts() {
		System.out.println("FindAccounts success");
		return Arrays.asList(new Account(1, 1, "123456789", 1234), new Account(2, 1, "123456780", 2500),
				new Account(3, 1, "123456781", 10000));
	}

}