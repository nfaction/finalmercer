package graphics;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JPanel;

import entities.BasketBall;
import entities.Entities;
import enums.EType;
import graphics.MainGUI.mainMenuScenarioButtonListener;

import model.Model;
// Start, clear, reset
public class SandboxPanel extends JPanel implements Observer, MouseMotionListener, MouseListener, Runnable {
	private int imageShiftX = 365;
	private int imageShiftY = 0;
	private boolean running = true;
	private Image leftRamp;
	private Image basketball;
	private Image bowlingball;
	private Image balloon;
	private Image toolbox;
	private Image sandbox;
	private Image PingPongBall;
	private ArrayList<Entities> temp = new ArrayList<Entities>();
	private SpriteSheet sprites = new SpriteSheet();
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
	
	Thread run;
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	
	// Adjustments for Images of all the Entities now in Model.

	
	private Model model = new Model(500, 500);
	
	
	public SandboxPanel(){
		
		this.setLayout(null);
		this.setSize(950, 600);
		this.setLocation(0, 50);
		this.setBackground(Color.BLACK);
		start.setSize(125, 30);
		start.setLocation(800, 10);
		stop.setSize(125, 30);
		stop.setLocation(800, 50);
		this.add(start);
		this.add(stop);
		registerListeners();
		try {
			toolbox = ImageIO.read(new File("Images/ToolBox.gif"));
			sandbox = ImageIO.read(new File("Images/SandBox.gif"));
			basketball = ImageIO.read(new File("Images/basketball.gif"));
			bowlingball = ImageIO.read(new File("Images/bowling ball.gif"));
			balloon = ImageIO.read(new File("Images/Balloon.gif"));
			PingPongBall = ImageIO.read(new File("Images/PingPongBall.gif"));
			leftRamp = ImageIO.read(new File("Images/BrickWall.gif"));
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
		start.addActionListener(new startButtonListener());
		stop.addActionListener(new stopButtonListener());
	}
	
	public void startEngine(){
		run = new Thread(this, "GUI Engine");
		run.start();
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D o = (Graphics2D) g;
		o.drawImage(toolbox, 10, 10, this);
		o.drawImage(sandbox, 350, 10, this);
		o.drawImage(basketball, 60, 40, this);
		o.drawImage(bowlingball, 60, 120, this);
		o.drawImage(balloon, 60, 180, this);		// Add here! /////////////////////
		o.drawImage(PingPongBall, 75, 250, this);
		o.drawImage(leftRamp, 15, 270, this);
		temp = model.getObjList();
		// Allows objects to be drag-able
		if(basketballmoved){
			o.drawImage(basketball, newXi - BasketBall.X_LENGHT , newYi - BasketBall.X_LENGHT, this);
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
			o.drawImage(sprites.getStateImage(ent), upperx, uppery, this);
			System.out.println("upper x = " + upperx + "Sprite was drawn");
			System.out.println("upper y = " + uppery);
		}
		}
	}


	public void update(Observable arg0, Object arg1) {
		//repaint();
		paintImmediately(0, 0, 950, 650);
	}


	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


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


	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


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
				System.out.println("BasketBall = " + model.addObjToBoard(EType.basketball,newXi - BasketBall.X_LENGHT, newYi -BasketBall.Y_LENGHT ));
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


	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This action listener listens for a Main Menu Options button click and handles that
	 * action.
	 */
	public class startButtonListener implements ActionListener {
	
		public void actionPerformed(ActionEvent arg0) {
			startEngine();
			
		}
	}
	
	/**
	 * This action listener listens for a Main Menu Options button click and handles that
	 * action.
	 */
	public class stopButtonListener implements ActionListener {
	
		public void actionPerformed(ActionEvent arg0) {
			model.stop();
			//pause();
			running = false;
		}
	}

	public void pause() {
		run.stop();
	}
	

	public void run() {
		float target = 1000 / 60.0f;
		float frameAverage = target;
		long lastFrame = System.currentTimeMillis();
		float yield = 10000f;
		float damping = 0.1f;
		@SuppressWarnings("unused")
		long renderTime = 0;
		@SuppressWarnings("unused")
		long logicTime = 0;

		while (running) {
			// adaptive timing loop from Master Onyx
			long timeNow = System.currentTimeMillis();
			frameAverage = (frameAverage * 10 + (timeNow - lastFrame)) / 11;
			lastFrame = timeNow;

			yield += yield * ((target / frameAverage) - 1) * damping + 0.05f;

			for (int i = 0; i < yield; i++) {
				Thread.yield();
			}
			repaint();
			// update data model
			long beforeLogic = System.currentTimeMillis();
			for (int i = 0; i < 5; i++) {
				model.step();
			}
			logicTime = System.currentTimeMillis() - beforeLogic;
			paintImmediately(0, 0, 950, 650);
		}
	}
}
