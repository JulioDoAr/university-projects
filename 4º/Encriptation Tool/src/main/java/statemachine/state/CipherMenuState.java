package statemachine.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import service.CipherService;
import service.FileService;
import srt.Header;
import srt.Options;
import statemachine.GeneralData;
import statemachine.State;
import statemachine.StateMachine;

/**
 * MENU: The menu to cipher a file.
 */
public class CipherMenuState implements State {

	/** The cipher service. */
	private CipherService cipherService;

	/** The file service. */
	private FileService fileService;

	/**
	 * Instantiates a new instance.
	 */
	public CipherMenuState() {
		cipherService = CipherService.getInstance();
		fileService = FileService.getInstance();
	}

	/**
	 * Menu to cipher a file.
	 *
	 * @param stateMachine the state machine
	 * @param data         the general data
	 */
	@Override
	public void handle(StateMachine stateMachine, GeneralData data) {
		// Pedir clave por duplicado
		stateMachine.nextState(new AskForDuplicatePasswordState());
		// Pedir fichero a cifrar
		stateMachine.nextState(new AskForFileState("Insert a file to cipher."));

		// Selección de algoritmo
		stateMachine.nextState(new AskForAlgorithmState());

		// Generar SALT
		stateMachine.nextState(new GenerateCipherState());

		// Añadir Header al fichero
		Header header = new Header(Options.OP_SYMMETRIC_CIPHER, data.getAlgorithm(),
				Options.authenticationAlgorithms[0], data.getSalt());

		// Crear fichero
		File outputFile = fileService.changeFileExtension(data.getSelectedFile(), ".cif");

		// Escribir en el fichero
		if (outputFile.exists()) {
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				outputFile.createNewFile();
				out = new FileOutputStream(outputFile);
				in = new FileInputStream(data.getSelectedFile());

				if (!header.save(out))
					throw new Exception("Header could not be saved.");
				cipherService.cipher(in, out, data.getCipher());
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

}
