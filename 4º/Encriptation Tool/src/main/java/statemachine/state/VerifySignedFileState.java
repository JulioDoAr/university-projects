package statemachine.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import service.FileService;
import service.IOService;
import service.KeyService;
import srt.Header;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Verify a signed file
 */
public class VerifySignedFileState implements State {

	/** The key service. */
	private static KeyService keyService;

	/** The file service. */
	private static FileService fileService;

	/**
	 * Instantiates a new instance.
	 */
	public VerifySignedFileState() {
		keyService = KeyService.getInstance();
		fileService = FileService.getInstance();
	}

	/**
	 * Check if the keys can be exported and ask the user for a place to store it.
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
		File signed = IOService.getInstance().askForFile("Seleccione fichero para comprobar su firma.");
		FileInputStream in = null;
		try {
			in = new FileInputStream(signed);

			Header header = fileService.loadHeader(in);
			if (header == null) {
				System.out.println("No se ha podido cargar la cabecera correctamente.");
				return;
			}
			String algorithm = header.getAlgorithm2();
			byte[] signature = header.getData();

			boolean result = keyService.verifySignature(algorithm, keyService.getKeys().getPublic(), in, signature);

			if (result)
				System.out.println("La firma es correcta.");
			else
				System.out.println("La firma es incorrecta.");

		} catch (IOException | InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(in);
		}
	}
}
