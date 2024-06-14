package main;

import logger.ConsoleOutputOperation;
import logger.Logger;

/**
 * Punto de ejecucion de la aplicacion
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class Main {

	public static void main(String[] args) {
		Logger.getInstance().addOutputOperation(new ConsoleOutputOperation());
		Logger.getInstance().startWrite();

		Menu m = new Menu();
		m.start();
		Logger.getInstance().finishWrite();
	}
}
