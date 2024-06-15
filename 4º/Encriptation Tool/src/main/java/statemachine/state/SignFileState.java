package statemachine.state;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import service.FileService;
import service.IOService;
import service.KeyService;
import srt.Header;
import srt.Options;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Sign a file with the private key
 */
public class SignFileState implements State {

	/** The key service. */
	private static KeyService keyService;

	/** The file service. */
	private static FileService fileService;

	/** The IOservice. */
	private static IOService iOService;

	/**
	 * Instantiates a new instance.
	 */
	public SignFileState() {
		keyService = KeyService.getInstance();
		fileService = FileService.getInstance();
		iOService = IOService.getInstance();
	}

	/**
	 * Sign a file with the private key if it is generated and store the sign and
	 * the content of the file in another file
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		// Comprobar si existen las claves
		if (keyService.getKeys() == null) {
			System.out.println("No existen claves para exportar.");
			return;
		}

		// Comprobar si la clave es RSA
		if (!keyService.getKeys().getPrivate().getAlgorithm().contains("RSA")) {
			System.out.println("La clave no es RSA. No se puede firmar un fichero con ella.");
			return;
		}

		// Preguntar por file
		File toSign = iOService.askForFile("Seleccione el fichero a cifrar:");

		iOService.showOptions(Options.signAlgorithms);
		int selectedAlgorithm = iOService.askForNumber(Options.signAlgorithms);
		String algorithm = Options.signAlgorithms[selectedAlgorithm];

		byte[] signature;
		try {
			signature = keyService.createSignature(algorithm, keyService.getKeys().getPrivate(), toSign);
			Header header = new Header(Options.OP_SIGNED, Options.cipherAlgorithms[0], algorithm, signature);

			File signed = fileService.changeFileExtension(toSign, ".fir");
			fileService.writeTo(header, toSign, signed);
			System.out.println("Fichero firmado correctamente.");
			System.out.println(signed.getAbsolutePath());
			return;
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("Error al firmar el fichero.");
	}

}
