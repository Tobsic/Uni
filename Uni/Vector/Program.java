import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;



/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 *
 */
public class Program {
	static ArrayList<Vector> vectors;
	static String message;
	
	/**
	 * Program start to open the main menu.
	 * @param args
	 */
	public static void main(String[] args){
		vectors = new ArrayList<Vector>();		
		mainMenu("Hauptmen�");
	}
	
	/**
	 * Main menu with options to create or edit vectors.
	 * @param Title The path to current menu
	 */
	public static void mainMenu(String Title){
		while(true){
			try {
				switch(menu(Title, "Vektor erstellen", "Vektoren bearbeiten", "Hilfe")){
				case 0:
					return;
				case 1:
					vectorCreationMenu(Title + " > Erstellen");
					break;
				case 2:
					Vector choice = vectorSelectionMenu(Title + " > Bearbeiten > Auswahl von v1  (Erster Vektor der Auszuf�hrenden Rechnung und Zielspeicherort)");
					if(choice == null) continue;
					vectorOperationMenu(Title + " > " + choice + " bearbeiten", choice);
					break;
				case 3:
					Console.clear();
					Console.writeln("Dies ist ein Programm f�r die Arbeit mit Vektoren.\n" +
							"Die M�glichkeiten umfassen die Erstellung, Addition und Subtraktion mehrerer Vektoren,\n" +
							"die Multiplikation und Division mit/durch eine Realzahl sowie\n" +
							"das Bilden des Kreuzproduktes, Skalarproduktes und Spatproduktes.\n\n" +
							"Sie k�nnen die Ergebnisse entweder in neu erstellten oder bereits bestehenden Vektoren speichern.\n\n" +
							"Die Men�f�hrung sieht im �berblick wie folgt aus:\n" +
							"1: Vektor erstellen - dies erstellt einen neuen Vektor\n" +
							"1.1:	Eingabe�\t- gibt einen neuen Vektor ein. Keine weitere Aktion\n" +
							"1.2:	Addition\t- addiert zwei Vektoren und speichert das Ergebnis in einem neuen Vektor\n" +
							"1.3:	Differenz\t- subtrahiert zwei Vektoren und speichert das Ergebnis in einem neuen Vektor\n" +
							"1.4:	Multiplizieren\t- multipliziet zwei Vektoren und speichert das Ergebnis in einem neuen Vektor\n" +
							"1.5:	Dividieren\t- dividiert zwei Vektoren und speichert das Ergebnis in einem neuen Vektor\n" +
							"1.6:	Kreuzprodukt\t- bildet das Kreuzprodukt aus zwei Vektoren und speichert das Ergebnis in einem neuen Vektor\n" +
							"1.7:	Einheitsvektor\t- erstellt einen neuen Einheitsvektor\n\n" +
							"2: Vektor bearbeiten\t- hier w�hlen Sie einen bereits bestehenden Vektor an und bearbeiten diesen\n" +
							"2.*.1-9: Differenz, Multiplizieren, Dividieren, Skalarprodukt (nur Ausgabe, kein Speichern), Kreuzprodukt," +
							"Spatprodukt (nur Ausgabe, kein Speichern), In Einheitsvektor umwandeln, L�nge (nur Ausgabe, kein Speichern)\n\n" +
							"Ich hoffe, Ihre Fragen wurden beantwortet.\n" +
							"Version 0.8.3, CCL\n" +
							"Creators: Veit Heller (539501), Tobias Brosge (539713)\n" +
							"\n" +
							"Enter zum beenden");
					Console.getString();
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
				switch (menu(Title, "Addieren", "Differenz", "Multiplizieren", "Dividieren", "Skalarprodukt (nur Ausgabe)", "Kreuzprodukt", "Spatprodukt (nur Ausgabe)", "In Einheitsvektor umwandeln", "L�nge (nur Ausgabe)")) {
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
					setMessage("Die L�nge von {0} ist {1}", v, v.getLength());
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
		int choice = Console.getInteger("Bitte w�hlen Sie die Nummer eines Men�punktes");
		while((choice < 0) || (choice > menupoints.length)){
			drawMenupoints(menupoints);
			choice = Console.getInteger("Die Auswahl liegt ausserhalb des Men�umfanges");
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
