package de.hellerBrosge.graph.gui;

/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 * 
 */
public class GraphException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new not specific GraphException
	 */
	public GraphException(){
		super();
	}
	
	/**
	 * Crate a new GraphException with a message
	 * @param message Message for the new GraphException
	 */
	public GraphException(String message){
		super(message);
	}
}
