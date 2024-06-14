package com.mdad.ui;

import java.util.List;

import com.mdad.models.User;
import com.mdad.services.FriendshipService;
import com.mdad.services.FriendshipServiceImpl;
import com.mdad.services.LoginService;

public class SearchUsersMenu {

	private FriendshipService friendshipService;
	private LoginService loginService;

	public SearchUsersMenu() {
		friendshipService = FriendshipServiceImpl.getInstance();
		loginService = LoginService.getInstance();
	}

	public void showSearchUsers() {
		System.out.println("Search Users");
		System.out.println("Enter the name to filter: ");
		String name = Utils.readLine();
		System.out.println("Searching...");
		List<User> users = friendshipService.searchByName(loginService.getCurrentUser().getName(), name);

		System.out.println();
		if (users.isEmpty()) {
			System.out.println("No users found.");
		} else {
			System.out.println("Users found:");
			for (User user : users) {
				System.out.println(" - " + user.getName());
			}
		}
		System.out.println();
	}
}
