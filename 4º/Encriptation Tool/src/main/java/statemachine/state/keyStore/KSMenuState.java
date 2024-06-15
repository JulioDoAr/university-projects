package statemachine.state.keyStore;

import service.IOService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;
import statemachine.state.ExitMenuState;

/**
 * STATE: The main menu of key store
 */
public class KSMenuState implements State {

	/** The options to show. */
	public final static String KeyStoreMenu[] = { "Crear almacen de claves", "Seleccionar almacen de claves",
			"Importar clave", "Mostrar claves", "Eliminar claves", "Volver", "Salir" };

	/** The IOService. */
	private IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public KSMenuState() {
		iOService = IOService.getInstance();
	}

	/**
	 * Show the main menu of the application and start all the possible options.
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		boolean back = false;
		while (!back) {
			iOService.showOptions(KeyStoreMenu);
			int option = iOService.askForNumber(KeyStoreMenu);
			switch (option) {
			case 0:
				stateMachine.nextState(new KSCreateState());
				break;
			case 1:
				stateMachine.nextState(new KSSelectionState());
				break;
			case 2:
				stateMachine.nextState(new KSImportKeyState());
				break;
			case 3:
				stateMachine.nextState(new KSShowKeysState());
				break;
			case 4:
				stateMachine.nextState(new KSDeleteKeysState());
				break;
			case 5:
				back = true;
				break;
			case 6:
				stateMachine.nextState(new ExitMenuState());
				break;
			}
			System.out.println("\n------------------------------");
			System.out.println("------------------------------");
			System.out.println("------------------------------\n");
		}
	}
}
