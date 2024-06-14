package com.mdad.ui;

import java.util.List;

import com.mdad.models.User;
import com.mdad.services.FriendshipService;
import com.mdad.services.FriendshipServiceImpl;
import com.mdad.services.LoginService;

public class BlockUserMenu {

	private FriendshipService friendshipService;
	private LoginService loginService;

	public BlockUserMenu() {
		friendshipService = FriendshipServiceImpl.getInstance();
		loginService = LoginService.getInstance();
	}

	public void show() {
		System.out.println("Blocked users:");
		List<User> blockedUsers = friendshipService.getBlockedUsers(loginService.getCurrentUser());
		if (blockedUsers.isEmpty()) {
			System.out.println("No blocked users.");
		} else {
			for (User user : blockedUsers) {
				System.out.println(" - " + user.getName());
			}
		}
		System.out.println();

		System.out.println("If you want to block a new user, write its name. If not, leave empty:");
		String username = Utils.readLine();
		if(!username.isEmpty()){
			if(!username.equals(loginService.getCurrentUser().getName()) && friendshipService.checkUser(username)){
				if (friendshipService.blockUser(loginService.getCurrentUser(), username)) {
					System.out.println("User blocked successfully.");
				} else {
					System.out.println("User not found.");
				}
			} else
				System.out.println("Failed to block user. User name invalid!");
		} else
			System.out.println("Aborting...");
		System.out.println();
	}
}
