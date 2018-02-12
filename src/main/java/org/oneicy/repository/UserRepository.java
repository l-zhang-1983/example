package org.oneicy.repository;

import org.oneicy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustomized {
	Collection<User> getUsersByUserNameLike(String userName);
}
