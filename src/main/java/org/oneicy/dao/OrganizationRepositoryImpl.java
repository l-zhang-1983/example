package org.oneicy.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class OrganizationRepositoryImpl implements OrganizationRepositoryCustomized {
	@PersistenceContext
	protected EntityManager em;

}
