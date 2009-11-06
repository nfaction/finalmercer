package entities;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test_SandBox extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		JFrame window = new Test_SandBox();
		window.setVisible(true);
	}
	
	private java.awt.Container cp = getContentPane();
	private JPanel sBox, gCollection;
	
	public Test_SandBox() {
		
		// Create JFrame
		this.setSize(700, 460);
		this.setLocation(100, 100);
		this.setTitle("Sand Box (Testing Grounds)");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		// Create JPanel for sand box
		sBox = new JPanel();
		sBox.setSize(525, 459);
		sBox.setLocation(159, 0);
		sBox.setBackground(Color.BLUE);
		cp.add(sBox);
		
		// Create JPanel for goodies box
		gCollection = new JPanel();
		gCollection.setSize(155, 459);
		gCollection.setLocation(0, 0);
		gCollection.setBackground(Color.RED);
		cp.add(gCollection);
	}
}
