package com.mdad.ui;

import java.util.List;

import com.mdad.models.User;
import com.mdad.services.FriendshipService;
import com.mdad.services.FriendshipServiceImpl;
import com.mdad.services.LoginService;

public class MyFriendsMenu {

	private FriendshipService friendshipService;
	private LoginService loginService;

	public MyFriendsMenu() {
		friendshipService = FriendshipServiceImpl.getInstance();
		loginService = LoginService.getInstance();
	}

	public void showMyFriends() {
		System.out.println("My Friends");
		System.out.println("Showing my friends...");
		List<User> friends = friendshipService.getFriends(loginService.getCurrentUser());

		System.out.println();
		if (friends.isEmpty()) {
			System.out.println("It's just you...\n");
		} else {
			System.out.println("Users found:");
			for (int i = 0; i < friends.size(); i++) {
				System.out.println("["+(i + 1) + "] " + friends.get(i).getName());
			}
			System.out.println("\nDo you want to remove a friend? If yes, select its number or choose 0 to exit:");
			int selection = Utils.readInput(friends.size());
			if(selection > 0){
				String username = friends.get(selection-1).getName();
				if (!username.isEmpty()) {
					if (friendshipService.removeFriend(loginService.getCurrentUser().getName(), username)) {
						System.out.println("Friend removed successfully.");
					} else {
						System.out.println("User not found.");
					}
				} else {
					System.out.println("Invalid user!");
				}
			} else
				System.out.println("Aborting...");
		}

		System.out.println();
	}

}
