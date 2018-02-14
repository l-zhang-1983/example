package org.oneicy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.oneicy.entity.Organization;
import org.oneicy.entity.User;
import org.oneicy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class ExampleController {

	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

	private UserService userService;

	@Autowired
	private void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(path = "/preLogin")
	public String preLogin() {
		return "login";
	}

	@ResponseBody
	@RequestMapping("/string/{query}")
	public String string(@PathVariable("query") String query) throws JsonProcessingException {
		Collection<User> list = this.userService.getUserListBy(query);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(list);
	}

	@ResponseBody
	@RequestMapping("/entityExperiment/{query}")
	public Collection<User> entityExperiment(@PathVariable("query") String query) {
		return this.userService.getUserListBy(query);
	}

	@ResponseBody
	@RequestMapping("/org/{query}")
	public Collection<Organization> org(@PathVariable("query") String query) {
		Collection<Organization> list = this.userService.getOrganizationList(query);
		for (Organization organization : list) {
			logger.error("users: {}", organization.getUsers());
		}
		return list;
	}

}
