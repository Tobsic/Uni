package de.hellerBrosge.graph.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import de.hellerBrosge.graph.graph.Graph;
import de.hellerBrosge.graph.graph.Node;

enum Tool{
	add, connect, select
}


public class GraphFrame extends JFrame implements ActionListener, GraphListener{
	private static final long serialVersionUID = 1L;
	
	private JPanel panField;
	private Graph<JNode> graph;
	private JPopupMenu context;
	private JMenuItem conAdd,
					  conPrintToConsole,
					  conSave,
					  conSaveAs,
					  conLoad,
					  conClear;
	private JFileChooser fileChooser;

	/**
	 * Initialize a new GUI to visualize a graph
	 */
	public GraphFrame(){
		super();

		conAdd = new JMenuItem("Knoten hinzufügen");
		conAdd.addActionListener(this);
		
		conPrintToConsole = new JMenuItem("Print to Console");
		conPrintToConsole.addActionListener(this);
		
		conSave = new JMenuItem("Speichern");
		conSave.addActionListener(this);
		
		conSaveAs = new JMenuItem("Speichern unter...");
		conSaveAs.addActionListener(this);
		
		conLoad = new JMenuItem("Öffnen");
		conLoad.addActionListener(this);
		
		conClear = new JMenuItem("Säubern");
		conClear.addActionListener(this);
		
		context = new JPopupMenu();
		context.add(conAdd);
		context.add(conPrintToConsole);
		context.add(conSave);
		context.add(conSaveAs);
		context.add(conLoad);
		context.add(conClear);
		
		panField = new JPanel(){
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				g.setPaintMode();
				int size = graph.getSize();
				for(int i = 0; i < size; i++){
					Node<JNode> node = graph.getNode(i);
					Point nodeCenter = node.getValue().getCenter();
					ArrayList<Node<JNode>> subNodes = node.getConnectedNodes();
					for(Node<JNode> subNode : subNodes){
						Point subNodeCenter = subNode.getValue().getCenter();
						g.drawLine(nodeCenter.x, nodeCenter.y, subNodeCenter.x, subNodeCenter.y);
						Point difference = new Point(nodeCenter.x - subNodeCenter.x, nodeCenter.y - subNodeCenter.y);
						double distance = nodeCenter.distance(subNodeCenter);
						double aspectX = difference.x / distance,
							   aspectY = difference.y / distance,
//							   rad = (node.getValue().getWidth() - 3) / 2,
							   subRad = subNode.getValue().getWidth() / 2,
							   arrowX = subNodeCenter.x + (int)(subRad * aspectX),
							   arrowY = subNodeCenter.y + (int)(subRad * aspectY);
//						g.fillOval(nodeCenter.x - (int)(rad * aspectX) - 8, nodeCenter.y - (int)(rad * aspectY) - 8, 16, 16);
						g.drawLine((int)arrowX, (int)arrowY, (int)(arrowX + 10 * aspectX - 5 * aspectY), (int)(arrowY + 10 * aspectY + 5 * aspectX));
						g.drawLine((int)arrowX, (int)arrowY, (int)(arrowX + 10 * aspectX + 5 * aspectY), (int)(arrowY + 10 * aspectY - 5 * aspectX));
						
					}
				}
			}
		};
		panField.setLayout(null);
		panField.setComponentPopupMenu(context);

		graph = new Graph<JNode>();
		loadGraph();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(500,500);
		this.setContentPane(panField);
		this.setVisible(true);
		
		fileChooser = new JFileChooser();
	}
	
	/**
	 * Create a new node, add it to the graph and the GUI
	 * @param center The center of the point on the panel
	 */
	private void addNode(Point center){
		JNode comp = new JNode(this, "new Node");
		comp.setCenter(center);
		graph.add(comp.getNode());
		panField.add(comp);
		panField.repaint();
	}

	/**
	 * Load the default graph file (graph.sav)
	 */
	private void loadGraph(){
		loadGraph("graph.sav");
	}
	
	/**
	 * Load a graph from a file at the given path
	 * @param path Path to the file within a graph
	 */
	private void loadGraph(String path){
		try {
			graph = (Graph<JNode>) Graph.load(path);
			panField.removeAll();
			int size = graph.getSize();
			for(int i = 0; i < size; i++){
				JNode node = graph.getNode(i).getValue();
				panField.add(node);
				node.setListener(this);
			}
			panField.repaint();
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		} catch(Exception e){
//			e.printStackTrace();
		}
	}

	/**
	 * Save the graph to the default graph file (graph.sav)
	 */
	private void saveGraph(){
		saveGraph("graph.sav");
	}

	/**
	 * Save the graph to a file at the given path
	 * @param path Path to the file where the graph should be saved
	 */
	private void saveGraph(String path) {
		try {
			graph.save(path);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object sender = arg0.getSource();
		if(sender == conAdd)
			addNode(panField.getMousePosition());
		else if(sender == conPrintToConsole)
			graph.print(System.out);
		else if(sender == conSave)
			saveGraph();
		else if(sender == conSaveAs) {
			if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
				saveGraph(fileChooser.getSelectedFile().getAbsolutePath());
		} else if(sender == conLoad) {
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				loadGraph(fileChooser.getSelectedFile().getAbsolutePath());
		}
		else if(sender == conClear){
			panField.removeAll();
			graph.clear();
			panField.repaint();
		}
	}

	@Override
	public void NodeConnect(JNode node) {
		Node<JNode> thisNode = node.getNode();
		ArrayList<Node<JNode>> connectable = graph.getConnectable(thisNode);
		ArrayList<JNode> connectableComp = new ArrayList<JNode>();
		for(Node<JNode> n : connectable)
			connectableComp.add(n.getValue());
		JNode comp = (JNode) JOptionPane.showInputDialog(this, "Bitte wäheln Sie den zu verbindenen Knoten an", "Verbinden von " + node, JOptionPane.QUESTION_MESSAGE, null, connectableComp.toArray(), null);
		if(comp != null)
			thisNode.connectTo(comp.getNode());
		panField.repaint();
	}

	@Override
	public void NodeDisconnect(JNode node) {
		ArrayList<Node<JNode>> subNodes = node.getNode().getConnectedNodes();
		ArrayList<JNode> connected = new ArrayList<JNode>();
		for(Node<JNode> k : subNodes)
			connected.add(k.getValue());
		JNode comp = (JNode) JOptionPane.showInputDialog(this, "Bitte wäheln Sie den Knoten aus, von dem Sie die zu Verbindung entfernen möchten", "Verbindung von " + node + " entfernen", JOptionPane.QUESTION_MESSAGE, null, connected.toArray(), null);
		if(comp != null)
			graph.disconnect(node.getNode(), comp.getNode());
		panField.repaint();
	}

	@Override
	public void NodeDelete(JNode node) {
		graph.remove(node.getNode());
		panField.remove(node);
		panField.repaint();
	}
	
	@Override
	public void FindPath(JNode node) {
		ArrayList<JNode> nodes = new ArrayList<JNode>();
		int nodeCount = graph.getSize();
		for(int i = 0; i < nodeCount; i++){
			JNode n = graph.getNode(i).getValue();
			if(n != node)
				nodes.add(n);
		}
		JNode comp = (JNode)JOptionPane.showInputDialog(this, "Bitte wäheln Sie den Knoten aus, zu welchem alle Verbindungen von " + node + " aufgelistet werden sollen.", "Pfad von " + node + " zu einem ausgewähltem Knoten finden", JOptionPane.QUESTION_MESSAGE, null, nodes.toArray(), null);
		if(comp != null){
			ArrayList<ArrayList<Node<JNode>>> pathes = graph.findPath(node.getNode(), comp.getNode());
			if(pathes == null)
				JOptionPane.showMessageDialog(this, "Es wurde kein Pfad von " + node + " zu " + comp + " gefunden.", "Kein Pfad!", JOptionPane.INFORMATION_MESSAGE);
			else{
				String result = "Es wurden folgende Wege gefunden:";
				for(ArrayList<Node<JNode>> path : pathes){
					result += "\n" + path.get(0).getValue();
					int length = path.size();
					for(int i = 1; i < length; i++)
						result += " --> " + path.get(i).getValue();
				}
				JOptionPane.showMessageDialog(this, result, "Pfade von " + node + " zu " + comp, JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	@Override
	public void NodeMoved(JNode node) {
		panField.repaint();
	}
	
	@Override
	public void NodeResized(JNode node) {
		panField.repaint();
	}
	
	@Override
	public void dispose() {
		saveGraph();
		super.dispose();
	}
}
