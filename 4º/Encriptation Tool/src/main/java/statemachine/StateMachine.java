package statemachine;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class StateMachine. Core of the application. Manage the flow of states.
 */
public class StateMachine {

	/** The historic. Used to have an historic of the states actually working. */
	List<State> historic;

	/** The data shared between all the applpication. */
	GeneralData data;

	/**
	 * Instantiates a new state machine.
	 */
	public StateMachine() {
		historic = new ArrayList<State>();
		data = new GeneralData();
	}

	/**
	 * Add the state to the historic and execute it. When it finish, removes it from
	 * the historic.
	 *
	 * @param s the next state
	 */
	public void nextState(State s) {
		historic.add(s);
		s.handle(this, data);
		historic.remove(s);
	}
}
