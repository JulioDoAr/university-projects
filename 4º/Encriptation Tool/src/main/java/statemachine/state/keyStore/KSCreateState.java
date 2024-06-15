package statemachine.state.keyStore;

import java.io.File;

import service.IOService;
import service.KeyStoreService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Manages the creation of a new Key Store.
 */
public class KSCreateState implements State {

	/**
	 * Handle.
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		String file = IOService.getInstance().ask("Seleccione la ruta donde generar el almacen de claves.");
		String password = IOService.getInstance().askForDuplicatedPassword();

		File keyStore = KeyStoreService.getInstance().createKeyStore(file, password.toCharArray());

		if (keyStore != null) {
			System.out.println("Almacen de claves creado correctamente en: ");
			System.out.println(keyStore.getAbsolutePath());
		} else {
			System.out.println("Ha habido un problema a la hora de crear el fichero.");
		}
	}
}
