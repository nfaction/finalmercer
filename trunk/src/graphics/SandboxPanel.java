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
import entities.LeftRamp;
import enums.EType;
import graphics.MainGUI.mainMenuScenarioButtonListener;

import model.Model;

// Start, clear, reset
public class SandboxPanel extends JPanel implements Observer,
		MouseMotionListener, MouseListener, Runnable {
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
	// Moved Gif booleans for Entities
	private boolean balloonmoved;
	private boolean basketballmoved;
	private boolean beltmoved;
	private boolean bowlingballmoved;
	private boolean bucketmoved;
	private boolean candlemoved;
	private boolean conveyorbeltmoved;
	private boolean dominomoved;
	private boolean gearmoved;
	private boolean leftrampmoved;
	private boolean lightmoved;
	private boolean pinmoved;
	private boolean pingpongballmoved;
	private boolean rightrampmoved;

	Thread run;
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");

	private Model model = new Model(500, 500);

	public SandboxPanel() {
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

	/**
	 * This method creates a thread for the physics world to run on.
	 */
	public void startEngine() {
		run = new Thread(this, "GUI Engine");
		run.start();
	}

	/**
	 * This method paints all objects onto this panel.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D o = (Graphics2D) g;
		o.drawImage(toolbox, 10, 10, this);
		o.drawImage(sandbox, 350, 10, this);
		o.drawImage(basketball, 60, 40, this);
		o.drawImage(bowlingball, 60, 120, this);
		o.drawImage(balloon, 60, 180, this); // Add here! /////////////////////
		o.drawImage(PingPongBall, 75, 250, this);
		o.drawImage(leftRamp, 15, 270, this);
		temp = model.getObjList();
		// Allows objects to be drag-able
		if (basketballmoved) {
			o.drawImage(basketball, newXi - BasketBall.X_LENGTH, newYi
					- BasketBall.X_LENGTH, this);
		}
		if (bowlingballmoved) {
			o.drawImage(bowlingball, newXi, newYi, this);
		}
		if (leftrampmoved) {
			o.drawImage(leftRamp, newXi, newYi, this);
		}

		// Painting the objects from the list

		Iterator<Entities> entitiesIter = temp.iterator();
		if (entitiesIter.hasNext()) {
			while (entitiesIter.hasNext()) {
				Entities ent = entitiesIter.next();
				int upperx = (int) ent.getUpperX() + imageShiftX;
				int uppery = (int) ent.getUpperY() + imageShiftY;
				o.drawImage(sprites.getStateImage(ent), upperx, uppery, this);
				System.out.println("upper x = " + upperx + "Sprite was drawn");
				System.out.println("upper y = " + uppery);
			}
		}
	}

	/**
	 * This method handles all the clicks from the toolbox and the sandbox.
	 */
	public void mousePressed(MouseEvent arg0) {
		newX = arg0.getX();
		newY = arg0.getY();
		// Code for each type of object in toolbox
		if ((newX > 60 && newX < 110) && (newY > 40 && newY < 90)) {
			basketballmoved = true;
		} else if ((newX > 60 && newX < 110) && (newY > 120 && newY < 170)) {
			bowlingballmoved = true;
		} else if ((newX > 60 && newX < 110) && (newY > 270 && newY < 300)) {
			leftrampmoved = true;
		}
		// Code for objects being placed into the sandbox
		else if ((newX > 350 && newX < 850) && (newY > 10 && newY < 510)) {
			newXi -= imageShiftX;
			newXi -= imageShiftY;
			if (basketballmoved) {
				if (model.addObjToBoard(EType.basketball, newXi
						- BasketBall.X_LENGTH, newYi - BasketBall.Y_LENGTH)) {
					basketballmoved = false;
					System.out.println("BasketBall = ");
				}
				// send click to model
			} else if (bowlingballmoved) {
				if (model.addObjToBoard(EType.bowlingball, newXi, newYi)) {
					basketballmoved = false;
					System.out.println("BowlingBall = ");
				}

				// System.out.println("BowlingBall = " +
				// model.addObjToBoard(EType.bowlingball,newXi, newYi));
				// send click to model
			} else if (leftrampmoved) {
				if (model.addObjToBoard(EType.leftRamp, newXi, newYi)) {
					leftrampmoved = false;
					System.out.println("Left Ramp = ");
				}
				System.out.println("Left Ramp was not added!!!!!!!");
				// send click to model
			}
		}

	}

	/**
	 * This method gets the position of the mouse and allows for select-ability
	 * of objects, including dragging and dropping.
	 */
	public void mouseMoved(MouseEvent arg0) {
		newX = arg0.getX();
		newY = arg0.getY();
		if (balloonmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (basketballmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (beltmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (bowlingballmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (bucketmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (candlemoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (conveyorbeltmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (dominomoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (gearmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (leftrampmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (pinmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (pingpongballmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
		if (rightrampmoved) {
			newXi = newX;
			newYi = newY;
			repaint();
		}
	}

	/**
	 * This method repaints the whole panel when observers are notified.
	 */
	public void update(Observable arg0, Object arg1) {
		paintImmediately(0, 0, 950, 650);
	}

	/**
	 * This method runs the physics and drawing on the separate thread. This
	 * also includes timers to slow down the physics to look more realistic.
	 */

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

	/**
	 * This action listener listens for a Main Menu Options button click and
	 * handles that action.
	 */
	public class startButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			startEngine();
		}
	}

	/**
	 * This action listener listens for a Main Menu Options button click and
	 * handles that action.
	 */
	public class stopButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			model.stop();
			// pause();
			running = false;
		}
	}

	/**
	 * Not needed.
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * Not needed.
	 */
	public void mouseDragged(MouseEvent arg0) {
	}

	/**
	 * Not needed.
	 */
	public void mouseClicked(MouseEvent arg0) {
	}

	/**
	 * Not needed.
	 */
	public void mouseEntered(MouseEvent arg0) {
	}

	/**
	 * Not needed.
	 */
	public void mouseExited(MouseEvent arg0) {
	}

}
