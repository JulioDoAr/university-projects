package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Singleton. Methods about cipher and decipher
 */
public class CipherService {

	/** The instance. */
	private static CipherService instance = null;

	/**
	 * Instantiates a new cipher service.
	 */
	private CipherService() {
	}

	/**
	 * Gets the single instance of CipherService.
	 *
	 * @return single instance of CipherService
	 */
	public static CipherService getInstance() {
		if (instance == null)
			instance = new CipherService();
		return instance;
	}

	/**
	 * Generate salt.
	 *
	 * @param length the length in bytes
	 * @return the generated salt
	 */
	public byte[] generateSalt(int length) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] randomBytes = new byte[length];
		secureRandom.nextBytes(randomBytes);
		return randomBytes;
	}

	/**
	 * Generate secret key.
	 *
	 * @param passwd    the password
	 * @param pPS       the PBE parameters
	 * @param algorithm the algorithm
	 * @return the secret key
	 */
	public SecretKey generateSecretKey(char[] passwd, PBEParameterSpec pPS, String algorithm) {
		SecretKey sKey = null;
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd);
		SecretKeyFactory kf;
		try {
			kf = SecretKeyFactory.getInstance(algorithm);
			sKey = kf.generateSecret(pbeKeySpec);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Cryptographic algorithm is not available in the environment.");
		} catch (InvalidKeySpecException e) {
			System.err.println("Invalid key specifications.");
		}
		return sKey;
	}

	/**
	 * Cipher the input stream and writes it in the output
	 *
	 * @param in  the input stream from where to read
	 * @param out the output stream where write
	 * @param c   the cipher
	 */
	public void cipher(InputStream in, OutputStream out, Cipher c) {
		CipherInputStream cis = null;
		try {
			cis = new CipherInputStream(in, c);
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = cis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cis != null)
					cis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generate cipher to encrypt.
	 *
	 * @param algorithm the algorithm
	 * @param sKey      the secret key
	 * @param pPS       the PBE parameters
	 * @return the cipher
	 */
	public Cipher generateEncryptCipher(String algorithm, SecretKey sKey, PBEParameterSpec pPS) {
		Cipher c = null;
		try {
			c = Cipher.getInstance(algorithm);
			c.init(Cipher.ENCRYPT_MODE, sKey, pPS);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Generate cipher to decrypt.
	 *
	 * @param algorithm the algorithm
	 * @param sKey      the secret key
	 * @param pPS       the PBE parameters
	 * @return the cipher
	 */
	public Cipher generateDecryptCipher(String algorithm, SecretKey sKey, PBEParameterSpec pPS) {
		Cipher c = null;
		try {
			c = Cipher.getInstance(algorithm);
			c.init(Cipher.DECRYPT_MODE, sKey, pPS);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Generate the PBE parameters.
	 *
	 * @param salt             the salt
	 * @param iterationCounter the iteration counter
	 * @return the PBE parameters
	 */
	public PBEParameterSpec generateParameterSpec(byte[] salt, int iterationCounter) {
		return new PBEParameterSpec(salt, iterationCounter);
	}
}
