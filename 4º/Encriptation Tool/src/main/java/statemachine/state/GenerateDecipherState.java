package statemachine.state;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

import service.CipherService;
import srt.Options;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * STATE: Generate all the data necessary to decipher.
 */
public class GenerateDecipherState implements State {

	/** The cipher service. */
	private CipherService cipherService;

	/**
	 * Instantiates a new instance.
	 */
	public GenerateDecipherState() {
		cipherService = CipherService.getInstance();
	}

	/**
	 * Generate all the data necessary to decipher. Store it in <b>salt</b>,
	 * <b>parameters</b>, <b>secretKey</b> and <b>cipher</b>
	 *
	 * @param stateMachine the state machine
	 * @param data         the data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		byte[] salt = cipherService.generateSalt(8);
		data.setSalt(salt);

		PBEParameterSpec parameters = cipherService.generateParameterSpec(salt, Options.IC);
		data.setParameters(parameters);

		SecretKey sk = cipherService.generateSecretKey(data.getPassword().toCharArray(), parameters,
				data.getAlgorithm());
		data.setSecretKey(sk);

		Cipher cipher = cipherService.generateDecryptCipher(data.getAlgorithm(), sk, parameters);
		data.setCipher(cipher);
	}

}
