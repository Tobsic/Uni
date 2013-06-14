import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * A panel with the options at first and the field and states after starting
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 * 
 */
public class ReversiPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel lblMessage, lblPoints;
	Image bgImg;
	float bgWidth, bgHeight;
	ReversiPanel self;
	JPanel reversiFieldHolder, panNorth;
	ReversiField reversiField;
	ReversiOptionsPanel reversiOptions;
	JButton btnOptions, btnRevenge;

	/**
	 * Initialize a new ReversiPanel and all its components
	 */
	ReversiPanel(){
		super();
		self = this;
		bgImg = new ImageIcon(ReversiWindow.class.getResource("Brett.jpg")).getImage();
		bgWidth = bgImg.getWidth(null);
		bgHeight = bgImg.getHeight(null);
		
		lblMessage = new JLabel("Options");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(lblMessage.getFont().deriveFont(25f));
		lblMessage.setForeground(Color.WHITE);
		
		btnOptions = new JButton("Options");
		btnOptions.setBackground(Color.BLACK);
		btnOptions.setForeground(Color.WHITE);
		btnOptions.addActionListener(this);
		btnOptions.setVisible(false);
		
		btnRevenge = new JButton("Revenge");
		btnRevenge.setBackground(Color.BLACK);
		btnRevenge.setForeground(Color.WHITE);
		btnRevenge.addActionListener(this);
		btnRevenge.setVisible(false);
		
		panNorth = new JPanel();
		panNorth.setBackground(new Color(0,0,0,0));
		panNorth.setLayout(new FlowLayout());
		panNorth.add(lblMessage);
		panNorth.add(btnOptions);
		panNorth.add(btnRevenge);
		
		reversiOptions = new ReversiOptionsPanel(this);
		
		reversiField = new ReversiField(){
			private static final long serialVersionUID = 1L;
			@Override
			void endMove(int player, int player1Own, int player2Own) {
				switch(player){
				case 1:
					lblMessage.setText("Player 1's turn");
					break;
				case 2:
					lblMessage.setText("Player 2's turn");
					break;
				case 0:
					lblMessage.setText(player1Own == player2Own ? "No one has lost!" : player1Own > player2Own ? "Player 1 has won!" : "Player 2 has won!");
					self.btnOptions.setVisible(true);
					self.btnRevenge.setVisible(true);
				}
				int pointsum = player1Own + player2Own;
				lblPoints.setText(String.format("%d : %d - %.2f%% : %.2f%%" , player1Own, player2Own, (float)player1Own / pointsum * 100, (float)player2Own / pointsum * 100));
				self.revalidate();
				self.repaint();
			}
		};
		reversiFieldHolder = new JPanel();
		reversiFieldHolder.setBackground(new Color(0,0,0,0));
		reversiFieldHolder.setLayout(new AspectLayout());
		reversiFieldHolder.add(reversiField);
		
		lblPoints = new JLabel();
		lblPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPoints.setForeground(Color.WHITE);

		this.setBackground(new Color(0,0,0,0));
		this.setLayout(new BorderLayout());
		this.add(panNorth, BorderLayout.NORTH);
		this.add(reversiOptions, BorderLayout.CENTER);
		this.add(lblPoints, BorderLayout.SOUTH);
	}

	/**
	 * Start game, show options or rematch on clicking an element
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object sender = arg0.getSource();
		if(sender == reversiOptions.btnOk){
			reversiField.init((Integer)reversiOptions.cbSize.getSelectedItem(), reversiOptions.cbPlayers.getSelectedIndex() == 1);
			lblMessage.setText("Player 1's turn");
			lblPoints.setText("2 : 2 - 50% : 50 %");
			this.remove(reversiOptions);
			this.add(reversiFieldHolder, BorderLayout.CENTER);
			this.revalidate();
			this.repaint();
		}else if(sender == btnOptions){
			lblMessage.setText("Options");
			btnOptions.setVisible(false);
			btnRevenge.setVisible(false);
			this.remove(reversiFieldHolder);
			this.add(reversiOptions, BorderLayout.CENTER);
			this.revalidate();
			this.repaint();
		}else if(sender == btnRevenge){
			lblMessage.setText("Player 1's turn");
			btnOptions.setVisible(false);
			btnRevenge.setVisible(false);
			reversiField.init((Integer)reversiOptions.cbSize.getSelectedItem(), reversiOptions.cbPlayers.getSelectedIndex() == 1);
			this.revalidate();
			this.repaint();
		}
	}
	
	/**
	 * draw the background picture
	 */
	@Override
	public void paint(Graphics g) {
		float aspect = Math.max((float)this.getWidth() / bgWidth, (float)this.getHeight() / bgHeight);
		int imgWidth  = (int)(bgWidth * aspect),
			imgHeight = (int)(bgHeight * aspect),
			imgX = (this.getWidth() - imgWidth) / 2,
			imgY = (this.getHeight() - imgHeight) / 2;
		g.drawImage(bgImg, imgX, imgY, imgWidth, imgHeight, this);
		super.paint(g);
	}
}
