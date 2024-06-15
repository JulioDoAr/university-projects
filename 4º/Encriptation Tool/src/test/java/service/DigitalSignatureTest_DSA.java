package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import srt.Header;
import srt.Options;

class DigitalSignatureTest_DSA {

	private static KeyService service;
	private static FileService fileService;

	@BeforeAll
	static void beforeAll() {
		service = KeyService.getInstance();
		service.generateKeys("DSA");
		fileService = FileService.getInstance();
	}

	@Test
	void test_signFile() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, IOException {
		File toSign = new File("test.txt");
		File signed = new File("test.fir");
		signed.delete();

		byte[] signature = service.createSignature(Options.signAlgorithms[1], service.getKeys().getPrivate(), toSign);

		Header header = new Header(Options.OP_SIGNED, Options.cipherAlgorithms[0], Options.signAlgorithms[1],
				signature);

		signed = FileService.getInstance().changeFileExtension(toSign, ".fir");
		FileService.getInstance().writeTo(header, toSign, signed);

		assertTrue(signed.exists());
	}

	@Test
	void test_verifySignature() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, IOException {
		File signed = new File("test.fir");
		FileInputStream in = new FileInputStream(signed);

		Header header = fileService.loadHeader(in);
		assertTrue(header != null);
		String algorithm = header.getAlgorithm2();
		byte[] signature = header.getData();

		boolean result = service.verifySignature(algorithm, service.getKeys().getPublic(), in, signature);
		assertTrue(result);
	}

	@Test
	void test_cipherWithPublic() throws Exception {
		File toCipher = new File("test.txt");
		File ciphed = new File("test.cif");
		ciphed.delete();

		ciphed = FileService.getInstance().changeFileExtension(toCipher, ".cif");
		service.cipherWithKey(service.getKeys().getPublic(), toCipher, new FileOutputStream(ciphed));

		assertTrue(ciphed.exists());
		assertTrue(ciphed.length() > 0);
	}

	@Test
	void test_decipherWithPrivate() throws Exception {
		File toDecipher = new File("test.cif");
		File deciphed = new File("test3.txt");
		deciphed.delete();
		String algorithm = Options.cipherAlgorithms[1];

		deciphed = FileService.getInstance().changeFileExtension(toDecipher, "3.txt");
		InputStream in = new FileInputStream(toDecipher);
		OutputStream out = new FileOutputStream(deciphed);

		service.decipherWithSignature(service.getKeys().getPrivate(), in, out);

		assertTrue(deciphed.exists());
		byte[] f1 = Files.readAllBytes(Paths.get(deciphed.getAbsolutePath()));
		byte[] f2 = Files.readAllBytes(Paths.get(new File("test2.txt").getAbsolutePath()));
		// Devuelve falso, pero a la vista son iguales.
		assertTrue(Arrays.equals(f1, f2));
	}

}
