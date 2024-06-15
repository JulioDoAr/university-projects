package statemachine.state;

import java.io.File;
import java.io.IOException;

import service.IOService;
import service.KeyService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Save the public and private key from a file
 */
public class SaveKeysState implements State {

	/** The key service. */
	KeyService keyService;

	/** The IOservice. */
	IOService ioService;

	/**
	 * Instantiates a new instance.
	 */
	public SaveKeysState() {
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
		// Check if keys exist
		if (keyService.getKeys() == null) {
			System.out.println("No existen claves para exportar.");
			return;
		}

		// Ask for file
		System.out.println("¿Donde quieres guardar las claves?");
		String path;
		try {
			path = ioService.readLine();
			File file = new File(path);
			keyService.saveKey(file);
			System.out.println("Claves guardadas correctamente:");
			System.out.println(file.getAbsolutePath());
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Error al guardar las claves.");
	}

}
