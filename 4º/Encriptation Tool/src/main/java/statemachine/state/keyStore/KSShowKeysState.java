package statemachine.state.keyStore;

import service.IOService;
import service.KeyStoreService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Manages the operation to show all the alias in the key store.
 */
public class KSShowKeysState implements State {

	/** The service. */
	KeyStoreService service;

	/**
	 * Instantiates a new state.
	 */
	public KSShowKeysState() {
		service = KeyStoreService.getInstance();
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
		String psw = IOService.getInstance().askForPassword();

		service.showAllKeys(psw.toCharArray());
	}

}
