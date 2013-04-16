import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;



public class program {

	static ArrayList<Vector> Vectors;
	
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args){
		Vectors = new ArrayList<Vector>();		
		mainMenu();

		/*Vector v = new Vector(3);
		v.getInput();
		System.out.println("Die Länge des Vektors ist: " + v.getLength());*/
	}
	
	public static void mainMenu(){
		while(true){
			try {
				switch(menu("Vektor erstellen", "Vektoren bearbeiten")){
				case 0:
					return;
				case 1:
					vectorCreationMenu();
					break;
				case 2:
					Vector choice = vectorSelectionMenu();
					if(choice == null) continue;
					vectorOperationMenu(choice);
					break;
				}
			} catch (IOException e) {
				println("Fehler: " + e.getMessage());
			}
		}
	}
	
	public static Vector vectorSelectionMenu() throws IOException{
		int choice = menu(Vectors.toArray());
		return choice == 0 ? null : Vectors.get(choice - 1);
	}
	
	public static void vectorCreationMenu(){
		Vector v1, v2;
		while(true){
			try {
				switch(menu("Eingabe", "Addition", "Division")){
				case 0:
					return;
				case 1:
					Vectors.add(new Vector(Console.Integer("Anzahl der Dimensionen")).getInput());
					break;
				case 2:
					v1 = vectorSelectionMenu();
					if(v1 == null) continue;
					v2 = vectorSelectionMenu();
					if(v2 == null) continue;
					Vectors.add(Vector.Sum(v1, v2));
					break;
				case 3:
					v1 = vectorSelectionMenu();
					if(v1 == null) continue;
					v2 = vectorSelectionMenu();
					if(v2 == null) continue;
					Vectors.add(v1.Clone().Diff(v2));
					break;
				}
			} catch (Exception e) {
				println("Fehler: " + e.getMessage());
			}
		}
	}
	
	public static void vectorOperationMenu(Vector v){
		Vector choice;
		while(true){
			try {
				switch (menu("Addieren", "Subtrahieren")) {
				case 0:
					return;
				case 1:
					choice = vectorSelectionMenu();
					if (choice == null)
						continue;
					v.Add(choice);
					break;
				case 2:
					choice = vectorSelectionMenu();
					if (choice == null)
						continue;
					v.Diff(choice);
					break;
				}
			} catch (Exception e) {
				println("Fehler: " + e.getMessage());
			}
		}
	}
	
	public static int menu(Object...menupoints) throws IOException{
		drawMenupoints(menupoints);
		int choice = Console.Integer("Bitte wählen Sie die Nummer eines Menüpunktes");
		while((choice < 0) || (choice > menupoints.length)){
			drawMenupoints(menupoints);
			choice = Console.Integer("Die Auswahl liegt auserhalb des Menüumfanges");
		}
		return choice;
	}
	
	public static void drawMenupoints(Object...object){
		clear();
		println("0:\tBeenden");
		for(int i = 0; i < object.length; i++)
			println("{0}:\t{1}", i + 1, object[i]);
		println("");
	}
	
	public static void println(String Message, Object... args){
		System.out.println(MessageFormat.format(Message, args));
	}
	public static void clear(){
		for(int i = 0; i < 20; i++)
			println("");
	}
}
