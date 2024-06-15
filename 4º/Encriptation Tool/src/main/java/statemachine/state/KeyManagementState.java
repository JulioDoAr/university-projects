package statemachine.state;

import service.IOService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Class to show a menu to manage the states related with key management
 */
public class KeyManagementState implements State {

	/** The Constant options. */
	public final static String options[] = { "Generar claves", "Guardar claves", "Cargar claves", "Volver", "Salir" };

	/** The iOservice. */
	private IOService iOService;

	/**
	 * Instantiates a new key management state.
	 */
	public KeyManagementState() {
		iOService = IOService.getInstance();
	}

	/**
	 * Show the different options to manage a key and launch the selected option
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		iOService.showOptions(options);
		int option = iOService.askForNumber(options);
		switch (option) {
		case 0:
			stateMachine.nextState(new GenerateKeysState());
			break;
		case 1:
			stateMachine.nextState(new SaveKeysState());
			break;
		case 2:
			stateMachine.nextState(new LoadKeysState());
			break;
		case 4:
			stateMachine.nextState(new ExitMenuState());
			break;
		}
	}

}
