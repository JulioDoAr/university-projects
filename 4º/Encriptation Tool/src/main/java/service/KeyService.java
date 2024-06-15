package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Singleton. Methods about the public and private key
 */
public class KeyService {

	/** The key size. */
	private int KEY_SIZE = 512;

	/** The instance. */
	private static KeyService instance = null;

	/** The keys. */
	private KeyPair keys;

	/** The file service. */
	private final FileService fileService;

	/**
	 * Instantiates a new cipher service.
	 */
	private KeyService() {
		keys = null;
		fileService = FileService.getInstance();
	}

	/**
	 * Gets the single instance of KeyService.
	 *
	 * @return single instance of KeyService
	 */
	public static KeyService getInstance() {
		if (instance == null)
			instance = new KeyService();
		return instance;
	}

	/**
	 * Generate a new pair of keys.
	 *
	 * @param algorithm the algorithm
	 * @return the key pair
	 */
	public KeyPair generateKeys(String algorithm) {
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(algorithm);
			kpg.initialize(KEY_SIZE);
			keys = kpg.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keys;
	}

	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	public KeyPair getKeys() {
		return keys;
	}

	/**
	 * Set the keys.
	 * 
	 * @param keys to set
	 */
	public void setKeys(KeyPair keys) {
		this.keys = keys;
	}

	/**
	 * Creates the signature of a file.
	 *
	 * @param algorithm the algorithm to create the signature
	 * @param key       the private key
	 * @param file      the file File to calculate its signature
	 * @return the byte[] The calculated signature of the byte
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws InvalidKeyException      the invalid key exception
	 * @throws SignatureException       the signature exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public byte[] createSignature(String algorithm, PrivateKey key, File file)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		// Obtener instancia del objeto:
		Signature dsa = Signature.getInstance(algorithm);
		// Iniciar para crear firma con Clave Privada:
		dsa.initSign(key);
		// Procesar información a firmar:
		try (InputStream in = new FileInputStream(file)) {
			byte[] buffer = new byte[1024];
			while (in.read(buffer) != -1) {
				dsa.update(buffer);
			}
		}
		return dsa.sign();
	}

	/**
	 * Verify signature of a file.
	 *
	 * @param algorithm the algorithm to create the signature
	 * @param key       the private key
	 * @param in        the input stream to load the data
	 * @param sig       the sig a precalculated signature of the file
	 * @return true, if successful, false if not
	 * @throws SignatureException       the signature exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws InvalidKeyException      the invalid key exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public boolean verifySignature(String algorithm, PublicKey key, InputStream in, byte[] sig)
			throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
		// Obtener instancia del objeto:
		Signature dsa = Signature.getInstance(algorithm);
		// Iniciar para verificar firma con Clave Pública:
		dsa.initVerify(key);
		// Procesar información a firmar:
		byte[] buffer = new byte[1024];
		while (in.read(buffer) != -1) {
			dsa.update(buffer);
		}
		// Verificar la firma:
		return dsa.verify(sig);
	}

	/**
	 * Encript a file with a key
	 *
	 * @param key  the key to cipher
	 * @param file the file to cipher
	 * @param out  the output stream in wich write all the encripted data
	 * @throws NoSuchAlgorithmException  the no such algorithm exception
	 * @throws NoSuchPaddingException    the no such padding exception
	 * @throws InvalidKeyException       the invalid key exception
	 * @throws FileNotFoundException     the file not found exception
	 * @throws IOException               Signals that an I/O exception has occurred.
	 * @throws IllegalBlockSizeException the illegal block size exception
	 * @throws BadPaddingException       the bad padding exception
	 */
	public void cipherWithKey(Key key, File file, OutputStream out)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException,
			IOException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] buffer = new byte[53];
		try (InputStream in = new FileInputStream(file)) {
			while (in.read(buffer) != -1)
				out.write(cipher.doFinal(buffer));
		}
	}

	/**
	 * Decipher a file with a signature.
	 *
	 * @param key the key to decipher
	 * @param in  the input stream to read data
	 * @param out the output stream to write the decripted data
	 * @throws NoSuchAlgorithmException  the no such algorithm exception
	 * @throws NoSuchPaddingException    the no such padding exception
	 * @throws InvalidKeyException       the invalid key exception
	 * @throws IllegalBlockSizeException the illegal block size exception
	 * @throws BadPaddingException       the bad padding exception
	 * @throws IOException               Signals that an I/O exception has occurred.
	 */
	public void decipherWithSignature(Key key, InputStream in, OutputStream out) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte[] buffer = new byte[64];
		while (in.read(buffer) != -1) {
			out.write(cipher.doFinal(buffer));
		}
	}

	/**
	 * Save the key in the chosen file.
	 *
	 * @param toSave Place
	 */
	public void saveKey(File toSave) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(toSave));
			out.writeObject(keys);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(out);
		}
	}

	/**
	 * Delete the generated keys.
	 */
	public void deleteKeys() {
		keys = null;
	}

	/**
	 * Load the keys from a file.
	 *
	 * @param toLoad File to load the keys
	 * @return true if the keys has been loaded. False if not
	 */
	public boolean loadKeys(File toLoad) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(toLoad));
			keys = (KeyPair) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(in);
		}
		return keys != null;
	}
}
