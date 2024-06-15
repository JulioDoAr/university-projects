package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import srt.Options;

/**
 * The cipher service test.
 */
class CipherServiceTest {

	/** The cipher service. */
	static CipherService service;

	/** The file service. */
	static FileService fileService;

	/**
	 * Initialize data.
	 */
	@BeforeAll
	static void before() {
		service = CipherService.getInstance();
		fileService = FileService.getInstance();
	}

	/**
	 * Cipher a file, decipher it and check if both are equals.
	 *
	 * @throws Exception the exception in any case.
	 */
	@Test
	void cipherDecipher() throws Exception {
		String password = "Password1234+";
		String algorithm = Options.cipherAlgorithms[1];

		// Generate parameters of encription
		byte[] salt = service.generateSalt(8);
		PBEParameterSpec pPS = service.generateParameterSpec(salt, Options.IC);
		SecretKey sKey = service.generateSecretKey(password.toCharArray(), pPS, algorithm);

		// Resources to use;
		ClassLoader classLoader = getClass().getClassLoader();
		File original = new File("test.txt");
		File ciphed = fileService.changeFileExtension(original, ".cif");
		File deciphed = fileService.changeFileExtension(original, ".cla");

		Cipher cipher;

		// Cipher
		cipher = service.generateEncryptCipher(algorithm, sKey, pPS);
		InputStream in = new FileInputStream(original);
		OutputStream out = new FileOutputStream(ciphed);
		service.cipher(in, out, cipher);
		out.close();
		in.close();

		// Decipher
		cipher = service.generateDecryptCipher(algorithm, sKey, pPS);
		in = new FileInputStream(ciphed);
		out = new FileOutputStream(deciphed);
		service.cipher(in, out, cipher);
		out.close();
		in.close();

		assertTrue(Files.mismatch(Paths.get(original.toURI()), Paths.get(deciphed.toURI())) == -1);
	}

}
