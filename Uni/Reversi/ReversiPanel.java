import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ReversiPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel lblMessage, lblPoints;
	Image bgImg;
	float bgWidth, bgHeight;
	ReversiPanel self;
	JPanel reversiFieldHolder;
	ReversiField reversiField;
	ReversiOptionsPanel reversiOptions;

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
		
		reversiOptions = new ReversiOptionsPanel(this);
		
		reversiFieldHolder = null;
		
		lblPoints = new JLabel();
		lblPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPoints.setForeground(Color.WHITE);

		this.setBackground(new Color(0,0,0,0));
		this.setLayout(new BorderLayout());
		this.add(lblMessage, BorderLayout.NORTH);
		this.add(reversiOptions, BorderLayout.CENTER);
		this.add(lblPoints, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == reversiOptions.btnOk){
			if(reversiFieldHolder == null){
				
				
				reversiField = new ReversiField((Integer)reversiOptions.cbSize.getSelectedItem()){
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
							lblMessage.setText(player1Own == player2Own ? "No one has lost!" : player1Own > player2Own ? "Player 1 has on!" : "Player 2 has won!");
						}
						int pointsum = player1Own + player2Own;
						lblPoints.setText(String.format("%d : %d - %.2f%% : %.2f%%" , player1Own, player2Own, (float)player1Own / pointsum * 100, (float)player2Own / pointsum * 100));
						self.repaint();
					}
				};
				reversiFieldHolder = new JPanel();
				reversiFieldHolder.setBackground(new Color(0,0,0,0));
				reversiFieldHolder.setLayout(new AspectLayout());
				reversiFieldHolder.add(reversiField);
				
				lblMessage.setText("Player 1's turn");
				lblPoints.setText("2 : 2 - 50% : 50 %");
			}else
				reversiField.init((Integer)reversiOptions.cbSize.getSelectedItem());
			this.remove(reversiOptions);
			this.add(reversiFieldHolder, BorderLayout.CENTER);
			this.revalidate();
			this.repaint();
		}
	}
	
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
