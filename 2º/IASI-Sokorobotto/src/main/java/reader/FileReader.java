package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Clase encargada de la lectura del fichero que representa el tablero
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class FileReader {

	private static FileReader instance = null;

	public static FileReader getInstance() {
		if (instance == null)
			instance = new FileReader();
		return instance;
	}

	private FileReader() {
	}

	public int[][] readSquareMatrix(File data) {
		int[][] matrix = null;

		Scanner reader = null;
		try {
			reader = new Scanner(data);
			// Inicializamos el lector de datos.
			int cindex, rindex = 0;

			// Obtenemos la primera linea del documento y guardamos la longitud
			String[] row = reader.nextLine().split(",");
			int matrixSize = row.length;
			matrix = new int[matrixSize][matrixSize];

			// Almacenamos los datos de la primera linea
			for (cindex = 0; cindex < matrixSize; cindex++)
				matrix[rindex][cindex] = Integer.parseInt(row[cindex]);
			rindex++;

			// Continuamos con el resto de lineas hasta finalizar las filas
			while (rindex < matrixSize) {
				row = reader.nextLine().split(",");
				for (cindex = 0; cindex < matrixSize; cindex++)
					matrix[rindex][cindex] = Integer.parseInt(row[cindex]);
				rindex++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}

		return matrix;
	}
}
