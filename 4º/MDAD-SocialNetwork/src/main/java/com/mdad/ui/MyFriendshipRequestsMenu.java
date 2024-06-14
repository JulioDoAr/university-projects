package com.mdad.ui;

import java.util.List;

import com.mdad.models.User;
import com.mdad.services.FriendshipService;
import com.mdad.services.FriendshipServiceImpl;
import com.mdad.services.LoginService;

public class MyFriendshipRequestsMenu {

	private FriendshipService friendshipService;
	private LoginService loginService;

	public MyFriendshipRequestsMenu() {
		friendshipService = FriendshipServiceImpl.getInstance();
		loginService = LoginService.getInstance();
	}

	public void showMyFriendshipRequests() {
		System.out.println("My Friendship Requests");
		System.out.println("Showing my friendship requests...\n");

		List<User> friends = friendshipService.getFriendRequests(loginService.getCurrentUser().getName());

		if (friends.isEmpty()) {
			System.out.println("No petitions.");
		} else {
			System.out.println("Users found:");
			for (int i = 0; i < friends.size(); i++) {
				User friend = friends.get(i);
				System.out.println("["+i + "] " + friend.getName());
			}
		}

		System.out.println(
				"\nDo you want to accept a friendship request? If yes, select its number, if not, leave empty: ");
		int response = Utils.readNumber();
		if (response > -1 && response < friends.size()) {
			if (friendshipService.acceptFriendRequest(loginService.getCurrentUser().getName(),
					friends.get(response).getName())) {
				System.out.println("Friendship request accepted.");
			} else {
				System.out.println("Error accepting request.");
			}
		}
	}
}
