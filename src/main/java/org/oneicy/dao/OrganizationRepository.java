package org.oneicy.dao;

import org.oneicy.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OrganizationRepository extends JpaRepository<Organization, Integer>, OrganizationRepositoryCustomized {
	Collection<Organization> getAllByOrgCodeContains(String query);
}
