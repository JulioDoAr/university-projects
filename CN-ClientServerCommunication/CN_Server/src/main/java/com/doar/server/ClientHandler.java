package com.doar.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
	private Socket clientSocket;
	private final int id;

	public ClientHandler(Socket socket, int id) {
		this.clientSocket = socket;
		this.id = id;
		printMessage(String.format("Connection accepted. IP=%s, port=%d", socket.getInetAddress().toString(),
				socket.getPort()));
	}

	@Override
	public void run() {
		try {
			// Handle client connection here
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

			// Read input from client
			String inputLine;
			boolean exit = false;
			while (!exit) {
				inputLine = in.readLine();
				printMessage("Received \""+ inputLine +"\"");
				
				if (inputLine.toLowerCase().compareTo("exit") == 0) {
					exit = true;
					printMessage("Closing connection...");
				} else {
					out.println("Server received: " + inputLine);
				}
			}

			// Clean up
			in.close();
			out.close();
			clientSocket.close();
			printMessage("Connection closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printMessage(String message) {
		System.out.println("Client " + id + ": " + message);
	}
}