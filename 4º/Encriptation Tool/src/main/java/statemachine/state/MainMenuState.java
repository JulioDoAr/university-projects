package statemachine.state;

import service.IOService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;
import statemachine.state.keyStore.KSMenuState;

/**
 * MENU: Main menu.
 */
public class MainMenuState implements State {

	/** The options to show. */
	public final static String MainMenu[] = { "Cifrar", "Descifrar", "Proteger con hash", "Verificar hash",
			"Gestión de claves", "Firma Digital", "Almacen de claves", "Salir" };

	/** The IOService. */
	private IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public MainMenuState() {
		iOService = IOService.getInstance();
	}

	/**
	 * Show the main menu of the application and start all the possible options.
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		System.out.println("SRT - Julio Domínguez Arjona");
		System.out.println("Para salir, puede escribir 'salir' en cualquier momento.");
		while (true) {
			iOService.showOptions(MainMenu);
			int option = iOService.askForNumber(MainMenu);
			switch (option) {
			case 0:
				stateMachine.nextState(new CipherMenuState());
				break;
			case 1:
				stateMachine.nextState(new DecipherMenuState());
				break;
			case 2:
				stateMachine.nextState(new ProtectWithHashState());
				break;
			case 3:
				stateMachine.nextState(new VerifyHashState());
				break;
			case 4:
				stateMachine.nextState(new KeyManagementState());
				break;
			case 5:
				stateMachine.nextState(new DigitalSignatureMenuState());
				break;
			case 6:
				stateMachine.nextState(new KSMenuState());
				break;
			case 7:
				stateMachine.nextState(new ExitMenuState());
				break;
			}
			System.out.println("\n------------------------------");
			System.out.println("------------------------------");
			System.out.println("------------------------------\n");
		}
	}

}
