# Computer Networks

Universitatea de Vest din Timișoara (Romania)
2022-2023

## Communication between client and server

### Knowledge

- Java
- Maven
- Server-Client architecture

### Requirements for the client 

	- The user should be able to specify the IP address of the server and the server port (as command line arguments)
	- The client will send a connection request to the server 
	- The user should be able to send text messages to the server and should display server's response
	- After this message exchange, the client should close the connection to the server

### Requirements for the server

	- The server should create a listening socket bound to port 8080 (for example)
	- Client's connection request should be processed and acknowledged 
	- When a client's message is received, the server will display the message and the client info (IP address and port) 
	- The server should respond to client's messages
	- Client's disconnect request should be processed and acknowledged 
	- The server should be available for all clients, until explicitly closed by the user
