package statemachine.state;

import java.io.IOException;

import service.IOService;
import service.KeyService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Generate a new pair of keys
 */
public class GenerateKeysState implements State {

	/** The key types. */
	private final String[] keyTypes = { "DSA", "RSA" };

	/** The iOservice. */
	private final IOService iOService;

	/** The key service. */
	private final KeyService keyService;

	/**
	 * Instantiates a new instance.
	 */
	public GenerateKeysState() {
		iOService = IOService.getInstance();
		keyService = KeyService.getInstance();
	}

	/**
	 * Check if keys already exist.
	 *
	 * @return true, if successful
	 */
	private boolean checkIfKeysAlreadyExist() {
		boolean keysExist = true;
		// Check if keys already exist
		if (keyService.getKeys() != null) {
			System.out.println("Ya existen unas claves. Está seguro de querer sobreescribirlas? (s/n) ");
			try {
				String response = iOService.readLine();
				keysExist = response.toLowerCase().startsWith("s");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return keysExist;
	}

	/**
	 * Menu to generate a new pair of keys. Can be selected between all the keyTypes
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		// Si las claves existen y no quiere sobreescribirlas -> Volver
		if (!checkIfKeysAlreadyExist())
			return;

		// Ask for keys type
		iOService.showOptions(keyTypes);
		int option = iOService.askForNumber(keyTypes);

		keyService.generateKeys(keyTypes[option]);
		System.out.println("Claves generadas correctamente.");
	}

}
