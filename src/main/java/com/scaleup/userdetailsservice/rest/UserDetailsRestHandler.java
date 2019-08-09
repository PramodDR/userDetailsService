package com.scaleup.userdetailsservice.rest;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scaleup.userdetailsservice.domain.UserDetailsDomainManager;
import com.scaleup.userdetailsservice.domain.converters.UserDetailsInputConverter;
import com.scaleup.userdetailsservice.jaxb.UserData;

@RestController
@RequestMapping("/")
public class UserDetailsRestHandler {

	private static Logger log = LoggerFactory
			.getLogger(UserDetailsRestHandler.class);

	private UserDetailsInputConverter inputConverter = new UserDetailsInputConverter();

	@Autowired
	private UserDetailsDomainManager userDetailsDomainManager;
	

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<Void> addUser(@RequestBody UserData userData) {
		log.info("User email data for addUser::" + userData.getEmail());
		return CompletableFuture.supplyAsync(
				() -> inputConverter.convert(userData)).thenCompose(
				convertedInput -> { return userDetailsDomainManager
						.addUser(convertedInput);});
	}
	
	@RequestMapping(value = "/deleteUser/{emailId}", method = RequestMethod.DELETE)
	public CompletableFuture<Void> addUser(@PathVariable String emailId) {
		log.info("User email data for delete API::" + emailId);
		return userDetailsDomainManager
						.deleteUser(emailId);
	}
	
	/*@RequestMapping(value = "/getUser", method = RequestMethod.DELETE)
	public CompletableFuture<Void> getUser(@RequestParam String email,String firstName,String lastName) {
		log.info("Get details by email::" + emailId);
		return userDetailsDomainManager
						.deleteUser(emailId);
	}*/
	
	

}
