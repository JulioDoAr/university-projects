package com.mdad.ui;

import com.mdad.services.LoginService;

public class RegisterMenu {

	private LoginService loginService;

	public RegisterMenu() {
		loginService = LoginService.getInstance();
	}

	public void showRegister() {
		System.out.println("Register");
		System.out.println("Enter your username: ");
		String username = Utils.readLine();
		System.out.println("Enter your password: ");
		String password = Utils.readLine();
		System.out.println("Registering...");
		if (loginService.register(username, password)) {
			System.out.println("Registration successful.");
		} else {
			System.out.println("Registration failed.");
		}
		System.out.println();
	}

}
