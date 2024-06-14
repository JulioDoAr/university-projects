package logger;

/**
 * Output operation that write the messages for the standard output.
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 */
public class ConsoleOutputOperation implements OutputOperation {

	public void startWrite() {
		// Do nothing
	}

	public void write(String message, Object... params) {
		System.out.print(String.format(message, params));
	}

	public void write(String message) {
		System.out.print(message);
	}

	public void finishWrite() {
		// Do nothing
	}

	public void writeLine(String message) {
		System.out.println(message);

	}

	public void writeLine(String message, Object... params) {
		System.out.println(String.format(message, params));
	}

	public void write(Object obj) {
		System.out.print(obj.toString());
	}

	public void writeLine(Object obj) {
		System.out.println(obj.toString());
	}

}
