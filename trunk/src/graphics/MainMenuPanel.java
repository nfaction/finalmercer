package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel{

	static Image rubeG;
	JLabel intro = new JLabel("To start, go to File, then Start!");
	JButton startB = new JButton("Start");
	JButton scenarioB = new JButton("Scenarios");
	JButton optionB = new JButton("Options");
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public MainMenuPanel(){
		try {
			rubeG = ImageIO.read(new File("Images/Rubenvent.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setLayout(null);
		this.setSize(this.getWidth(), this.getHeight());
		this.setLocation(0, 0);
		this.setBackground(Color.BLACK);
		
		this.setLayout(null);
		this.setSize(950, 500);
		this.setLocation(0,0);
		
		intro.setSize(250, 20);
		intro.setLocation(365, 100);
//		
//		startB.setSize(150, 40);
//		startB.setLocation(400, 200);
//		
//		scenarioB.setSize(150, 40);
//		scenarioB.setLocation(400, 250);
//		
//		optionB.setSize(150, 40);
//		optionB.setLocation(400, 300);
//		
		this.add(intro);
//		this.add(startB);
//		this.add(scenarioB);
//		this.add(optionB);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D o = (Graphics2D) g;
		o.drawImage(rubeG, 158, 123,  this);
	}
}
