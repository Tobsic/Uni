import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;



/**
 * 
 * @author Tobias Brosge (539713)
 *
 */
public class program {
	static ArrayList<Vector> vectors;
	static String message;
	
	/**
	 * Program start to open the main menu.
	 * @param args
	 */
	public static void main(String[] args){
		vectors = new ArrayList<Vector>();		
		mainMenu("Hauptmenü");

//		Console.writeln("Die Länge des Vektors ist: {0}", new Vector(3).getInput().getLength());
		/*Vector v = new Vector(3);
		v.getInput();
		System.out.Console.writeln("Die Länge des Vektors ist: " + v.getLength());*/
	}
	
	/**
	 * Main menu with options to create or edit vectors.
	 * @param Title The path to current menu
	 */
	public static void mainMenu(String Title){
		while(true){
			try {
				switch(menu(Title, "Vektor erstellen", "Vektoren bearbeiten")){
				case 0:
					return;
				case 1:
					vectorCreationMenu(Title + " > Erstellen");
					break;
				case 2:
					Vector choice = vectorSelectionMenu(Title + " > Bearbeiten > Auswahl von v1  (Erster Vektor der Auszuführenden Rechnung und Zielspeicherort)");
					if(choice == null) continue;
					vectorOperationMenu(Title + " > " + choice + " bearbeiten", choice);
					break;
				}
			} catch (IOException e) {
				setMessage("Fehler: {0}", e.getMessage());
			}
		}
	}
	
	/**
	 * Let the user choice a vector from the saved vectors.
	 * @param Title The path to current menu
	 * @return Chosen vector
	 * @throws IOException
	 */
	public static Vector vectorSelectionMenu(String Title) throws IOException{
		int choice = menu(Title, vectors.toArray());
		return choice == 0 ? null : vectors.get(choice - 1);
	}
	
	/**
	 * Create a vector by different operations (input or operation with existing vectors).
	 * @param Title The path to current menu
	 */
	public static void vectorCreationMenu(String Title){
		while(true){
			try {
				Vector v1, v2, result = new Vector(0);
				float f1;
				switch(menu(Title, "Eingabe", "Addition", "Differenz", "Multiplizieren", "Dividieren", "Kreuzprodukt", "Einheitsvektor")){
				case 0:
					return;
				case 1:
					result = new Vector(Console.getInteger("Anzahl der Dimensionen")).getInput();
					break;
				case 2:
					v1 = vectorSelectionMenu(Title + " > Addition (v1 + v2) > Auswahl von v1");
					if(v1 == null) continue;
					v2 = vectorSelectionMenu(Title + " > Addition (" + v1 + " + v2) > Auswahl von v2");
					if(v2 == null) continue;
					result = Vector.Sum(v1, v2);
					break;
				case 3:
					v1 = vectorSelectionMenu(Title + " > Differenz (v1 - v2) > Auswahl von v1");
					if(v1 == null) continue;
					v2 = vectorSelectionMenu(Title + " > Differenz (" + v1 + " - v2) > Auswahl von v2");
					if(v2 == null) continue;
					result = v1.Clone().Diff(v2);
					break;
				case 4:
					v1 = vectorSelectionMenu(Title + " > Multiplizieren (v * f) > Auswahl von v");
					if(v1 == null) continue;
					f1 = Console.getFloat("Multiplizieren mit (f)");
					result = v1.Clone().Multiply(f1);
					break;
				case 5:
					v1 = vectorSelectionMenu(Title + " > Dividieren (v / f) > Auswahl von v");
					if(v1 == null) continue;
					f1 = Console.getFloat("Dividieren durch (f)");
					result = v1.Clone().Divide(f1);
					break;
				case 6:
					v1 = vectorSelectionMenu(Title + " > Kreuzprodukt (v1 x v2) > Auswahl von v1");
					if(v1 == null) continue;
					v2 = vectorSelectionMenu(Title + " > Kreuzprodukt (" + v1 + " x v2) > Auswahl von v2");
					if(v2 == null) continue;
					result = v1.Clone().CrossProduct(v2);
					break;
				case 7:
					v1 = vectorSelectionMenu(Title + " > Einheitsvektor von v1 > Auswahl von v1");
					if(v1 == null) continue;
					result = v1.Clone().toUnit();
					break;
				}
				vectors.add(result);
				setMessage("{0} wurde erfolgreich erstellt und gespeichert.", result);
			} catch (Exception e) {
				setMessage("Fehler: {0}", e.getMessage());
			}
		}
	}
	
	/**
	 * Menu to edit the given vector whith a operation of the menu (addition, subtraktion, dotproduct, crossproduct, trilpeproduct, to unit, length).
	 * @param Title The path to current menu
	 * @param v Vector to edit
	 */
	public static void vectorOperationMenu(String Title, Vector v){
		Vector v1, v2;
		float f1;
		while(true){
			try {
				switch (menu(Title, "Addieren", "Differenz", "Multiplizieren", "Dividieren", "Skalarprodukt (nur Ausgabe)", "Kreuzprodukt", "Spatprodukt (nur Ausgabe)", "In Einheitsvektor umwandeln", "Länge (nur Ausgabe)")) {
				case 0:
					return;
				case 1:
					v1 = vectorSelectionMenu(Title + " > Addition > Vektorwahl von v2 ( " + v + " + v2 )");
					if(v1 == null)
						continue;
					v.Add(v1);
					break;
				case 2:
					v1 = vectorSelectionMenu(Title + " > Differenz > Vektorwahl von v2 ( " + v + " - v2 )");
					if(v1 == null)
						continue;
					v.Diff(v1);
					break;
				case 3:
					f1 = Console.getFloat("{0} multipliziren mit", v);
					v.Multiply(f1);
					break;
				case 4:
					f1 = Console.getFloat("{0} dividieren durch", v);
					v.Divide(f1);
					break;
				case 5:
					v1 = vectorSelectionMenu(Title + " > Skalarprodukt > Vektorwahl von v2 ( " + v + " x v2 )");
					if(v1 == null)
						continue;
					setMessage("Das Skalarprodukt von {0} und {1} ist: {2}", v, v1, v.DotProduct(v1));
					continue; // Message nicht neu setzen
				case 6:
					v1 = vectorSelectionMenu(Title + " > Differenz > Vektorwahl von v2 ( " + v + " + v2 )");
					if(v1 == null)
						continue;
					v.Diff(v1);
					break;
				case 7:
					v1 = vectorSelectionMenu(Title + " > Skalarprodukt > Vektorwahl von v2 ( (" + v + " x v2) * v3 )" );
					if(v1 == null)
						continue;
					v2 = vectorSelectionMenu(Title + " > Skalarprodukt > Vektorwahl von v3 ( (" + v + " x " + v1 + ") * v3 )");
					if(v2 == null)
						continue;
					setMessage("Das Spatprodukt von {0}, {1} und {2} ist: {3}", v, v1, v2, Vector.TripleProduct(v, v1, v2));
					continue; // Message nicht neu setzen
				case 8:
					v.toUnit();
					break;
				case 9:
					setMessage("Die Länge von {0} ist {1}", v, v.getLength());
					continue; // Message nicht neu setzen
				}
				setMessage("Folgendes wurde Berechnet: {0}", v);
			} catch (Exception e) {
				setMessage("Fehler: {0}", e.getMessage());
			}
		}
	}
	
	/**
	 * Create a menu in the console.
	 * @param Title The path to current menu
	 * @param menupoints Titles of the menu points
	 * @return Chosen menu point number (0 is exit)
	 * @throws IOException
	 */
	public static int menu(String Title, Object...menupoints) throws IOException{
		Console.clear();
		Console.writeln(Title);
		Console.writeln();
		drawMenupoints(menupoints);
		if(message != null){
			Console.writeln(message);
			Console.writeln();
			message = null;
		}
		int choice = Console.getInteger("Bitte wählen Sie die Nummer eines Menüpunktes");
		while((choice < 0) || (choice > menupoints.length)){
			drawMenupoints(menupoints);
			choice = Console.getInteger("Die Auswahl liegt auserhalb des Menüumfanges");
		}
		return choice;
	}
	
	/**
	 * List the menu points with number (Nr: Title).
	 * @param object Titles of menu points
	 */
	public static void drawMenupoints(Object...object){
		for(int i = 0; i < object.length; i++)
			Console.writeln("{0}:\t{1}", i + 1, object[i]);
		Console.writeln();
		Console.writeln("0:\tBeenden");
		Console.writeln();
	}
	
	/**
	 * Set the message that should shown in the next menu.
	 * @param Message Format String
	 * @param args Arguments for the formated message string
	 */
	private static void setMessage(String Message, Object...args){
		message = MessageFormat.format(Message, args);
	}
}
