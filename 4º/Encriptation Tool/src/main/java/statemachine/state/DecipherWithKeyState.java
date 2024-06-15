package statemachine.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import service.FileService;
import service.IOService;
import service.KeyService;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * The Class DecipherWithKeyState.
 */
public class DecipherWithKeyState implements State {

	/** The key service. */
	private KeyService keyService;

	/** The file service. */
	private FileService fileService;

	/** The iOservice. */
	private IOService iOService;

	/**
	 * Instantiates a new decipher with key state.
	 */
	public DecipherWithKeyState() {
		keyService = KeyService.getInstance();
		fileService = FileService.getInstance();
		iOService = IOService.getInstance();
	}

	/**
	 * Menu to decipher with the secret key
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		// Check if keys exist
		if (keyService.getKeys() == null) {
			System.out.println("No existen claves para exportar.");
			return;
		}

		// Check if key is RSA
		if (!keyService.getKeys().getPrivate().getAlgorithm().contains("RSA")) {
			System.out.println("La clave no es RSA. No se puede firmar un fichero con ella.");
			return;
		}

		// Ask for file
		File toDecipher = iOService.askForFile("Seleccione fichero para desencriptar.");
		File deciphed = fileService.changeFileExtension(toDecipher, ".cla");

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(toDecipher);
			out = new FileOutputStream(deciphed);
			keyService.decipherWithSignature(keyService.getKeys().getPrivate(), in, out);
			System.out.println("Fichero descifrado guardado en:");
			System.out.println(deciphed.getAbsolutePath());
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(out);
			fileService.closeSafely(in);
		}
	}

}
