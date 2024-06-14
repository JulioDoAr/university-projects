package com.doar.main;

import java.io.IOException;

import com.doar.server.Server;

public class MainServer {

	// the server should create a listening socket bound to port 8080 (for example)
	private static final int PORT_NUMBER = 8080;

	public static void main(String[] args) throws IOException {
		Server server = new Server(PORT_NUMBER);
		server.listen();
	}
}
