package statemachine.state;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * MENU: The menu to exit the application.
 */
public class ExitMenuState implements State {

	/** The console reader. */
	private BufferedReader consoleReader;

	/**
	 * Instantiates a new instance.
	 */
	public ExitMenuState() {
		consoleReader = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * The menu to exit the application.
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		System.out.println("Salir? [S/N]");
		String confirmation;
		try {
			confirmation = consoleReader.readLine();
			if (confirmation.equalsIgnoreCase("s"))
				System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
