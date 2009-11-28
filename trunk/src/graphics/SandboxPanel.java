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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.BasketBall;
import entities.Entities;
import enums.EType;

import model.Model;

public class SandboxPanel extends JPanel implements Observer, MouseMotionListener, MouseListener {
	private int imageShiftX = 365;
	private int imageShiftY = 0;
	
	private Image basketball;
	private Image bowlingball;
	private Image toolbox;
	private Image sandbox;
	private ArrayList<Entities> temp = new ArrayList<Entities>();
	private SpriteSheet cardImages;
	private int newX, newY;
	private int newXi = 0, newYi = 0;
	private boolean basketballmoved;
	private boolean bowlingballmoved;
	private boolean bucketmoved;
	private boolean candlemoved;
	private boolean dominomoved;
	private boolean pingpongballmoved;
	private boolean rightrampmoved;
	private boolean leftrampmoved;
	private boolean lightmoved;
	
	private Model model = new Model(500, 500);
	
	
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
		temp = model.getObjList();
		// Allows objects to be drag-able
		if(basketballmoved){
			int bx = new BasketBall().getImageX();
			int by = new BasketBall().getImageY();
			o.drawImage(basketball, newXi - bx, newYi - by, this);
		}
		if(bowlingballmoved){
			o.drawImage(bowlingball, newXi, newYi, this);
		}
		// Painting the objects from the list
		Iterator<Entities> entitiesIter = temp.iterator();
		if(entitiesIter.hasNext()){
			System.out.println("Got here");
		while(entitiesIter.hasNext()){
			Entities ent = entitiesIter.next();
			int upperx = (int) ent.getUpperX() + imageShiftX;
			int uppery = (int) ent.getUpperY() + imageShiftY;
			o.drawImage(basketball, upperx, uppery, this);
			System.out.println("upper x = " + upperx);
			System.out.println("upper y = " + uppery);
		}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		//repaint();
		paintImmediately(0, 0, 950, 650);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		newX = arg0.getX();
		newY = arg0.getY();
		
		if(basketballmoved){
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if(bowlingballmoved){
			newXi = newX;
			newYi = newY;
			repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
//		newX = arg0.getX();
//		newY = arg0.getY();
//		System.out.println("x = " + newXi);
//		System.out.println("y = " + newYi);
//		// Code for each type of object in toolbox
//		if((newX > 60 && newX < 110) && (newY > 40 && newY < 90)){
//			basketballmoved = true;
//
//		}
//		else if((newX > 60 && newX < 110) && (newY > 120 && newY < 170)){
//			bowlingballmoved = true;
//		}
//		// Code for objects being placed into the sandbox
//		else if((newX > 350 && newX < 850) && (newY > 10 && newY < 510)){
//			newXi -= 352;
//			newXi -= 14;
//			System.out.println("after adjustment x = " + newXi);
//			System.out.println("after adjustment y = " + newYi);
//			if(basketballmoved){
//				basketballmoved = false;
//				System.out.println("BasketBall = " + model.addObjToBoard(EType.basketball,newXi, newYi));
//				//send click to model
//			}
//			else if(bowlingballmoved){
//				basketballmoved = false;
//				System.out.println(newXi);
//				System.out.println(newYi);
//				System.out.println("BowlingBall = " + model.addObjToBoard(EType.bowlingball,newXi, newYi));
//				//send click to model
//			}
//		}
//		
//		
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
		newX = arg0.getX();
		newY = arg0.getY();
		System.out.println("x = " + newXi);
		System.out.println("y = " + newYi);
		// Code for each type of object in toolbox
		if((newX > 60 && newX < 110) && (newY > 40 && newY < 90)){
			basketballmoved = true;

		}
		else if((newX > 60 && newX < 110) && (newY > 120 && newY < 170)){
			bowlingballmoved = true;
		}
		// Code for objects being placed into the sandbox
		else if((newX > 350 && newX < 850) && (newY > 10 && newY < 510)){
			newXi -= imageShiftX;
			newXi -= imageShiftY;
			System.out.println("after adjustment x = " + newXi);
			System.out.println("after adjustment y = " + newYi);
			if(basketballmoved){
				basketballmoved = false;
				System.out.println("BasketBall = " + model.addObjToBoard(EType.basketball,newXi, newYi));
				//send click to model
			}
			else if(bowlingballmoved){
				basketballmoved = false;
				System.out.println(newXi);
				System.out.println(newYi);
				System.out.println("BowlingBall = " + model.addObjToBoard(EType.bowlingball,newXi, newYi));
				//send click to model
			}
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
