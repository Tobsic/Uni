import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Console {

	private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	public static Float Float(String Message) throws IOException{
		System.out.print(Message + ": ");
		String value = console.readLine();
		while(!value.matches("[-]?[0-9]+(\\.[0-9]+)?")){
			System.out.print("Die Eingabe muss numerisch sein: ");
			value = console.readLine();
		}
		return java.lang.Float.parseFloat(value);
	}
	public static int Integer(String Message) throws IOException {
		System.out.print(Message + ":");
		String value = console.readLine();
		while(!value.matches("[-]?[0-9]+")){
			System.out.print("Sie müssen eine Ganzzahl eingeben: ");
			value = console.readLine();
		}
		return Integer.parseInt(value);
	}
}
