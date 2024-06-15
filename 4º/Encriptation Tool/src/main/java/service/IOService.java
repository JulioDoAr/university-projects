package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;

/**
 * Singleton. Methods about I/O with the user
 */
public class IOService {

	/** The instance. */
	private static IOService instance = null;

	/** The console reader. */
	private BufferedReader consoleReader;

	/**
	 * Instantiates a new IO service.
	 */
	private IOService() {
		consoleReader = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * Gets the single instance of IOService.
	 *
	 * @return the IOService instance
	 */
	public static IOService getInstance() {
		if (instance == null)
			instance = new IOService();
		return instance;
	}

	/**
	 * Read the next line of default input. If the user writes "exit", ask if want
	 * to finish the application.
	 *
	 * @return the line read
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String readLine() throws IOException {
		String input = consoleReader.readLine();
		if ("salir".equalsIgnoreCase(input)) {
			System.out.println("Salir? [S/N]");
			String confirmation = consoleReader.readLine();
			if (confirmation.equalsIgnoreCase("s"))
				System.exit(0);
		} else {
		}
		return input;
	}

	/**
	 * Chech if an url exist and is a file.
	 *
	 * @param url to the file
	 * @return true if is a file, false if not
	 */
	public boolean exist(String url) {
		File f = new File(url);
		return f.exists() && !f.isDirectory();
	}

	/**
	 * Ask the user to input a valid number to select one option.
	 *
	 * @param options number of options available
	 * @return Number selected between 0 and options.lenght
	 */
	public int askForNumber(String[] options) {
		int election = -1;
		boolean valid = false;
		do
			try {
				String name = readLine();
				election = Integer.parseInt(name);
				if (election < 0 || election > options.length - 1)
					throw new IndexOutOfBoundsException();
				valid = true;
			} catch (IOException e) {
				e.printStackTrace();
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("No se ha introducido un número.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("El número se sale del rango.");
			}
		while (!valid);

		return election;
	}

	/**
	 * Ask the user to input a valid number to select one option.
	 *
	 * @param options number of options available
	 * @return Number selected between 0 and options.lenght
	 */
	public int askForNumber(int range) {
		int election = -1;
		boolean valid = false;
		do
			try {
				String name = readLine();
				election = Integer.parseInt(name);
				if (election < 0 || election > range)
					throw new IndexOutOfBoundsException();
				valid = true;
			} catch (IOException e) {
				e.printStackTrace();
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("No se ha introducido un número.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("El número se sale del rango.");
			}
		while (!valid);

		return election;
	}

	/**
	 * Print a list of options with one number for each.
	 *
	 * @param options List of options to print
	 */
	public void showOptions(String[] options) {
		for (int i = 0; i < options.length; i++) {
			System.out.println(String.format("%d. %s", i, options[i]));
		}
		System.out.println(String.format("Escribe un número entre 0 y %d", options.length - 1));
	}

	/**
	 * Ask the user to write the url of a file.
	 *
	 * @param question Question to ask to the user
	 * @return a valid File structure
	 */
	public File askForFile(String question) {
		System.out.println(question);

		File file = null;
		boolean valid = false;
		do {
			try {
				String url = readLine();
				file = new File(url);
				if (!file.exists()) {
					System.out.println("El archivo no existe.");
				} else if (file.isDirectory()) {
					System.out.println("El archivo es una carpeta. Debe ser un fichero.");
				} else {
					valid = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!valid);
		return file;
	}

	/**
	 * Ask the user to write the url of a file.
	 *
	 * @param question Question to ask to the user
	 * @return a valid File structure
	 */
	public String ask(String question) {
		System.out.println(question);
		String input = null;
		try {
			input = readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * Ask the user to insert two times a password and check if both are equals.
	 *
	 * @return A valid password
	 */
	public String askForDuplicatedPassword() {
		boolean match = false;
		String pw1 = null, pw2 = null;
		do {
			try {
				System.out.print("Contraseña: ");
				pw1 = readLine();
				System.out.print("Repita la contraseña: ");
				pw2 = readLine();
				if (pw1.equals(pw2)) {
					match = true;
				} else {
					System.out.println("LAs contraseñas no coinciden. Intentelo de nuevo.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!match);
		return pw1;
	}

	/**
	 * Ask the user to insert a password.
	 *
	 * @return The password written
	 */
	public String askForPassword() {
		String pw1 = null;
		try {
			System.out.print("Contraseña: ");
			pw1 = readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pw1;
	}

	/**
	 * Show all the data of a KeyPair
	 * 
	 * @param keyPair to show data
	 */
	public void showKey(KeyPair keyPair) {
		System.out.println("Private:" + keyPair.getPrivate().toString());
		System.out.println("Public:" + keyPair.getPublic().toString());
	}
}
