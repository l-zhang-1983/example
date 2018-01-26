package org.oneicy.dao;

import org.oneicy.entity.ExampleList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

public class UserRepositoryImpl implements UserRepositoryCustomized {
	@PersistenceContext
	protected EntityManager em;

	public Collection<ExampleList> getExampleList(String name) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("from ExampleList el ");
		jpql.append("where el.listName = :listName");
		jpql.append("");
		jpql.append("");
		jpql.append("");
		jpql.append("");
		Query query = em.createQuery(jpql.toString());
		query.setParameter("listName", name);
		query.getResultList();
		return null;
	}
}
