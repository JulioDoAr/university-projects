package exceptions;

public class ClientNotFoundException extends Exception {

	public ClientNotFoundException() {
		super("Client does not exist.");
	}
}
