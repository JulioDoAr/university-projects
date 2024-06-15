package statemachine.state;

import java.io.File;

import service.CipherService;
import service.FileService;
import service.HashService;
import srt.Header;
import srt.Options;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Manage the protection with hash or mac.
 */
public class ProtectWithHashState implements State {

	/** The hash service. */
	HashService hashService;

	/** The file service. */
	FileService fileService;

	/** The cipher service. */
	CipherService cipherService;

	/**
	 * Instantiates a state.
	 */
	public ProtectWithHashState() {
		hashService = HashService.getInstance();
		fileService = FileService.getInstance();
		cipherService = CipherService.getInstance();
	}

	/**
	 * Manage the protection with hash or mac..
	 *
	 * @param sm   the sm
	 * @param data the data
	 */
	@Override
	public void handle(StateMachine sm, GeneralData data) {
		// Pedir fichero a cifrar
		sm.nextState(new AskForFileState("Inserta el fichero a proteger con hash"));
		// Pedir algoritmo a utilizar
		sm.nextState(new AskForHashAlgorithmState());
		// Pedir clave por duplicado
		sm.nextState(new AskForDuplicatePasswordState());

		if (data.getAlgorithm().contains("Hmac")) {
			protectWithMac(data);
		} else {
			protectWithHash(data);
		}
	}

	/**
	 * Concat two arrays of bytes.
	 *
	 * @param first  the first array
	 * @param second the second array
	 * @return the concatenation ob bytes
	 */
	private byte[] concat(byte[] first, byte[] second) {
		byte[] result = new byte[first.length + second.length];
		for (int i = 0; i < first.length; i++) {
			result[i] = first[i];
		}
		for (int i = 0; i < second.length; i++) {
			result[i + first.length] = second[i];
		}
		return result;
	}

	/**
	 * Protect with mac.
	 *
	 * @param data the data
	 */
	private void protectWithMac(GeneralData data) {
		byte[] salt = cipherService.generateSalt(HashService.SALT_LENGHT);
		byte[] calculatedMac = hashService.calculateMac(data.getPassword(), data.getAlgorithm(), data.getSelectedFile(),
				salt);

		Header header = new Header(Options.OP_HASH_MAC, Options.hashmacAlgorithms[0], data.getAlgorithm(),
				concat(salt, calculatedMac));
		File output = fileService.changeFileExtension(data.getSelectedFile(), ".hsh");

		fileService.writeTo(header, data.getSelectedFile(), output);

		System.out.println("Fichero con MAC calculado en " + output.getAbsolutePath());
	}

	/**
	 * Protect with hash.
	 *
	 * @param data the data
	 */
	private void protectWithHash(GeneralData data) {
		byte[] hash = hashService.calculateHash(data.getPassword(), data.getAlgorithm(), data.getSelectedFile());

		Header header = new Header(Options.OP_HASH_MAC, Options.hashmacAlgorithms[0], data.getAlgorithm(), hash);

		File output = fileService.changeFileExtension(data.getSelectedFile(), ".hsh");

		fileService.writeTo(header, output, data.getSelectedFile());

		System.out.println("Fichero con hash calculado en " + output.getAbsolutePath());
	}

}
