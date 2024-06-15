package service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import srt.Header;
import srt.Options;

class HashTest {

	static HashService hashService;
	static FileService fileService;

	@BeforeAll
	public static void beforeAll() {
		hashService = HashService.getInstance();
		fileService = FileService.getInstance();
	}

	@Test
	void test() throws NoSuchAlgorithmException, IOException {
		File inputFile = new File("test.txt");
		assertTrue(inputFile.exists(), "InputFile does not exist");
		// Preguntar por algoritmo
		String algorithm = Options.hashmacAlgorithms[4];
		// Preguntar por contrasenia
		String secret = "Contrasenia";

		InputStream in = null;
		byte[] hash = null;
		try {
			in = new FileInputStream(inputFile);
			hash = hashService.calculateHash(secret, algorithm, in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(in);
		}

		assertTrue(hash != null);

		Header header = new Header(Options.OP_HASH_MAC, algorithm, Options.hashmacAlgorithms[0], hash);

		File output = new File(inputFile.getParent(), "test_hashed.txt");
		OutputStream os = null;
		in = null;
		try {
			os = new FileOutputStream(output);
			in = new FileInputStream(inputFile);
			header.save(os);

			fileService.writeTo(in, os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileService.closeSafely(in);
			fileService.closeSafely(os);
		}
		assertTrue(true);
	}

	@Test
	void test_differentHash() throws FileNotFoundException {
		String algorithm = Options.hashmacAlgorithms[2];
		String password = "password";

		InputStream in1 = new FileInputStream(new File("test.txt"));
		InputStream in2 = new FileInputStream(new File("test_vacio.txt"));

		byte[] hash1 = hashService.calculateHash(password, algorithm, in1);
		byte[] hash2 = hashService.calculateHash(password, algorithm, in2);

		assertNotEquals(hash1, hash2);
	}

}
