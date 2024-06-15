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
public class AskForHashAlgorithmState implements State {

	/** The IOService. */
	private IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public AskForHashAlgorithmState() {
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
		iOService.showOptions(Options.hashmacAlgorithms);
		int option = iOService.askForNumber(Options.hashmacAlgorithms);
		String algorithm = Options.hashmacAlgorithms[option];
		data.setAlgorithm(algorithm);
	}

}
