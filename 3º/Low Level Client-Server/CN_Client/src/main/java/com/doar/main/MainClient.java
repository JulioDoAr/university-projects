package com.doar.main;

import com.doar.cli.CommandLineInterface;

public class MainClient {

	// The user should be able to specify the IP address of the server and the
	// server port(as command line arguments)
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: CommandLineInterface <serverAddress> <serverPort>");
			System.exit(1);
		}

		String serverAddress = args[0];
		int serverPort = -1;
		try {
			serverPort = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("The serverPort needs to be a number");
			System.exit(1);
		}
		new CommandLineInterface(serverAddress, serverPort).execute();
		System.exit(0);
	}
}
