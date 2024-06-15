package statemachine.state;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import service.FileService;
import service.HashService;
import srt.Header;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Verify a hash protected file.
 */
public class VerifyHashState implements State {

	/** The file service. */
	private FileService fileService;

	/** The hash service. */
	private HashService hashService;

	/**
	 * Instantiates a new instance.
	 */
	public VerifyHashState() {
		hashService = HashService.getInstance();
		fileService = FileService.getInstance();
	}

	/**
	 * Gets the salt from data.
	 *
	 * @param data the data
	 * @return the salt from data
	 */
	private byte[] getSaltFromData(byte[] data) {
		byte[] salt = new byte[HashService.SALT_LENGHT];
		for (int i = 0; i < HashService.SALT_LENGHT; i++) {
			salt[i] = data[i];
		}
		return salt;
	}

	/**
	 * Gets the mac from data.
	 *
	 * @param data the data
	 * @return the mac from data
	 */
	private byte[] getMacFromData(byte[] data) {
		int macLenght = data.length - HashService.SALT_LENGHT;
		byte[] mac = new byte[macLenght];
		for (int i = 0; i < macLenght; i++) {
			mac[i] = data[HashService.SALT_LENGHT + i];
		}
		return mac;
	}

	/**
	 * Handle.
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		// Pedir fichero a comprobar
		stateMachine.nextState(new AskForFileState("Inserta el fichero al cual calcular el hash (.hsh)."));

		// Pedir clave
		stateMachine.nextState(new AskForPasswordState());

		// Cargar header
		Header header = new Header();
		InputStream in = null;
		byte[] calculatedValue = null;
		try {
			in = new FileInputStream(data.getSelectedFile());

			// Cargamos la cabecera
			if (!header.load(in))
				throw new Exception("ERROR: Fallo al cargar el header del fichero.");

			// Preparamos los datos
			byte[] precalculatedValue;
			if (header.getAlgorithm2().contains("Hmac")) {
				// Calculamos el hash actual
				byte[] salt = getSaltFromData(header.getData());
				calculatedValue = hashService.calculateMac(data.getPassword(), header.getAlgorithm2(), in, salt);
				// Cargamos los datos precalculados
				precalculatedValue = getMacFromData(header.getData());
			} else {
				// Calculamos el hash actual
				calculatedValue = hashService.calculateHash(data.getPassword(), header.getAlgorithm2(), in);
				// Cargamos los datos precalculados
				precalculatedValue = header.getData();
			}

			// Comparamos el hash precalculado con el calculado propio
			if (Arrays.equals(precalculatedValue, calculatedValue))
				System.out.println("El hash calculado coincide.");
			else
				System.out.println("El hash calculado NO coincide.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(in);
		}
	}

}
