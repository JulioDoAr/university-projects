package com.doar.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * - the client will send a connection request to the server - the user should
 * be able to send text messages to the server and should display server's
 * response - after this message exchange, the client should close the
 * connection to the server
 * 
 * @author julio
 *
 */
public class CommandLineInterface {

	private final String server;
	private final int port;

	public CommandLineInterface(String server, int port) {
		super();
		this.server = server;
		this.port = port;
	}

	public void execute() {
		try {
			// Connect to server socket
			Socket socket = new Socket(server, port);
			System.out.println("Welcome to the CLI. To exit type 'exit'.");

			// Set up input and output streams
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Read user input and send to server
			String input;
			boolean exit = false;
			while (!exit) {
				input = userIn.readLine();
				serverOut.println(input);
				if (input.toLowerCase().compareTo("exit") == 0) {
					exit = true;
					System.out.println("Exiting...");
				} else {
					System.out.println(serverIn.readLine());
				}
			}

			// Clean up
			userIn.close();
			serverIn.close();
			serverOut.close();
			socket.close();
			System.out.println("Exited.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
