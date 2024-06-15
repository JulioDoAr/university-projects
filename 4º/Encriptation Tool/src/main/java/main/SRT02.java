package main;

import statemachine.StateMachine;
import statemachine.state.MainMenuState;

/**
 * Main method.
 */
public class SRT02 {

	/**
	 * Start the state machine that manages the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		StateMachine s = new StateMachine();
		s.nextState(new MainMenuState());
	}
}
