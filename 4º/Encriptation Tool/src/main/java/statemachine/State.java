package statemachine;

/**
 * The Interface State.
 */
public interface State {

	/**
	 * Handle the logic of the state.
	 *
	 * @param stateMachine the state machine. Used to modify the flow of the
	 *                     application.
	 * @param data         the data shared between all the application.
	 */
	void handle(StateMachine stateMachine, GeneralData data);
}
