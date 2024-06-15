package statemachine.state;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * The Class CipherWithKeyState.
 */
public class CipherWithKeyState implements State {

	/** The key service. */
	private static KeyService keyService;

	/** The file service. */
	private static FileService fileService;

	/** The IOservice. */
	private static IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public CipherWithKeyState() {
		keyService = KeyService.getInstance();
		fileService = FileService.getInstance();
		iOService = IOService.getInstance();
	}

	/**
	 * Menu to cipher with a public key
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
			System.out.println("La clave no es RSA. No se puede cifrar un fichero con ella.");
			return;
		}

		// Ask for file
		File toCipher = iOService.askForFile("Seleccione el fichero a cifrar");
		File ciphed = fileService.changeFileExtension(toCipher, ".cif");

		OutputStream out = null;
		try {
			out = new FileOutputStream(ciphed);
			keyService.cipherWithKey(keyService.getKeys().getPublic(), toCipher, out);
			System.out.println("Fichero cifrado guardado en:");
			System.out.println(ciphed.getAbsolutePath());
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(out);
		}
	}

}
