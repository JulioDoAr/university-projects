package statemachine.state;

import java.io.File;

import service.IOService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Ask the user to provide a valid file. It checks if it exist and if it
 * is a directory.
 */
public class AskForFileState implements State {

	/** The IOService. */
	private IOService iOService;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new instance.
	 * 
	 * @param message Message to show to the user
	 */
	public AskForFileState(String message) {
		iOService = IOService.getInstance();
		this.message = message;
	}

	/**
	 * Ask the user to provide a valid file. It checks if it exist and if it is a
	 * directory. If it is valid, it is stored into <b>selectedFile</b>
	 *
	 * @param stateMachine the state machine
	 * @param data         the general data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		File file = iOService.askForFile(message);
		data.setSelectedFile(file);
	}

}
