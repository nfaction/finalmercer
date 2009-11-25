package graphics;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SandboxPanel extends JPanel implements Observer, MouseMotionListener, MouseListener {
	private Image basketball;
	private Image bowlingball;
	private Image toolbox;
	private Image sandbox;
	
	private SpriteSheet cardImages;
	private int newX, newY;
	private int newXi = 0, newYi = 0;
	private boolean bbmoved;
	private boolean bwmoved;
	
	public SandboxPanel(){
		this.setLayout(null);
		this.setSize(950, 600);
		this.setLocation(0, 50);
		this.setBackground(Color.BLACK);
		registerListeners();
		try {
			toolbox = ImageIO.read(new File("Images/ToolBox.gif"));
			sandbox = ImageIO.read(new File("Images/SandBox.gif"));
			basketball = ImageIO.read(new File("Images/basketball.gif"));
			bowlingball = ImageIO.read(new File("Images/bowling ball.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method registers all the listeners.
	 */
	private void registerListeners() {
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D o = (Graphics2D) g;
		o.drawImage(toolbox, 10, 10, this);
		o.drawImage(sandbox, 350, 10, this);
		o.drawImage(basketball, 60, 40, this);
		o.drawImage(bowlingball, 60, 120, this);
		if(bbmoved){
			o.drawImage(basketball, newXi, newYi, this);
		}
		if(bwmoved){
			o.drawImage(bowlingball, newXi, newYi, this);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		newX = arg0.getX();
		newY = arg0.getY();
		
		if(bbmoved){
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if(bwmoved){
			newXi = newX;
			newYi = newY;
			repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		newX = arg0.getX();
		newY = arg0.getY();
		if((newX > 60 && newX < 110) && (newY > 40 && newY < 90)){
			bbmoved = true;

		}
		else if((newX > 60 && newX < 110) && (newY > 120 && newY < 170)){
			bwmoved = true;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("Clicked");
		if(bbmoved){
			bbmoved = false;
			//send click to model
		}
		else if(bwmoved){
			bbmoved = false;
			//send click to model
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
