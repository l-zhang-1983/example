package org.oneicy.service;

import org.oneicy.repository.OrganizationRepository;
import org.oneicy.repository.UserRepository;
import org.oneicy.entity.Organization;
import org.oneicy.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final OrganizationRepository organizationRepository;

	private final UserRepository userRepository;

	@Autowired
	public UserService(OrganizationRepository organizationRepository, UserRepository userRepository) {
		this.organizationRepository = organizationRepository;
		this.userRepository = userRepository;
	}

	public Collection<User> getUserListBy(String query) {
		return this.userRepository.getUsersByUserNameLike("%" + query + "%");
	}

	public Collection<Organization> getOrganizationList(String query) {
		return this.organizationRepository.getAllByOrgCodeContains(query);
	}

}
