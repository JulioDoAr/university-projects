package service;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import srt.Header;

/**
 * Singleton. Methods about files
 */
public class FileService {

	/** The Constant BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 1024;

	/** The instance. */
	private static FileService instance = null;

	/**
	 * Instantiates a new file service.
	 */
	private FileService() {
	}

	/**
	 * Gets the single instance of FileService.
	 *
	 * @return single instance of FileService
	 */
	public static FileService getInstance() {
		if (instance == null)
			instance = new FileService();
		return instance;
	}

	/**
	 * Check if a file exist.
	 *
	 * @param url the url
	 * @return the file if exist. <b>Null</b> if not.
	 */
	public File exist(String url) {
		File f = new File(url);
		if (f.exists() && !f.isDirectory()) {
			return f;
		} else
			return null;
	}

	/**
	 * Create a new file with the same name as originalFile but changes its
	 * extension to newExtension.
	 *
	 * @param originalFile the original file
	 * @param newExtension the new extension
	 * @return the new file
	 */
	public File changeFileExtension(File originalFile, String newExtension) {

		// Ensure the new extension starts with a dot.
		if (!newExtension.startsWith(".")) {
			newExtension = "." + newExtension;
		}

		String originalFileName = originalFile.getName();
		String newFileName = originalFileName.replaceAll("\\.\\w+$", newExtension);

		String parentDirectory = originalFile.getParent();
		File renamedFile = new File(parentDirectory, newFileName);

		try {
			renamedFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return renamedFile;
	}

	/**
	 * Write into outFile the header and the content of inFile.
	 *
	 * @param header  the header
	 * @param inFile  the input file
	 * @param outFile the output file
	 */
	public void writeTo(Header header, File inFile, File outFile) {
		OutputStream out = null;
		InputStream in = null;
		try {
			out = new FileOutputStream(outFile);
			in = new FileInputStream(inFile);
			header.save(out);
			writeTo(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSafely(in);
			closeSafely(out);
		}
	}

	public void writeTo(Header header, byte[] content, File outFile) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(outFile);
			header.save(out);
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSafely(out);
		}
	}

	/**
	 * Write the header and the content of one stream into another
	 *
	 * @param header the header
	 * @param in     the input stream
	 * @param out    the output stream
	 */
	public void writeTo(Header header, InputStream in, OutputStream out) {
		try {
			header.save(out);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		writeTo(in, out);
	}

	/**
	 * Write the content of one stream into another
	 *
	 * @param in  the input stream
	 * @param out the output stream
	 */
	public void writeTo(InputStream in, OutputStream out) {
		byte[] buffer = new byte[BUFFER_SIZE];

		// Variable para almacenar la cantidad de bytes leídos
		int bytesRead;

		// Leer bytes del InputStream y escribir en el OutputStream
		try {
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close a stream safely
	 *
	 * @param stream the stream
	 */
	public void closeSafely(Closeable stream) {
		if (stream != null)
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public Header loadHeader(InputStream in) {
		Header header = null;
		try {
			header = new Header();
			header.load(in);
		} catch (Exception e) {
			header = null;
		}

		return header;
	}
}
