package statemachine.state;

import java.io.File;
import java.io.IOException;

import service.IOService;
import service.KeyService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Load the public and private key from a file
 */
public class LoadKeysState implements State {

	/** The key service. */
	KeyService keyService;

	/** The IOservice. */
	IOService ioService;

	/**
	 * Instantiates a new load keys state.
	 */
	public LoadKeysState() {
		keyService = KeyService.getInstance();
		ioService = IOService.getInstance();
	}

	/**
	 * Check if keys exist and ask the user to replace the actual keys.
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		// Comprueba si existen claves actuales
		if (keyService.getKeys() != null) {
			// Si existen, informar de que se van a sobreescribir
			try {
				System.out.println("Se van a sobreescribir las claves actuales. Desea continuar? (s/n)");
				String response = ioService.readLine();
				if (!response.toLowerCase().startsWith("s"))
					return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Seleccionar fichero de claves
		File file = ioService.askForFile("Seleccione el fichero del que cargar las claves.");

		// Cargar claves
		keyService.loadKeys(file);
		System.out.println("Claves cargadas:");
		System.out.println("Public: " + keyService.getKeys().getPublic().toString());
		System.out.println("Private: " + keyService.getKeys().getPrivate().toString());

	}

}
