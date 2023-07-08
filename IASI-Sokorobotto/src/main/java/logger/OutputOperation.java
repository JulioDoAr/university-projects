package logger;

/**
 * Visitor pattern. Interface of operations that need to be implemented by the
 * output writers.
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 */
public interface OutputOperation {

	/**
	 * Do the actions needed to start the process of write.
	 */
	void startWrite();

	/**
	 * Write the message.
	 *
	 * @param message Message
	 */
	void write(String message);

	/**
	 * Write the message formatted with the parameters provided.
	 *
	 * @param message Message template.
	 * @param params  Objects to add to the template.
	 */
	void write(String message, Object... params);

	/**
	 * Write the message.
	 *
	 * @param message Message
	 */
	void writeLine(String message);

	/**
	 * Write the message formatted with the parameters provided.
	 *
	 * @param message Message template.
	 * @param params  Objects to add to the template.
	 */
	void writeLine(String message, Object... params);

	/**
	 * Do the actions needed to finish the process of write.
	 */
	void finishWrite();
}
