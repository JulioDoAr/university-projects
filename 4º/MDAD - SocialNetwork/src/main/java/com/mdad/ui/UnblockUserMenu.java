package com.mdad.ui;

import com.mdad.models.User;
import com.mdad.services.FriendshipService;
import com.mdad.services.FriendshipServiceImpl;
import com.mdad.services.LoginService;

import java.util.List;

public class UnblockUserMenu {

	private FriendshipService friendshipService;
	private LoginService loginService;

	public UnblockUserMenu() {
		friendshipService = FriendshipServiceImpl.getInstance();
		loginService = LoginService.getInstance();
	}

	public void show() {
		System.out.println("Blocked Users");
		System.out.println("Showing blocked users...");
		List<User> blocked = friendshipService.getBlockedUsers(loginService.getCurrentUser());

		System.out.println();
		if (blocked.isEmpty()) {
			System.out.println("You're friends with everyone! :)\n");
		} else {
			System.out.println("Users found:");
			for (int i = 0; i < blocked.size(); i++) {
				System.out.println("["+(i + 1) + "] " + blocked.get(i).getName());
			}
			System.out.println("\nDo you want to unblock someone? If yes, select its number or choose 0 to exit:");
			int selection = Utils.readInput(blocked.size());
			if(selection > 0){
				String username = blocked.get(selection-1).getName();
				if (!username.isEmpty()) {
					if (friendshipService.unblockUser(loginService.getCurrentUser(), username)) {
						System.out.println("User unblocked successfully.");
						System.out.println("Keep in mind that if the user has blocked you, your social interactions will remain restricted.");
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
