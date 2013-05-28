
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;


/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 *
 */

public class Console {
	/**
	 * Concat the Values of a string array and seperate them whith a given string 
	 * @param Array string array to join
	 * @param d seperator
	 * @return joined string of array values
	 */
	public static String join(String d, String...Array)
	{
	        if (Array.length == 0) return "";
	        String result = Array[0];
	        for(int i = 1; i < Array.length; i++)
	            result += d + Array[i];
	        return result;
	}
	/**
	 * Concat the Values of a string array and seperate them whith a given string 
	 * @param Array string array to join
	 * @param d seperator
	 * @return joined string of array values
	 */
	public static String join(String d, double...Array)
	{
	        if (Array.length == 0) return "";
	        String result = Array[0] + "";
	        for(int i = 1; i < Array.length; i++)
	            result += d + Array[i];
	        return result;
	}
	
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
	/**
	 * Gives the ability to read a single character from console and repeat if no single character entered.
	 * @param Message Formated string that stands in front of the input cursor
	 * @param args Arguments to bring into formated string
	 * @return User input as single character
	 * @throws IOException
	 */
	public static char getChar(String Message, Object...args) throws IOException{
		String result;
		write(Message + ":", args);
		while((result = console.readLine()).length() != 1)
			write("Es darf nur ein Char eingegeben werden: ");
		return result.charAt(0);
	}
	/**
	 * Gives the ability to read a entered line from console.
	 * @param Message Formated string that stands in front of the input cursor
	 * @param args Arguments to bring into formated string
	 * @return User input as string
	 * @throws IOException
	 */
	public static String getString(String Message, Object...args) throws IOException {
		write(Message + ":", args);
		return console.readLine();
	}
	/**
	 * Gives the ability to read a entered line from console without message.
	 * @return User input as string
	 * @throws IOException
	 */
	public static String getString() throws IOException{
		return console.readLine();
	}
}
