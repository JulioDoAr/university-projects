package com.mdad.ui;

import com.mdad.services.FriendshipService;
import com.mdad.services.FriendshipServiceImpl;
import com.mdad.services.LoginService;

public class DeleteAccountMenu {

	private FriendshipService friendshipService;
	private LoginService loginService;

	public DeleteAccountMenu() {
		friendshipService = FriendshipServiceImpl.getInstance();
		loginService = LoginService.getInstance();
	}

	public void show() {
		System.out.println("Are you sure you want to delete your account? (yes/no | y/n)");
		String answer = Utils.readLine();
		if (answer.equals("yes") || answer.equals("y")) {
			if (friendshipService.deleteUser(loginService.getCurrentUser())) {
				System.out.println("Account deleted successfully.");
				loginService.logout();
			} else {
				System.out.println("Error deleting account.");
			}
		}
		System.out.println();
	}

}
