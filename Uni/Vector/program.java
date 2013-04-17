import java.io.IOException;
import java.util.ArrayList;



/**
 * 
 * @author Tobias Brosge (539713)
 *
 */
public class program {
	static ArrayList<Vector> Vectors;
	static String Message;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Vectors = new ArrayList<Vector>();		
		mainMenu("Hauptmenü");

		/*Vector v = new Vector(3);
		v.getInput();
		System.out.Console.writeln("Die Länge des Vektors ist: " + v.getLength());*/
	}
	
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
					Vector choice = vectorSelectionMenu(Title + " > Bearbeiten > Auswahl");
					if(choice == null) continue;
					vectorOperationMenu(Title + " > " + choice + " bearbeiten", choice);
					break;
				}
			} catch (IOException e) {
				Message = "Fehler: " + e.getMessage();
			}
		}
	}
	
	public static Vector vectorSelectionMenu(String Title) throws IOException{
		int choice = menu(Title, Vectors.toArray());
		return choice == 0 ? null : Vectors.get(choice - 1);
	}
	
	public static void vectorCreationMenu(String Title){
		while(true){
			try {
				Vector v1, v2, result = new Vector(0);
				float f1;
				switch(menu(Title, "Eingabe", "Addition", "Differenz", "Multiplizieren", "Dividieren", "Kreuzprodukt")){
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
				}
				Vectors.add(result);
				Message = result + " wurde erfolgreich erstellt und gespeichert.";
			} catch (Exception e) {
				Message = "Fehler: " + e.getMessage();
			}
		}
	}
	
	public static void vectorOperationMenu(String Title, Vector v){
		Vector v1, v2;
		while(true){
			try {
				switch (menu(Title, "Addieren", "Subtrahieren", "Multiplizieren", "Dividieren", "Skalarprodukt (nur Ausgabe)", "Kreuzprodukt", "Spatprodukt (nur Ausgabe)")) {
				case 0:
					return;
				case 1:
			v1 = vectorSelectionMenu(Title + " > Addition > Vektorwahl (Erster Vektor der Auszuführenden Rechnung und Zielspeicherort)");
					if(v1 == null)
						continue;
					v.Add(v1);
					break;
				case 2:
			v1 = vectorSelectionMenu(Title + " > Subtraktion > Vektorwahl");
					if(v1 == null)
						continue;
					v.Diff(v1);
					break;
				case 3:
					
					break;
				}
			} catch (Exception e) {
				Message = "Fehler: " + e.getMessage();
			}
		}
	}
	
	public static int menu(String Title, Object...menupoints) throws IOException{
		Console.clear();
		Console.writeln(Title);
		Console.writeln();
		drawMenupoints(menupoints);
		if(Message != null){
			Console.writeln(Message);
			Console.writeln();
			Message = null;
		}
		int choice = Console.getInteger("Bitte wählen Sie die Nummer eines Menüpunktes");
		while((choice < 0) || (choice > menupoints.length)){
			drawMenupoints(menupoints);
			choice = Console.getInteger("Die Auswahl liegt auserhalb des Menüumfanges");
		}
		return choice;
	}
	
	public static void drawMenupoints(Object...object){
		for(int i = 0; i < object.length; i++)
			Console.writeln("{0}:\t{1}", i + 1, object[i]);
		Console.writeln();
		Console.writeln("0:\tBeenden");
		Console.writeln();
	}
}
