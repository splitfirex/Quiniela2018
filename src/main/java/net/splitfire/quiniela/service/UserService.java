package net.splitfire.quiniela.service;

import java.util.List;

import net.splitfire.quiniela.model.User;


public interface UserService {
	
	User findByName(String name);
	
	void saveUser(User user);

	List<User> getUserList();
	
}
