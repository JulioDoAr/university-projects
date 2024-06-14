package com.mdad.services;

import com.mdad.database.DBConnection;
import com.mdad.models.User;

public class LoginService {

	private static LoginService instance;
	private FriendshipService friendshipService;
	private DBConnection dbConnection;
	private User user;

	private LoginService() {
		user = null;
		friendshipService = FriendshipServiceImpl.getInstance();
		dbConnection = DBConnection.getInstance();
	}

	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
		}
		return instance;
	}

	public boolean login(String name, String password) {
		user = friendshipService.login(name, password);
		return isLoggedIn();
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public boolean logout() {
		user = null;
		return true;
	}

	public boolean register(String username, String password) {
		// El usuario ya existe previamente
		if (dbConnection.getUser(username) != null) {
			return false;
		}

		user = friendshipService.register(username, username + "@example.com", password);
		return isLoggedIn();
	}

	public User getCurrentUser() {
		return user;
	}
}
