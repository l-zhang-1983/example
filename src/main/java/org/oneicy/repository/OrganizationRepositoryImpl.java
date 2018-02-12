package org.oneicy.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class OrganizationRepositoryImpl implements OrganizationRepositoryCustomized {
	@PersistenceContext
	protected EntityManager em;

}
