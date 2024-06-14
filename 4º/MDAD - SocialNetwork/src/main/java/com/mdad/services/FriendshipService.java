package com.mdad.services;

import java.util.List;

import com.mdad.models.User;

public interface FriendshipService {

	/**
	 * Login with the given name and password
	 * 
	 * @param name
	 * @param password
	 * @return User object if login is successful, null otherwise
	 */
	public User login(String name, String password);

	/**
	 * Register a new user with the given name, email and password
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @return User object if registration is successful, null otherwise
	 */
	public User register(String name, String email, String password);

	/**
	 * Search for users by name
	 * 
	 * @param name
	 * @return List of users that contains the given name
	 */
	public List<User> searchByName(String username, String name);

	/**
	 * Get friends of the given user
	 * 
	 * @param name
	 * @return List of friends of the given user
	 */
	public List<User> getFriends(User user);

	/**
	 * Get friend requests of the given user
	 * 
	 * @param name
	 * @return List of friend requests of the given user
	 */
	public List<User> getFriendRequests(String name);

	/**
	 * Send a friend request from sender to receiver
	 * 
	 * @param sender
	 * @param receiver
	 * @return true if the friend request is sent successfully, false otherwise
	 */
	public boolean sendFriendRequest(String sender, String receiver);

	/**
	 * Accept a friend request from sender to receiver
	 * 
	 * @param sender
	 * @param receiver
	 * @return true if the friend request is accepted successfully, false otherwise
	 */
	public boolean acceptFriendRequest(String sender, String receiver);

	/**
	 * Reject a friend request from sender to receiver
	 * 
	 * @param sender
	 * @param receiver
	 * @return true if the friend request is rejected successfully, false otherwise
	 */
	public boolean rejectFriendRequest(String sender, String receiver);

	/**
	 * Remove a friend from the friend list of the given user
	 * 
	 * @param sender
	 * @param receiver
	 * @return true if the friend is removed successfully, false otherwise
	 */
	public boolean removeFriend(String sender, String receiver);

	/**
	 * Blocks the given user
	 * 
	 * @param user     The user who wants to block
	 * @param username The username of the user to block
	 * @return true if the user is blocked successfully, false otherwise
	 */
	public boolean blockUser(User user, String toBlock);

	/**
	 * Unblocks the given user
	 *
	 * @param user		The user who wants to unblock someone
	 * @param toUnblock	The username of the user to unblock
	 * @return true if the user is unblocked successfully, false otherwise
	 */
	public boolean unblockUser(User user, String toUnblock);

	/**
	 * Get the list of blocked users
	 * 
	 * @param user The user who wants to see blocked users
	 * @return List of blocked users
	 */
	public List<User> getBlockedUsers(User user);

	/**
	 * Unblock the given user
	 * 
	 * @param user The user who wants to unblock
	 * @return true if the user is unblocked successfully, false otherwise
	 */
	public boolean deleteUser(User user);

	/**
	 * Checks if a user exists
	 *
	 * @param username Name of the user to look for
	 * @return true if the user exists, false otherwise
	 */
	public boolean checkUser(String username);

	/**
	 * Checks if the user is blocked by the issuer
	 *
	 * @param user		User who issues the check
	 * @param username	User to check for block
	 * @return true if the username provided is blocked by the user, false otherwise
	 */
	public boolean isBlocked(User user, String username);

	/**
	 * Check if there's the user is blocked by the username provided
	 *
	 * @param user		The user who checks
	 * @param username	The user to check
	 * @return true if there's a block towards the user, false otherwise
	 */
	public boolean isBlockedBy(User user, String username);

	/**
	 * Checks if there's any block edge between two users
	 *
	 * @param user		The user who sends the query
	 * @param username	The user to check blockages with
	 * @return true if there's any block, false otherwise
	 */
	public boolean theresBlocks(User user, String username);
}
