package statemachine.state.keyStore;

import java.security.KeyPair;

import service.IOService;
import service.KeyService;
import service.KeyStoreService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Manages the import of a key from the key store
 */
public class KSImportKeyState implements State {

	/** The service. */
	KeyStoreService service;

	/** The IOservice. */
	IOService iOService;

	/**
	 * Instantiates a new state.
	 */
	public KSImportKeyState() {
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

		String psw = IOService.getInstance().askForPassword();

		KeyPair kp = service.loadKey(keyArray[selectedOption], psw.toCharArray());

		if (kp != null) {
			KeyService.getInstance().setKeys(kp);
			System.out.println(
					"OK: Claves cargadas correctamente. Estas claves se pueden utilizar en toda la aplicación en general.");
			IOService.getInstance().showKey(kp);
		} else
			System.out.println("ERR: Contraseña incorrecta");

	}
}
