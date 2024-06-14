package com.mdad.services;

import java.util.List;

import com.mdad.database.DBConnection;
import com.mdad.models.User;

public class FriendshipServiceImpl implements FriendshipService {

	private static FriendshipServiceImpl instance;
	private final DBConnection dbConnection;

	private FriendshipServiceImpl() {
		dbConnection = DBConnection.getInstance();
	}

	public static FriendshipServiceImpl getInstance() {
		if (instance == null) {
			instance = new FriendshipServiceImpl();
		}
		return instance;
	}

	@Override
	public User login(String name, String password) {
		User user = dbConnection.getUser(name);
		if (user == null) {
			return null;
		} else if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}

	@Override
	public User register(String name, String email, String password) {
		User user = new User(name, email, password);
		if (dbConnection.createUser(user))
			return user;
		return null;
	}

	@Override
	public List<User> searchByName(String username, String name) {
		return dbConnection.searchByName(username, name);
	}

	@Override
	public List<User> getFriends(User user) {
		return dbConnection.getFriends(user.getName());
	}

	@Override
	public List<User> getFriendRequests(String name) {
		return dbConnection.getFriendRequests(name);
	}

	@Override
	public boolean sendFriendRequest(String sender, String receiver) {
		return dbConnection.sendFriendRequest(sender, receiver);
	}

	@Override
	public boolean acceptFriendRequest(String sender, String receiver) {
		return dbConnection.acceptFriendRequest(sender, receiver);
	}

	@Override
	public boolean rejectFriendRequest(String sender, String receiver) {
		return dbConnection.rejectFriendRequest(sender, receiver);
	}

	@Override
	public boolean removeFriend(String sender, String receiver) {
		return dbConnection.removeFriend(sender, receiver);
	}

	@Override
	public boolean blockUser(User user, String toBlock) {
		return dbConnection.blockUser(user.getName(), toBlock);
	}

	@Override
	public boolean unblockUser(User user, String toUnblock) {return dbConnection.unblockUser(user.getName(), toUnblock); }

	@Override
	public List<User> getBlockedUsers(User user) {
		return dbConnection.getBlockedUsers(user.getName());
	}

	@Override
	public boolean deleteUser(User user) {
		return dbConnection.deleteUser(user.getName());
	}

	@Override
	public boolean checkUser(String username){ return dbConnection.checkUser(username); }

	@Override
	public boolean isBlocked(User user, String username){ return dbConnection.isBlocked(user.getName(), username); }

	@Override
	public boolean isBlockedBy(User user, String username){ return dbConnection.isBlockedBy(user.getName(), username); }

	@Override
	public boolean theresBlocks(User user, String username) { return dbConnection.theresBlocks(user.getName(), username); }
}
