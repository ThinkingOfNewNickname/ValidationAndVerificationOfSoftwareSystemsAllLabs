package com.entryverificationsystem.entryverificationsystem.services;

import com.entryverificationsystem.entryverificationsystem.dao.UserDao;

import com.entryverificationsystem.entryverificationsystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public User addUser(User user) {
		return this.userDao.saveUser(user);
	}

	public boolean hasUser(String username) {
		return this.userDao.hasUser(username);
	}

	public User validateUser(String username, String password) {
		return this.userDao.validateUser(username, password);
	}

	
}
