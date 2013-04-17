
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
	public static Float getFloat(String Message) throws IOException{
		System.out.print(Message + ": ");
		String value = console.readLine();
		while(!value.matches("[-]?[0-9]+(\\.[0-9]+)?")){
			System.out.print("Die Eingabe muss numerisch sein: ");
			value = console.readLine();
		}
		return java.lang.Float.parseFloat(value);
	}
	public static int getInteger(String Message) throws IOException {
		System.out.print(Message + ":");
		String value = console.readLine();
		while(!value.matches("[-]?[0-9]+")){
			System.out.print("Sie müssen eine Ganzzahl eingeben: ");
			value = console.readLine();
		}
		return Integer.parseInt(value);
	}
	public static void writeln(){
		System.out.println();
	}
	public static void writeln(Object obj){
		System.out.println(obj);
	}
	public static void writeln(String Message, Object... args){
		System.out.println(MessageFormat.format(Message, args));
	}
	public static void clear(){
		for(int i = 0; i < 20; i++)
			writeln();
	}
}
