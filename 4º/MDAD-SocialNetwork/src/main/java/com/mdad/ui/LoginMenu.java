package com.mdad.ui;

import com.mdad.services.LoginService;

public class LoginMenu {

	private LoginService loginService;

	public LoginMenu() {
		loginService = LoginService.getInstance();
	}

	public void showLogin() {
		System.out.println("Login");
		System.out.println("Enter your username: ");
		String username = Utils.readLine();
		System.out.println("Enter your password: ");
		String password = Utils.readLine();
		System.out.println("Logging in...");
		if (loginService.login(username, password)) {
			System.out.println("Login successful.");
		} else {
			System.out.println("Login failed.");
		}
		System.out.println();
	}

}
