package com.doar.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends ServerSocket {

	private int accepted;

	public Server(int port) throws IOException {
		super(port);
		System.out.println("Server created at port " + port);
		accepted = 1;
	}

	public void listen() throws IOException {
		System.out.println("Server started");
		while (true) {
			// client's connection request should be processed and acknowledged
			Socket clientSocket = this.accept();
			new ClientHandler(clientSocket, accepted).start();
			accepted++;
		}
	}
}