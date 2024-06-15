package statemachine.state.keyStore;

import service.IOService;
import service.KeyStoreService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Manages the deletion of a key from the key store
 */
public class KSDeleteKeysState implements State {

	/** The KeyStoreService. */
	KeyStoreService service;

	/** The IOService. */
	IOService iOService;

	/**
	 * Instantiates a new state.
	 */
	public KSDeleteKeysState() {
		service = KeyStoreService.getInstance();
		iOService = IOService.getInstance();
	}

	/**
	 * Handle.
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		if (!service.isInited()) {
			System.out.println("No se ha seleccionado un almacen de claves.");
			return;
		}

		String[] keyArray = service.getAllKeysAsArray();

		iOService.showOptions(keyArray);
		int selectedOption = iOService.askForNumber(keyArray);

		service.deleteKey(keyArray[selectedOption]);

	}
}
