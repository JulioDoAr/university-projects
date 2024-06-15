package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class KeyManagementTest {

	private static KeyService service;

	@BeforeAll
	static void beforeAll() {
		service = KeyService.getInstance();
	}

	@Test
	void generateKeys_DSA() throws NoSuchAlgorithmException {
		service.generateKeys("DSA");
	}

	@Test
	void generateKeys_RSA() throws NoSuchAlgorithmException {
		service.generateKeys("RSA");
	}

	@Test
	void saveKey() {
		service.generateKeys("DSA");

		File toSave = new File("test_keyToLoad.key");
		if (toSave.exists())
			toSave.delete();

		service.saveKey(toSave);

		assertTrue(toSave.exists());
	}

	@Test
	void loadKeys() {
		service.deleteKeys();

		File toLoad = new File("test_keyToSave.key");
		assertTrue(toLoad.exists(), "El fichero para la carga de claves no existe");
		service.loadKeys(toLoad);
		assertTrue(service.getKeys() != null);
	}
}
