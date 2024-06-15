package statemachine.state.keyStore;

import java.io.File;

import service.IOService;
import service.KeyStoreService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: The main menu of key store.
 */
public class KSSelectionState implements State {

	/** The service. */
	KeyStoreService service;

	/**
	 * Instantiates a new state.
	 */
	public KSSelectionState() {
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

		File keyStoreFile = IOService.getInstance().askForFile("Introduzca la ruta del almacen de claves.");
		String psw = IOService.getInstance().askForPassword();

		boolean init = service.init(keyStoreFile.getAbsolutePath(), psw.toCharArray());
		if (init)
			System.out.println("El fichero se ha abierto correctamente.");
		else
			System.out.println("Error al abrir el fichero.");

	}

}
