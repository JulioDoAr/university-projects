package com.mdad.ui;

import com.mdad.services.LoginService;

public class MainMenu {

	private LoginService loginService;

	public MainMenu() {
		loginService = LoginService.getInstance();
	}

	private void printMainMenu() {
		System.out.println("Welcome to MDAD"+ ((loginService.isLoggedIn()) ? (" " + loginService.getCurrentUser().getName()) : "") +"!");
		System.out.println("0. Exit");
		System.out.println("1. Register");
		if (loginService.isLoggedIn()) {
			System.out.println("2. Logout");
			System.out.println("3. Search Users");
			System.out.println("4. My Friends");
			System.out.println("5. My friendship requests");
			System.out.println("6. Send friendship request");
			System.out.println("7. Block User");
			System.out.println("8. Unblock User");
			System.out.println("9. Delete account");
		} else
			System.out.println("2. Login");
		System.out.println("Enter your choice: ");
	}

	public void showMenu() {
		int choice = -1;

		while (choice != 0) {
			printMainMenu();
			choice = Utils.readInput(9);
			switch (choice) {
			case 1: // Register
				RegisterMenu register = new RegisterMenu();
				register.showRegister();
				break;
			case 2: // Login/Logout
				if (loginService.isLoggedIn()) {
					LogoutMenu logout = new LogoutMenu();
					logout.showLogout();
				} else {
					LoginMenu login = new LoginMenu();
					login.showLogin();
				}
				break;
			case 3: // Search Users
				if (loginService.isLoggedIn()) {
					SearchUsersMenu searchUsers = new SearchUsersMenu();
					searchUsers.showSearchUsers();
				} else {
					System.out.println("Please login first.");
				}
				break;
			case 4: // My Friends
				if (loginService.isLoggedIn()) {
					MyFriendsMenu myFriends = new MyFriendsMenu();
					myFriends.showMyFriends();
				} else {
					System.out.println("Please login first.");
				}
				break;
			case 5: // My friendship requests
				if (loginService.isLoggedIn()) {
					MyFriendshipRequestsMenu myFriendshipRequests = new MyFriendshipRequestsMenu();
					myFriendshipRequests.showMyFriendshipRequests();
				} else {
					System.out.println("Please login first.");
				}
				break;
			case 6: // Send friendship request
				if (loginService.isLoggedIn()) {
					SendFriendshipRequestMenu sendFriendshipRequest = new SendFriendshipRequestMenu();
					sendFriendshipRequest.show();
				} else {
					System.out.println("Please login first.");
				}
				break;
				case 7: // Block User
					if (loginService.isLoggedIn()) {
						BlockUserMenu blockUser = new BlockUserMenu();
						blockUser.show();
					} else {
						System.out.println("Please login first.");
					}
				break;
				case 8: // Unblock User
					if (loginService.isLoggedIn()) {
						UnblockUserMenu unblockUser = new UnblockUserMenu();
						unblockUser.show();
					} else {
						System.out.println("Please login first.");
					}
				break;
			case 9: // Delete account
				if (loginService.isLoggedIn()) {
					DeleteAccountMenu deleteAccount = new DeleteAccountMenu();
					deleteAccount.show();
				} else {
					System.out.println("Please login first.");
				}
				break;

			default:
				System.out.println("Invalid choice.");
				break;
			}
			if(choice > 2) {
				System.out.println("Press enter to return to the main menu.");
				Utils.readLine();
			}
		}
		System.out.println("Goodbye!");

	}
}
