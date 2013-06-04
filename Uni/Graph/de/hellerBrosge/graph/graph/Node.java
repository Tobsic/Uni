package de.hellerBrosge.graph.graph;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 *
 */
public class Node<T extends Serializable> implements Serializable{
	private static final long serialVersionUID = 1L;
	T value;
	ArrayList<Node<T>> subNodes;
	
	/**
	 * Create a new Node with the given value
	 * @param value Value that the node should have
	 */
	public Node(T value){
		this.value = value;
		subNodes = new ArrayList<Node<T>>();
	}
	
	/**
	 * Create a new node without a value
	 */
	public Node(){
		this(null);
	}
	
	/**
	 * Get the value of the node
	 * @return The value of the node
	 */
	public T getValue(){
		return value;
	}
	
	/**
	 * Set the value of the node
	 * @param value Value to set as node value
	 */
	public void setValue(T value){
		this.value = value;
	}
	
	/**
	 * Connect the node with another one (better use the connect function of the graph!)
	 * @param node The node where the connection goes to
	 */
	public void connectTo(Node<T> node){
		if(!subNodes.contains(node))
			subNodes.add(node);
	}
	
	/**
	 * Disconnects a node
	 * @param node The node that should be disconnected
	 */
	public void disconnectFrom(Node<T> node){
		subNodes.remove(node);
	}
	
	/**
	 * Get connected nodes where the connection starts on this node
	 * @return Connected nodes
	 */
	public ArrayList<Node<T>> getConnectedNodes(){
		return subNodes;
	}

	/**
	 * Check if a node is directly connected to another node
	 * @param node The node to check if a connection goes to
	 * @return True if the node is directly connected
	 */
	public boolean isConnectedDirectTo(Node<T> node){
		return subNodes.contains(node);
	}
	
	@Override
	public String toString() {
		int nodeCount = subNodes.size();
		String result = value + " --> [";
		if(nodeCount > 0)
			result += " " + subNodes.get(0).getValue();
		for(int i = 1; i < nodeCount; i++)
			result += ", " + subNodes.get(i).getValue();
		return result + " ]";
	}
}
