package statemachine.state;

import service.IOService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Ask the user to provide a password.
 */
public class AskForPasswordState implements State {

	/** The IOService. */
	private IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public AskForPasswordState() {
		iOService = IOService.getInstance();
	}

	/**
	 * Ask the user to provide a password. It is stored into <b>password</b>
	 *
	 * @param stateMachine the state machine
	 * @param data         the general data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		String str = iOService.askForPassword();
		data.setPassword(str);
	}

}
