package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Output operation that write the messages in a file. It keeps opened while the
 * application is working.
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 */
public class FileOutputOperation implements OutputOperation {

	/**
	 * Output url where write the log
	 */
	private String outputDirectory;

	/**
	 * IO manager to create and write the file
	 */
	private BufferedWriter writer;

	/**
	 * Create a FileOutputOperation providing the url where write the log
	 *
	 * @param filename Url where the file will be created. It can be absolute or
	 *                 relative
	 */
	public FileOutputOperation(String filename) {
		outputDirectory = filename;
	}

	public void startWrite() {
		File file = new File(outputDirectory);
		if (file.exists())
			file.delete();
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String message, Object... params) {
		try {
			writer.write(String.format(message, params));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String message) {
		try {
			writer.write(message);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void finishWrite() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeLine(String message) {
		try {
			writer.write(message + "/n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeLine(String message, Object... params) {
		try {
			writer.write(String.format(message, params) + "/n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(Object obj) {
		try {
			writer.write(obj.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeLine(Object obj) {
		try {
			writer.write(obj.toString() + "/n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
