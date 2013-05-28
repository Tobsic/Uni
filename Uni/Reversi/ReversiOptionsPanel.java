import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ReversiOptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JPanel panSize, panPlayers;
	JLabel lblSize, lblPlayers;
	JComboBox<Integer> cbSize;
	JComboBox<String> cbPlayers;
	JButton btnOk;
	
	public ReversiOptionsPanel(ActionListener listener){
		super();
		
		lblSize = new JLabel("Spielfeldgröße: ", SwingConstants.RIGHT);
		lblSize.setBackground(new Color(0,0,0,0));
		lblSize.setForeground(Color.WHITE);
		
		cbSize = new JComboBox<Integer>(new Integer[]{4, 6, 8, 10});
		cbSize.setBackground(Color.BLACK);
		cbSize.setForeground(Color.WHITE);
		
		panSize = new JPanel();
		panSize.setBackground(new Color(0,0,0,0));
		panSize.setLayout(new FlowLayout());
		panSize.setSize(400, 50);
		panSize.add(lblSize);
		panSize.add(cbSize);
		
		lblPlayers = new JLabel("Spieler: ", SwingConstants.RIGHT);
		lblPlayers.setBackground(new Color(0,0,0,0));
		lblPlayers.setForeground(Color.WHITE);
		
		cbPlayers = new JComboBox<String>(new String[]{"Player vs. Player", "Player vs. Bot"});
		cbPlayers.setSize(20, 200);
		cbPlayers.setBackground(Color.BLACK);
		cbPlayers.setForeground(Color.WHITE);
		cbPlayers.setPreferredSize(new Dimension(200, 20));
		
		panPlayers = new JPanel();
		panPlayers.setBackground(new Color(0,0,0,0));
		panPlayers.setLayout(new FlowLayout());
		panPlayers.setSize(400, 50);
		panPlayers.add(lblPlayers);
		panPlayers.add(cbPlayers);
		
		btnOk = new JButton("Start Game");
		btnOk.setBackground(Color.BLACK);
		btnOk.setForeground(Color.WHITE);
		btnOk.addActionListener(listener);
		
		this.setBackground(new Color(0,0,0,0));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(panSize);
		this.add(panPlayers);
		this.add(btnOk);
		
		this.revalidate();
		this.repaint();
	}
}
