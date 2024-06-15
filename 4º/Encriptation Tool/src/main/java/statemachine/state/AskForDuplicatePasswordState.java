package statemachine.state;

import service.IOService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Ask for a password 2 times to the user and checks if is equal.
 */
public class AskForDuplicatePasswordState implements State {

	/** The IOService. */
	private IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public AskForDuplicatePasswordState() {
		iOService = IOService.getInstance();
	}

	/**
	 * Ask for a password 2 times to the user and checks if is equal. If it is
	 * equal, it is stored into <b>password</b>
	 *
	 * @param stateMachine the state machine
	 * @param data         the general data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		data.setPassword(iOService.askForDuplicatedPassword());
	}

}
