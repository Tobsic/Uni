package de.hellerBrosge.graph.gui;

/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 *
 */
public interface GraphListener {
	/**
	 * Will be performed, when a node should be deleted
	 * @param node Node that should be deleted
	 */
	public void NodeDelete(JNode node);
	/**
	 * Will be performed, when a node should be connected
	 * @param node Node that should be connected to another
	 */
	public void NodeConnect(JNode node);
	/**
	 * Will be performed, when a node should be disconnected
	 * @param node Node that should be disconnected from another
	 */
	public void NodeDisconnect(JNode node);
	/**
	 * Will be performed, when a node has moved
	 * @param node Node that has moved
	 */
	public void NodeMoved(JNode node);
}