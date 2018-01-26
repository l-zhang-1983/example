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

import java.util.*;

@Controller
public class ExampleController {

	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

	@Autowired
	private UserService userService = null;

	@ResponseBody
	@RequestMapping("/entityExperiment/{query}")
	public Collection<User> entityExperiment(@PathVariable("query") String query) {
		Collection<User> list = this.userService.getUserListBy(query);
		return list;
	}

	@ResponseBody
	@RequestMapping("/string/{query}")
	public String string(@PathVariable("query") String query) throws JsonProcessingException {
		Collection<User> list = this.userService.getUserListBy(query);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		return json;
	}

	@ResponseBody
	@RequestMapping("/org/{query}")
	public Collection<Organization> org(@PathVariable("query") String query) {
		Collection<Organization> list = this.userService.getOrganizationList(query);
		return list;
	}

	public static void main(String[] args) throws JsonProcessingException {
		List<Object> list = new ArrayList<Object>();
		list.add("中文");
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "值值值");
		list.add(map);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		System.out.println(json);
	}

}
