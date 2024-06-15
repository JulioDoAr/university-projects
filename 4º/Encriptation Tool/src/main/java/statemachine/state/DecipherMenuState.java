package statemachine.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

import service.CipherService;
import service.FileService;
import srt.Header;
import srt.Options;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * MENU: The menu to decipher a file.
 */
public class DecipherMenuState implements State {

	/** The cipher service. */
	private CipherService cipherService;

	/** The file service. */
	private FileService fileService;

	/**
	 * Instantiates a new instance.
	 */
	public DecipherMenuState() {
		cipherService = CipherService.getInstance();
		fileService = FileService.getInstance();
	}

	/**
	 * Menu to decipher a file.
	 *
	 * @param stateMachine the state machine
	 * @param data         the general data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {

		// Pedir fichero a cifrar
		stateMachine.nextState(new AskForFileState("Insert a file to decipher."));

		// Pedir clave
		stateMachine.nextState(new AskForPasswordState());

		// Descifrar
		File outputFile = fileService.changeFileExtension(data.getSelectedFile(), ".cla");

		Header header = new Header();

		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			// Leemos la cabecera
			in = new FileInputStream(data.getSelectedFile());
			out = new FileOutputStream(outputFile);

			if (!header.load(in))
				throw new Exception("Header could not be loaded.");
			// Generar SALT
			String algorithm = header.getAlgorithm1();
			byte[] salt = header.getData();
			PBEParameterSpec parameters = cipherService.generateParameterSpec(salt, Options.IC);
			SecretKey sk = cipherService.generateSecretKey(data.getPassword().toCharArray(), parameters, algorithm);
			Cipher cipher = cipherService.generateDecryptCipher(algorithm, sk, parameters);

			cipherService.cipher(in, out, cipher);
			System.out.println(String.format("Encriptación correcta. Fichero creado %s", outputFile.getPath()));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
