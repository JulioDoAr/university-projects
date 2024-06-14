package com.mdad.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

	private static final int EXIT_SIGNAL = 0;
	private static final int NOT_VALID_SIGNAL = -1;

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static int readInput(int max) {
		int choice = NOT_VALID_SIGNAL;
		while (choice == NOT_VALID_SIGNAL) {
			try {
				String input = reader.readLine();
				if (input != null)
					choice = Integer.parseInt(input);
			} catch (NumberFormatException | IOException e) {
				System.out.println("Invalid input. Please enter a number.");
			}
			if (choice < 0 || choice > max) {
				System.out.println("Invalid input. Please enter a number between 1 and " + max + ". 0 to exit.");
				choice = NOT_VALID_SIGNAL;
			}
		}
		return choice;
	}

	public static String readLine() {
		String input = null;
		try {
			input = reader.readLine();
		} catch (IOException e) {
			System.out.println("Error reading input.");
		}
		return input;
	}

	public static int readNumber() {
		int number = NOT_VALID_SIGNAL;
		try {
			String input = reader.readLine();
			if (input != null && !input.isEmpty())
				number = Integer.parseInt(input);
		} catch (NumberFormatException | IOException e) {
			System.out.println("Invalid input. Please enter a number.");
		}
		return number;
	}
}
