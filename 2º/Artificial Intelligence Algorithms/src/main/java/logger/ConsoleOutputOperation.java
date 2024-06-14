package logger;

/**
 * Output operation that write the messages for the standard output.
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 */
public class ConsoleOutputOperation implements OutputOperation {

	@Override
	public void startWrite() {
		// Do nothing
	}

	@Override
	public void write(String message, Object... params) {
		System.out.print(String.format(message, params));
	}

	@Override
	public void write(String message) {
		System.out.print(message);
	}

	@Override
	public void finishWrite() {
		// Do nothing
	}

	@Override
	public void writeLine(String message) {
		System.out.println(message);

	}

	@Override
	public void writeLine(String message, Object... params) {
		System.out.println(String.format(message, params));

	}

}
