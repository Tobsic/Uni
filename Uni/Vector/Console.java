
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;


/**
 * 
 * @author Tobias Brosge (539713)
 *
 */

public class Console {
	private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Writes a message as formated string to console.
	 * @param Message Formated string to write
	 * @param args Arguments to bring into formated string
	 */
	public static void write(String Message, Object... args){
		System.out.print(MessageFormat.format(Message, args));
	}
	/**
	 * Writes a empty line to console.
	 */
	public static void writeln(){
		System.out.println();
	}
	/**
	 * writes the string of an object to a line in the console.
	 * @param obj Object to convert to string
	 */
	public static void writeln(Object obj){
		System.out.println(obj);
	}
	/**
	 * Writes a message as formated string in one line to console.
	 * @param Message Formated string to write
	 * @param args Arguments to bring into formated string
	 */
	public static void writeln(String Message, Object... args){
		System.out.println(MessageFormat.format(Message, args));
	}
	/**
	 * Writes 20 empty lines to skip the view of current console content.
	 */
	public static void clear(){
		for(int i = 0; i < 20; i++)
			writeln();
	}
	/**
	 * Gives the ability to read a float from console and repeat if no float entered.
	 * @param Message Formated string that stands in front of the input cursor
	 * @param args Arguments to bring into formated string
	 * @return User input as float
	 * @throws IOException
	 */
	public static float getFloat(String Message, Object...args) throws IOException{
		write(Message + ": ", args);
		String value = console.readLine();
		while(!value.matches("[-]?[0-9]+(\\.[0-9]+)?")){
			System.out.print("Die Eingabe muss numerisch sein: ");
			value = console.readLine();
		}
		return Float.parseFloat(value);
	}
	/**
	 * Gives the ability to read a integer from console and repeat if no integer entered.
	 * @param Message Formated string that stands in front of the input cursor
	 * @param args Arguments to bring into formated string
	 * @return User input as integer
	 * @throws IOException
	 */
	public static int getInteger(String Message, Object...args) throws IOException {
		write(Message + ":", args);
		String value = console.readLine();
		while(!value.matches("[-]?[0-9]+")){
			write("Sie müssen eine Ganzzahl eingeben: ");
			value = console.readLine();
		}
		return Integer.parseInt(value);
	}
	/**
	 * Gives the ability to read a double from console and repeat if no double entered.
	 * @param Message Formated string that stands in front of the input cursor
	 * @param args Arguments to bring into formated string
	 * @return User input as double
	 * @throws IOException
	 */
	public static double getDouble(String Message, Object...args) throws IOException{
		write(Message + ":", args);
		String value = console.readLine();
		while(!value.matches("[-]?[0-9]+(.[0-9]+)?")){
			write("Die Eingabe muss numerisch sein: ");
			value = console.readLine();
		}
		return Double.parseDouble(value);
	}
}
