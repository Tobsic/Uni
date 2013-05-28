import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ReversiPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JLabel lblMessage, lblPoints;
	Image bgImg;
	float bgWidth, bgHeight;
	ReversiPanel self;

	ReversiPanel(){
		super();
		self = this;
		this.setBackground(new Color(0,0,0,0));
		bgImg = new ImageIcon(ReversiWindow.class.getResource("Brett.jpg")).getImage();
		bgWidth = bgImg.getWidth(null);
		bgHeight = bgImg.getHeight(null);
		this.setLayout(new BorderLayout());
		lblMessage = new JLabel("Player 1th turn.");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(lblMessage.getFont().deriveFont(25f));
		lblMessage.setForeground(Color.WHITE);
		this.add(lblMessage, BorderLayout.NORTH);
		JPanel AspectComp = new JPanel();
		AspectComp.setBackground(new Color(0,0,0,0));
		AspectComp.setLayout(new AspectLayout());
		AspectComp.add(new ReversiField(8){
			private static final long serialVersionUID = 1L;

			@Override
			void endMove(int player, int player1Own, int player2Own) {
				switch(player){
				case 1:
					lblMessage.setText("Player 1 have to set.");
					break;
				case 2:
					lblMessage.setText("Player 2 have to set.");
					break;
				case 0:
					lblMessage.setText(player1Own == player2Own ? "No one has lost!" : player1Own > player2Own ? "Player 1 has on!" : "Player 2 has won!");
				}
				int pointsum = player1Own + player2Own;
				lblPoints.setText(String.format("%d : %d - %.2f%% : %.2f%%" , player1Own, player2Own, (float)player1Own / pointsum * 100, (float)player2Own / pointsum * 100));
				self.repaint();//.setSize(this.getWidth(), this.getHeight());
			}
		});
		this.add(AspectComp, BorderLayout.CENTER);
		lblPoints = new JLabel("2 : 2 - 50% : 50 %");
		lblPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPoints.setForeground(Color.WHITE);
		this.add(lblPoints, BorderLayout.SOUTH);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		float aspect = Math.max((float)this.getWidth() / bgWidth, (float)this.getHeight() / bgHeight);
		int imgWidth  = (int)(bgWidth * aspect),
			imgHeight = (int)(bgHeight * aspect),
			imgX = (this.getWidth() - imgWidth) / 2,
			imgY = (this.getHeight() - imgHeight) / 2;
		g.drawImage(bgImg, imgX, imgY, imgWidth, imgHeight, this);
	}
}
