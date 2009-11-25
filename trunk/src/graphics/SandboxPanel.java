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
	private SpriteSheet cardImages;
	private int newX, newY;
	private int newXi = 0, newYi = 0;
	private boolean bbmoved;
	private boolean bwmoved;
	
	public SandboxPanel(){
		this.setLayout(new BorderLayout());
		this.setSize(950, 550);
		this.setLocation(0, 50);
		this.setBackground(Color.BLUE);
		registerListeners();
		try {
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
		o.drawImage(basketball, newXi, newYi, this);
		o.drawImage(bowlingball, newXi, newYi + 50, this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		paintImmediately(0,0,1000,1000);
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
		else if(bwmoved){
			newXi = newX;
			newYi = newY;
			repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		newX = arg0.getX();
		newY = arg0.getY();
		if((newX > 0 && newX < 50) && (newY > 0 && newY < 50)){
			bbmoved = true;

		}
		else if((newX > 0 && newX < 50) && (newY > 50 && newY < 100)){
			bwmoved = true;
		}
		else{
			bbmoved = false;
			bwmoved = false;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
