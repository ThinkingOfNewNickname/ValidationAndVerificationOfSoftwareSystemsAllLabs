package com.onlinegamestore.onlinegamestore.services;

import com.onlinegamestore.onlinegamestore.dao.UserDao;

import java.util.List;

import com.onlinegamestore.onlinegamestore.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public List<User> getUsers(){
		return this.userDao.getAllUser();
	}
	
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
