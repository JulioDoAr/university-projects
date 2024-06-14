package com.mdad.ui;

import com.mdad.services.LoginService;

public class LogoutMenu {

	private LoginService loginService;

	public LogoutMenu() {
		loginService = LoginService.getInstance();
	}

	public void showLogout() {
		System.out.println("Logging out...");
		if (loginService.logout()) {
			System.out.println("Logout successful.");
		} else {
			System.out.println("Logout failed.");
		}
		System.out.println();
	}
}
