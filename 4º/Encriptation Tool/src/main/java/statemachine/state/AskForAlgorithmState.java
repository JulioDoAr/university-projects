package statemachine.state;

import service.IOService;
import srt.Options;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Ask the user for the algorithm to use and save it in
 * <b>algorithm1</b>.
 */
public class AskForAlgorithmState implements State {

	/** The IOService. */
	private IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public AskForAlgorithmState() {
		iOService = IOService.getInstance();
	}

	/**
	 * Ask the user for the algorithm to use and save it in <b>algorithm1</b>.
	 *
	 * @param stateMachine the state machine
	 * @param data         the general data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		iOService.showOptions(Options.cipherAlgorithms);
		int option = iOService.askForNumber(Options.cipherAlgorithms);
		String algorithm = Options.cipherAlgorithms[option];
		data.setAlgorithm(algorithm);
	}

}
