import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;



public class program {

	static ArrayList<Vector> Vectors;
	static int selected = -1;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Vectors = new ArrayList<Vector>();
		/*Vector v = new Vector(3);
		v.getInput();
		System.out.println("Die L�nge des Vektors ist: " + v.getLength());*/
		
		mainMenu();
	}
	
	public static void mainMenu() throws IOException{
		while(true){
			switch(menu("Vektoren Ausw�hlen", "Vektor erstellen")){
			case 0:
				return;
			case 1:
				vectorSelectionMenu();
				break;
			case 2:
				vectorCreationMenu();
				break;
			}
		}
	}
	
	public static void vectorSelectionMenu() throws IOException{
		int choice = menu(Vectors.toArray());
		if(choice == 0)
			return;
		selected = choice - 1;
	}
	
	public static void vectorCreationMenu() throws IOException{
		Vectors.add(new Vector(ConsoleReader.Integer("Anzahl der Dimensionen")).getInput());
	}
	
	public static int menu(Object...menupoints) throws IOException{
		drawMenupoints(menupoints);
		int choice = ConsoleReader.Integer("Bitte geben w�hlen Sie die Nummer eines Men�punktes");
		while((choice < 0) || (choice > menupoints.length)){
			drawMenupoints(menupoints);
			choice = ConsoleReader.Integer("Die Auswahl liegt auserhalb des Men�umfanges");
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
