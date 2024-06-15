package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import srt.Options;

/**
 * Singleton. Methods about hashing
 */
public class HashService {

	/** The Constant SALT_LENGHT. */
	public static final int SALT_LENGHT = 8;

	/** The file service. */
	private FileService fileService;

	/** The instance. */
	private static HashService instance = null;

	/**
	 * Instantiates a new file service.
	 */
	private HashService() {
		fileService = FileService.getInstance();
	}

	/**
	 * Gets the single instance of FileService.
	 *
	 * @return single instance of FileService
	 */
	public static HashService getInstance() {
		if (instance == null)
			instance = new HashService();
		return instance;
	}

	/**
	 * Calculate hash of a file.
	 *
	 * @param password  the password
	 * @param algorithm the algorithm
	 * @param inFile    the input file
	 * @return calculated hash or null if has been an error
	 */
	public byte[] calculateHash(String password, String algorithm, File inFile) {
		byte[] hash = null;
		InputStream in = null;
		try {
			in = new FileInputStream(inFile);
			hash = calculateHash(password, algorithm, in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(in);
		}
		return hash;
	}

	/**
	 * Calculate hash of a stream.
	 *
	 * @param password  the password
	 * @param algorithm the algorithm
	 * @param is        the input stream
	 * @return calculated hash or null if has been an error
	 */
	public byte[] calculateHash(String password, String algorithm, InputStream is) {
		byte[] resumen = null;
		MessageDigest md;
		DigestInputStream dis = null;

		try {
			md = MessageDigest.getInstance(algorithm);
			md.update(password.getBytes());
			dis = new DigestInputStream(is, md);

			byte[] buffer = new byte[1024];

			// Calculamos el hash
			while (dis.read(buffer) > 0) {
			}

			md = dis.getMessageDigest();
			resumen = md.digest();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return resumen;
	}

	/**
	 * Calculate mac of a file.
	 *
	 * @param password  the password
	 * @param algorithm the algorithm
	 * @param inFile    the input file
	 * @param salt      the salt
	 * @return calculated mac or null if has been an error
	 */
	public byte[] calculateMac(String password, String algorithm, File inFile, byte[] salt) {
		byte[] calculatedMac = null;
		InputStream in = null;
		try {
			in = new FileInputStream(inFile);
			calculatedMac = calculateMac(password, algorithm, in, salt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(in);
		}
		return calculatedMac;

	}

	/**
	 * Calculate mac of a stream.
	 *
	 * @param password  the password
	 * @param algorithm the algorithm
	 * @param in        the input stream
	 * @param salt      the salt
	 * @return calculated mac or null if has been an error
	 */
	public byte[] calculateMac(String password, String algorithm, InputStream in, byte[] salt) {
		byte[] calculatedMac = null;
		Mac mac = null;
		try {
			mac = Mac.getInstance(algorithm);

			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, Options.IC, 256);
			SecretKey secretKey = skf.generateSecret(spec);

			mac.init(secretKey);

			byte[] buffer = new byte[1024];
			try {
				while (in.read(buffer) != -1) {
					mac.update(buffer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			calculatedMac = mac.doFinal();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return calculatedMac;
	}

}
