package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import data.Operator;
import logger.Logger;
import node.Node;
import search.AStartSearch;
import search.ClimbSearch;
import search.FirstBest;
import search.HorizontalSearch;
import search.Treatment;

/**
 * Menu principal de la aplicacion. Encargado de preguntar al usuario por el
 * juego a resolver, el algoritmo y el numero de veces a ejecutar el algoritmo
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class Menu {

	Logger log;
	Scanner sc;

	public Menu() {
		log = Logger.getInstance();
		sc = new Scanner(System.in);
	}

	/**
	 * Starts the menu
	 */
	public void start() {
		boolean exit = false;

		while (!exit) {
			int selectedPanel = askForPanel();
			if (selectedPanel == 0)
				exit = true;
			else {
				int selectedAlgorithm = askForAlgoritm();
				int times = askForTimes();

				int execution = 0;
				while (execution < times) {
					execution++;
					log.writeLine("--------------------------------------------");
					log.writeLine("-------------- Ejecucion %d ----------------", execution);
					log.writeLine("--------------------------------------------");
					if (selectedPanel != 11)
						execute(selectedAlgorithm, selectedPanel);
					else if (selectedPanel == 11)
						for (int i = 1; i <= 10; i++) {
							log.writeLine("--------------------------------------------");
							log.writeLine("--------------- Tablero %d -----------------", i);
							log.writeLine("--------------------------------------------");
							execute(selectedAlgorithm, i);
						}
				}
				log.writeLine("");
				log.writeLine("*****************************************************************************");
				log.writeLine("");
			}
		}
	}

	/**
	 * Execute a the selected algorithm with the selected panel
	 *
	 * @param selectedAlgorithm id of the algorithm
	 * @param selectedPanel     id of the panel
	 */
	private void execute(int selectedAlgorithm, int selectedPanel) {
		Treatment t = null;
		if (selectedAlgorithm == 0)
			t = new ClimbSearch(String.format("SOKOBOTTO%d.txt", selectedPanel));
		else if (selectedAlgorithm == 1)
			t = new AStartSearch(String.format("SOKOBOTTO%d.txt", selectedPanel));
		else if (selectedAlgorithm == 2)
			t = new HorizontalSearch(String.format("SOKOBOTTO%d.txt", selectedPanel));
		else if (selectedAlgorithm == 3)
			t = new FirstBest(String.format("SOKOBOTTO%d.txt", selectedPanel));
		executeTreatment(t);
	}

	/**
	 * Execute a generic treatment and print all its info
	 *
	 * @param t Treatment to execute
	 */
	private void executeTreatment(Treatment t) {
		log.writeLine("Algoritmo: %s", t.getName());
		log.writeLine("Tablero: %s", t.getPanelName());
		t.showOriginalData();

		log.writeLine("Ejecutando...");
		t.execute();

		log.writeLine("Tiempo utilizado: %dms", t.getTime());
		log.writeLine("Nodos generados: %d", t.getGeneratedNodes());
		Node solution = t.getSolution();
		if (solution != null) {
			log.writeLine("Encontrada solución: si");

			List<Operator> operators = new ArrayList<Operator>();
			Node<Node> node = solution;
			while (node.getOperator() != null) {
				operators.add(node.getOperator());
				node = node.getParent();
			}
			Collections.reverse(operators);
			for (Operator op : operators)
				log.write("%s, ", op.name());
			log.writeLine("");

			log.writeLine("¿Desea ver el recorrido? (s/n)");
			String input = sc.next();
			input = input.toLowerCase();
			if (input.equals("s") || input.equals("si")) {
				t.showMovements();
				log.writeLine("** El recorrido se lee de abajo a arriba");
			}
		} else
			log.writeLine("Encontrada solución: no");

	}

	/**
	 * Ask user the algorithm to execute
	 *
	 * @return the id of the algorithm
	 */
	private int askForAlgoritm() {
		log.writeLine("Elija el algoritmo a ejecutar");
		log.writeLine("[0] Escalada de maxima pendiente");
		log.writeLine("[1] A*");
		log.writeLine("[2] Busqueda Horizontal");
		log.writeLine("[3] Escalada simple");

		int selection = sc.nextInt();
		while (selection < 0 || selection > 3) {
			System.out.println("Entrada no valida. Repita de nuevo.");
			selection = sc.nextInt();
		}
		return selection;
	}

	/**
	 * Ask user the number of times to execute the algorithm
	 *
	 * @return the input number
	 */
	private int askForTimes() {
		log.writeLine("¿Cuantas veces desea ejecutar el algoritmo? (0-99)");

		int selection = sc.nextInt();
		while (selection < 0 || selection > 99) {
			System.out.println("Entrada no valida. Repita de nuevo.");
			selection = sc.nextInt();
		}
		return selection;
	}

	/**
	 * Ask user for the panel to play
	 *
	 * @return te number of the panel
	 */
	private int askForPanel() {
		log.writeLine("Elija el tablero a resolver:");
		log.writeLine("[0] Salir");
		for (int i = 1; i < 11; i++)
			log.writeLine("[%d] SOKOBOTTO%d.txt", i, i);
		log.writeLine("[11] Todos");

		int selectedPanel = sc.nextInt();
		while (selectedPanel < 0 || selectedPanel > 12) {
			System.out.println("Entrada no valida. Repita de nuevo.");
			selectedPanel = sc.nextInt();
		}
		return selectedPanel;
	}

}
