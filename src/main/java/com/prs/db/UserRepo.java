package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.User;
import com.prs.model.UserLogin;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByUsernameAndPassword(String username, String password);
}
