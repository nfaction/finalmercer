package graphics;

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
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import model.Model;
import entities.Balloon;
import entities.BasketBall;
import entities.BowlingBall;
import entities.Entities;
import entities.LeftRamp;
import enums.EType;

public class SandboxPanel extends JPanel implements Observer,
		MouseMotionListener, MouseListener, Runnable {
	protected static final int sandboxShiftX = 365;
	protected static final int sandboxShiftY = 13;
	protected static final int toolboxShiftX = 19;
	protected static final int toolboxShiftY = 20;
	private int xsize;
	private int ysize;

	
	private Image toolbox;
	private Image sandbox;

	private boolean running = true;
	private ArrayList<Entities> temp = new ArrayList<Entities>();
	private int newX, newY;
	private int newXi = 0, newYi = 0;
	private Data info;

	Thread run;
	JButton start = new JButton("Start");

	private Model model;

	public SandboxPanel(Model m, int xsize, int ysize) {
		info = Data.getObj();
		this.model = m;
		this.xsize = xsize;
		this.ysize = ysize;
		this.setLayout(null);
		this.setSize(950, 600);
		this.setLocation(0, 0);
		//this.setBackground(Color.BLACK);
		start.setSize(125, 30);
		start.setLocation(800, 10);
		this.add(start);
		registerListeners();
		try {
			// Static Background images
			toolbox = ImageIO.read(new File("Images/ToolBox.gif"));
			sandbox = ImageIO.read(new File("Images/SandBox.gif"));
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
		temp = model.getObjList();

		o.drawImage(toolbox, 10, 10, this);
		o.drawImage(sandbox, 350, 10, this);

		for (int i = 0; i < info.length(); i++) {
			EType temp = info.intToEType(i);
			o.drawImage(info.getImage(temp), info.getImageX(temp), info
					.getImageY(temp), this);
		}

		if (info.anyHasMoved()) {
			EType mover = info.whoIsMoving();
			o.drawImage(info.getImage(mover), newXi - info.getXLENGTH(mover),
					newYi - info.getYLENGTH(mover), this);
		}

		Iterator<Entities> entitiesIter = temp.iterator();
		if (entitiesIter.hasNext()) {
			while (entitiesIter.hasNext()) {
				Entities ent = entitiesIter.next();
				int upperx = (int) ent.getUpperX() + sandboxShiftX;
				int uppery = (int) ent.getUpperY() + sandboxShiftY;
				o.drawImage(ent.getSpriteImage(), upperx, uppery, this);
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
		if (newX < sandboxShiftX) {
			for (int i = 0; i < info.length(); i++) {
				EType type = info.intToEType(i);
				if ((newX > info.getImageX(type) && newX < (info
						.getImageX(type) + info.getXLENGTH(type) * 2))
						&& (newY > info.getImageY(type) && newY < (info
								.getImageY(type) + info.getYLENGTH(type) * 2))) {
					System.out.println(type + " selected");
					info.setMoved(type, true);
				}
			}

			// Code for objects being placed into the sandbox
		} else if ((newX > sandboxShiftX && newX < (sandboxShiftX + xsize))
				&& (newY > sandboxShiftY && newY < (sandboxShiftY + ysize))) {

			newXi -= sandboxShiftX;
			newXi -= sandboxShiftY;
			if (info.anyHasMoved()) {
				EType mover = info.whoIsMoving();
				if (model.addObjToBoard(mover, newXi - info.getXLENGTH(mover),
						newYi - info.getYLENGTH(mover))) {
					info.setMoved(mover, false);
					System.out.println(mover + " = true");
				}
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
		if (info.anyHasMoved()) {
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

	// /**
	// * This action listener listens for a Main Menu Options button click and
	// * handles that action.
	// */
	// public class stopButtonListener implements ActionListener {
	// public void actionPerformed(ActionEvent arg0) {
	// model.stop();
	// // pause();
	// running = false;
	// }
	// }

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