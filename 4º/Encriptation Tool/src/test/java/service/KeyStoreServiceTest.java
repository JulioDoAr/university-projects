package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class KeyStoreServiceTest {

	private static KeyStoreService service;

	@BeforeAll
	static void beforeAll() {
		service = KeyStoreService.getInstance();
	}

	@Test
	void openExistingKeyStore() throws NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			IOException, KeyStoreException, UnrecoverableKeyException {// Carga de un almacén de claves:
		String fileName = "miks";
		char[] password = "mipasswd".toCharArray();

		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(fileName), password);

		// Acceso a contenido:

		Enumeration<String> e = keyStore.aliases();
		KeyPair pair = null;
		while (e.hasMoreElements()) {
			String alias = e.nextElement();
			if (keyStore.isKeyEntry(alias)) {
				PrivateKey kr = (PrivateKey) keyStore.getKey(alias, password);
				Certificate cert = keyStore.getCertificate(alias);
				PublicKey ku = cert.getPublicKey();
				pair = new KeyPair(ku, kr);
			}
		}
		assertNotNull(pair);
		assertNotNull(pair.getPrivate());
		assertNotNull(pair.getPublic());
	}

}
