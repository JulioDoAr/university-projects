package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Singleton. Methods about the key store
 */
public class KeyStoreService {

	/**
	 * The actual key store
	 */
	private KeyStore keyStore = null;

	/** The instance. */
	private static KeyStoreService instance = null;

	/**
	 * Instantiates a new cipher service.
	 */
	private KeyStoreService() {
	}

	/**
	 * Gets the single instance of KeyService.
	 *
	 * @return single instance of KeyService
	 */
	public static KeyStoreService getInstance() {
		if (instance == null)
			instance = new KeyStoreService();
		return instance;
	}

	/**
	 * Initialize the key store
	 * 
	 * @param fileName File to load the key store
	 * @param password Password of the key store
	 * @return true if ok, false if error
	 */
	public boolean init(String fileName, char[] password) {
		boolean error = true;
		InputStream in = null;
		try {
			keyStore = KeyStore.getInstance("JKS");
			in = new FileInputStream(fileName);
			keyStore.load(in, password);
			error = false;
		} catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
		} finally {
			FileService.getInstance().closeSafely(in);
		}
		return !error;
	}

	/**
	 * Create a new key store
	 * 
	 * @param fileName File where save the key store
	 * @param password Pasword of the store
	 * @return the file where the key store is saved. Null if has been an error.
	 */
	public File createKeyStore(String fileName, char[] password) {
		File file = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			keyStore = KeyStore.getInstance("JKS");
			keyStore.load(null, password);
			keyStore.store(fos, password);
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * Check if the key store has been initialized.
	 * 
	 * @return true if yes, false if not.
	 */
	public boolean isInited() {
		return keyStore != null;
	}

	/**
	 * Return all the keys of the key store
	 * 
	 * @return a key array
	 */
	public String[] getAllKeysAsArray() {
		Enumeration<String> keys;
		String[] keyArray = null;
		try {
			keys = keyStore.aliases();
			List<String> parsedKeys = new ArrayList<String>();
			while (keys.hasMoreElements()) {
				parsedKeys.add(keys.nextElement());
			}
			keyArray = new String[parsedKeys.size()];
			for (int i = 0; i < keyArray.length; i++) {
				keyArray[i] = parsedKeys.get(i);
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}

		return keyArray;
	}

	/**
	 * Load the selected key by alias
	 * 
	 * @param string   Alias of the key
	 * @param password Password of the key
	 * @return the selected key or null if error
	 */
	public KeyPair loadKey(String string, char[] password) {
		Enumeration<String> e;
		KeyPair pair = null;
		try {
			e = keyStore.aliases();
			while (e.hasMoreElements()) {
				String alias = e.nextElement();
				if (keyStore.isKeyEntry(alias)) {
					PrivateKey kr = (PrivateKey) keyStore.getKey(alias, password);
					Certificate cert = keyStore.getCertificate(alias);
					PublicKey ku = cert.getPublicKey();
					pair = new KeyPair(ku, kr);
				}
			}
		} catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException ex) {
		}

		return pair;
	}

	/**
	 * Show all the keys of the key store
	 * 
	 * @param password of the key store
	 */
	public void showAllKeys(char[] password) {
		try {
			Enumeration<String> e = keyStore.aliases();
			System.out.println();

			while (e.hasMoreElements()) {
				String alias = e.nextElement();
				PrivateKey kr = (PrivateKey) keyStore.getKey(alias, password);
				Certificate cert = keyStore.getCertificate(alias);
				PublicKey ku = cert.getPublicKey();
				KeyPair pair = new KeyPair(ku, kr);
				System.out.println("Alias: " + alias);
				IOService.getInstance().showKey(pair);
				System.out.println();
			}
		} catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Delete a key by alias
	 * 
	 * @param alias of the key to delete
	 */
	public void deleteKey(String alias) {
		try {
			keyStore.deleteEntry(alias);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
}
