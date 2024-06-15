package statemachine.state;

import service.IOService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * The Class DigitalSignatureMenuState.
 */
public class DigitalSignatureMenuState implements State {

	/** The Constant options. */
	public final static String options[] = { "Firmar fichero", "Verificar firma fichero", "Cifrar fichero",
			"Descifrar fichero", "Volver", "Salir" };

	/** The iOservice. */
	private IOService iOService;

	/**
	 * Instantiates a new digital signature menu state.
	 */
	public DigitalSignatureMenuState() {
		iOService = IOService.getInstance();
	}

	/**
	 * Menu to show and manage the options of "Firma digital"
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
			stateMachine.nextState(new SignFileState());
			break;
		case 1:
			stateMachine.nextState(new VerifySignedFileState());
			break;
		case 2:
			stateMachine.nextState(new CipherWithKeyState());
			break;
		case 3:
			stateMachine.nextState(new DecipherWithKeyState());
			break;
		case 5:
			stateMachine.nextState(new ExitMenuState());
			break;
		}
	}
}
