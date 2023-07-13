package com.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Output operation that write the messages in a file. It keeps opened while the
 * application is working.
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

	@Override
	public void startWrite() {
		File file = new File(outputDirectory);
		if (file.exists()) {
			file.delete();
		}
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(String message, Object... params) {
		try {
			writer.write(String.format(message, params) + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(String message) {
		try {
			writer.write(message + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finishWrite() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
