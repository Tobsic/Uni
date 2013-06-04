package de.hellerBrosge.graph.graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.hellerBrosge.graph.gui.GraphException;

/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 *
 */
public class Graph<T extends Serializable> implements Serializable{
	private static final long serialVersionUID = 1L;
	List<Node<T>> nodes;
	
	/**
	 * Create a empty graph
	 */
	public Graph(){
		nodes = new ArrayList<Node<T>>();
	}

	/**
	 * Add a node to the graph
	 * @param node Node to add to the graph
	 */
	public void add(Node<T> node){
		this.nodes.add(node);
	}
	
	/**
	 * Connect to two nodes with a one way connection
	 * @param node1	The node where the connection goes from
	 * @param node2 The node where the connection goes to
	 * @throws GraphException "Rings are not allowed"
	 */
	public void connect(Node<T> node1, Node<T> node2) throws GraphException{
		if(deepSearch(node2, node1))
			throw new GraphException("Connect - Rings are not allowed");
		node1.connectTo(node2);
	}
	
	/**
	 * Remove a node and all connections from and to this node
	 * @param node Node to remove
	 */
	public void remove(Node<T> node){
		for(Node<T> k : nodes)
			k.disconnectFrom(node);
		nodes.remove(node);
	}
	
	/**
	 * Delete the connection of node1 to node2
	 * @param node1 The node where the connection goes from
	 * @param node2 The node where the connection goes to
	 */
	public void disconnect(Node<T> node1, Node<T> node2){
		node1.disconnectFrom(node2);
	}
	
	/**
	 * Delete the connection of two nodes in each direction
	 * @param node1 One node to disconnect 
	 * @param node2 Second node to disconnect
	 */
	public void disconnectionTwoWay(Node<T> node1, Node<T> node2){
		node1.disconnectFrom(node2);
		node2.disconnectFrom(node1);
	}
	
	/**
	 * Search for a one way connection between to nodes using deep search
	 * @param in The node, that is the start of connection to the other node
	 * @param target The node, that is searched in the first node
	 * @return Returns true, if a connection is present
	 */
	public boolean deepSearch(Node<T> in, Node<T> target){
		if(in == target)
			return true;
		ArrayList<Node<T>> subNodes = in.getConnectedNodes();
		for(Node<T> k : subNodes)
			if(deepSearch(k, target))
				return true;
		return false;
	}

	/**
	 * Search for a one way connection between to nodes using wide search
	 * @param in The node, that is the start of connection to the other node
	 * @param target The node, that is searched in the first node
	 * @return Returns true, if a connection is present
	 */
	public boolean wideSearch(Node<T> in, Node<T> target){
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		queue.add(in);
		while(!queue.isEmpty()){
			Node<T> node = queue.poll();
			if(node == target)
				return true;
			ArrayList<Node<T>> subNodes = node.getConnectedNodes();
			for(Node<T> k : subNodes){
				queue.add(k);
			}
		}
		return false;
	}
	
	/**
	 * Saves the graph to a binary file
	 * @param path Path to the file, where the graph should be stored
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save(String path) throws FileNotFoundException, IOException{
		   ObjectOutputStream o = null;
		   try{
			   o = new ObjectOutputStream(new FileOutputStream(path));
			   o.writeObject(this);
			   o.close();
		   } catch(IOException e){
			   if(o != null)
				   o.close();
			   throw e;
		   }
	}
	
	/**
	 * Loads a full Graph, that is stored in a binary file
	 * @param path Path to the binary file
	 * @return The stored graph as non specific graph
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Graph<?> load(String path) throws ClassNotFoundException, IOException, FileNotFoundException{
		ObjectInputStream in = null;
		try{
			in = new ObjectInputStream(new FileInputStream(path)); 
			Graph<?> result = (Graph<?>)in.readObject();
			in.close();
			return result;
		}catch(IOException e){
			if(in != null)
				in.close();
			throw e;
		}
	}
	
	/**
	 * Print the adjacency of the graph to the given stream
	 * @param stream The stream where the graph adjacency should printed
	 */
	public void print(PrintStream stream){
		for(Node<T> node : nodes)
			stream.println(node);
	}
	
	/**
	 * Returns the number of nodes in the graph
	 * @return Numbers of nodes in the graph
	 */
	public int getSize(){
		return nodes.size();
	}
	
	/**
	 * Returns a node with a specific index
	 * @param index The index of the node, that should be returned
	 * @return The node with the given index
	 */
	public Node<T> getNode(int index){
		return nodes.get(index);
	}
	
	/**
	 * Removes all nodes of the graph
	 */
	public void clear(){
		nodes.clear();
	}
	
	/**
	 * Get all nodes, which can connected by the given node
	 * @param from The node, where the connection goes from
	 * @return all nodes, that can connected by the given node
	 */
	public ArrayList<Node<T>> getConnectable(Node<T> from){
		ArrayList<Node<T>> result = new ArrayList<Node<T>>();
		for(Node<T> node : nodes)
			if(!from.isConnectedDirectTo(node) && !deepSearch(node, from))
				result.add(node);
		return result;
	}
}
