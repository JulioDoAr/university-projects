package logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Application logger to unify all outputs in it. This class is responsible of
 * write all the messages generated by the application on their respective
 * places.
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 */
public class Logger implements OutputOperation {

	/**
	 * Static instance of the singleton
	 */
	private static Logger instance;

	/**
	 * List of output operations to do for each message.
	 */
	private List<OutputOperation> operations;

	/**
	 * Private default constructor to fit the Singleton pattern
	 */
	private Logger() {
		operations = new ArrayList<OutputOperation>();
	};

	/**
	 * Add a new group of operations
	 *
	 * @param operation The group of operations to add.
	 */
	public void addOutputOperation(OutputOperation operation) {
		operations.add(operation);
	}

	/**
	 * Resets the logger
	 */
	public void reset() {
		operations.clear();
	}

	/**
	 * Singleton. Get the instance
	 *
	 * @return The global instance of the object.
	 */
	public static Logger getInstance() {
		if (instance == null)
			instance = new Logger();
		return instance;
	}

	public void startWrite() {
		for (OutputOperation operation : operations)
			operation.startWrite();
	}

	public void write(String message) {
		for (OutputOperation operation : operations)
			operation.write(message);
	}

	public void write(String message, Object... params) {
		for (OutputOperation operation : operations)
			operation.write(message, params);
	}

	public void finishWrite() {
		for (OutputOperation operation : operations)
			operation.finishWrite();
	}

	public void writeLine(String message) {
		for (OutputOperation operation : operations)
			operation.writeLine(message);
	}

	public void writeLine(String message, Object... params) {
		for (OutputOperation operation : operations)
			operation.writeLine(message, params);
	}

	public void write(Object obj) {
		for (OutputOperation operation : operations)
			operation.write(obj);

	}

	public void writeLine(Object obj) {
		for (OutputOperation operation : operations)
			operation.writeLine(obj);

	}
}
