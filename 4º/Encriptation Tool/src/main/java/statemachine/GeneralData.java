package statemachine;

import java.io.File;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

/**
 * The data bean of the application. It is shared over all the application.
 */
public class GeneralData {

	/** The password. */
	private String password;

	/** The algorithm 1. */
	private String algorithm;

	/** The selected file. */
	private File selectedFile;

	/** The salt. */
	private byte[] salt;

	/** The parameters. */
	private PBEParameterSpec parameters;

	/** The secret key. */
	private SecretKey secretKey;

	/** The cipher. */
	private Cipher cipher;

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the algorithm .
	 *
	 * @return the algorithm 1
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Sets the algorithm .
	 *
	 * @param algorithm1 the new algorithm
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * Gets the selected file.
	 *
	 * @return the selected file
	 */
	public File getSelectedFile() {
		return selectedFile;
	}

	/**
	 * Sets the selected file.
	 *
	 * @param selectedFile the new selected file
	 */
	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	/**
	 * Gets the salt.
	 *
	 * @return the salt
	 */
	public byte[] getSalt() {
		return salt;
	}

	/**
	 * Sets the salt.
	 *
	 * @param salt the new salt
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public PBEParameterSpec getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(PBEParameterSpec parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the secret key.
	 *
	 * @return the secret key
	 */
	public SecretKey getSecretKey() {
		return secretKey;
	}

	/**
	 * Sets the secret key.
	 *
	 * @param secretKey the new secret key
	 */
	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Gets the cipher.
	 *
	 * @return the cipher
	 */
	public Cipher getCipher() {
		return cipher;
	}

	/**
	 * Sets the cipher.
	 *
	 * @param cipher the new cipher
	 */
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

}
