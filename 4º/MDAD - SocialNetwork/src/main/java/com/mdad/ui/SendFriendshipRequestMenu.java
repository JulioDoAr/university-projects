package com.mdad.ui;

import com.mdad.services.FriendshipService;
import com.mdad.services.FriendshipServiceImpl;
import com.mdad.services.LoginService;

public class SendFriendshipRequestMenu {

	private FriendshipService friendshipService;
	private LoginService loginService;

	public SendFriendshipRequestMenu() {
		friendshipService = FriendshipServiceImpl.getInstance();
		loginService = LoginService.getInstance();
	}

	public void show() {
		System.out.println("Send Friendship Request");
		System.out.println("Enter the name of the user you want to send a friendship request (leave empty to exit): ");
		String name = null;
		do{
			name = Utils.readLine();
			if(name.equals(loginService.getCurrentUser().getName())) {
				System.out.println("You're already your best friend! Try someone else.");
				name = null;
			}
		} while(name==null);
		if(!name.isEmpty()){
			// Check if the user exists
			if(friendshipService.checkUser(name)){
				// Check if either one blocked the other
				if(!friendshipService.theresBlocks(loginService.getCurrentUser(), name)){
					System.out.println("Sending friendship request...");
					if (friendshipService.sendFriendRequest(loginService.getCurrentUser().getName(), name)) {
						System.out.println("Friendship request sent.");
					} else {
						System.out.println("Friendship request failed.");
					}
				} else
					System.out.println("Friendship request failed. Interactions with this user are restricted.");
			} else
				System.out.println("Friendship request failed. User name invalid!");
		}else {
			System.out.println("Aborting...");
		}
		System.out.println();
	}
}
