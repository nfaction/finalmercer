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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import entities.Balloon;
import entities.PingPongBall;
import entities.BasketBall;
import entities.BowlingBall;
import entities.Candle;
import entities.Entities;
import entities.LeftRamp;
import enums.EType;

import model.Model;

public class SandboxPanel extends JPanel implements Observer,
		MouseMotionListener, MouseListener, Runnable {
	private int sandboxShiftX = 365;
	private int sandboxShiftY = 13;
	private int toolboxShiftX = 19;
	private int toolboxShiftY = 20;
	private int xsize;
	private int ysize;

	/** Variables for image spacing */
	private int balloonimageX = 20 + toolboxShiftX;
	private int balloonimageY = 20 + toolboxShiftY;
	private int basketballimageX = 75 + toolboxShiftX;
	private int basketballimageY = 20 + toolboxShiftY;
	private int bowlingballimageX = 140 + toolboxShiftX;
	private int bowlingballimageY = 30 + toolboxShiftY;
	private int pingpongballimageX = 185 + toolboxShiftX;
	private int pingpongballimageY = 40 + toolboxShiftY;
	private int leftrampimageX = 20 + toolboxShiftX;
	private int leftrampimageY = 270 + toolboxShiftY;

	private Image toolbox;
	private Image sandbox;
	// private Image balloon;
	// private Image rightRamp;
	// private Image straightRamp;
	// private Image belt;
	// private Image bowlingball;
	// private Image bucket;
	// private Image candle;
	// private Image conveyorbelt;
	// private Image gear;
	// private Image leftRamp;
	// private Image light;
	// private Image PingPongBall;
	// private Image rightramp;
	// Moved Gif booleans for Entities
	// private boolean balloonmoved;
	// private boolean beltmoved;
	// private boolean bowlingballmoved;
	// private boolean bucketmoved;
	// private boolean candlemoved;
	// private boolean conveyorbeltmoved;
	// private boolean dominomoved;
	// private boolean gearmoved;
	// private boolean leftrampmoved;
	// private boolean lightmoved;
	// private boolean pinmoved;
	// private boolean pingpongballmoved;
	// private boolean rightrampmoved;
	private boolean running = true;
	private ArrayList<Entities> temp = new ArrayList<Entities>();
	private int newX, newY;
	private int newXi = 0, newYi = 0;
	private Data info;

	Thread run;
	JButton start = new JButton("Start");

	private Model model;

	public SandboxPanel(Model m, int xsize, int ysize) {
		info = new Data(toolboxShiftX, toolboxShiftY);
		this.model = m;
		this.xsize = xsize;
		this.ysize = ysize;
		this.setLayout(null);
		this.setSize(950, 600);
		this.setLocation(0, 0);
		this.setBackground(Color.BLACK);
		start.setSize(125, 30);
		start.setLocation(800, 10);
		this.add(start);
		registerListeners();
		try {
			// Static Background images
			toolbox = ImageIO.read(new File("Images/ToolBox.gif"));
			sandbox = ImageIO.read(new File("Images/SandBox.gif"));
			// Entities
			// balloon = ImageIO.read(new File("Images/Balloon.gif"));
			// basketball = ImageIO.read(new File("Images/basketball.gif"));
			// belt = ImageIO.read(new File("Images/basketball.gif"));
			// bowlingball = ImageIO.read(new File("Images/bowling ball.gif"));
			// bucket = ImageIO.read(new File("Images/basketball.gif"));
			// candle = ImageIO.read(new File("Images/basketball.gif"));
			// conveyorbelt = ImageIO.read(new File("Images/basketball.gif"));
			// gear = ImageIO.read(new File("Images/basketball.gif"));
			// leftRamp = ImageIO.read(new File("Images/LeftRamp.gif"));
			// light = ImageIO.read(new File("Images/BrickWall.gif"));
			// ngPongBall = ImageIO.read(new File("Images/PingPongBall.gif"));

			// rightRamp = ImageIO.read(new File("Images/BrickWall.gif"));
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
		EType type = EType.basketball;
		/** Code for each type of object in toolbox */
		if ((newX > info.getImageX(EType.basketball) && newX < (info
				.getImageX(EType.basketball) + info.getXLENGTH(type) * 2))
				&& (newY > info.getImageY(EType.basketball) && newY < (info
						.getImageY(EType.basketball) + info.getYLENGTH(type) * 2))) {
			System.out.println(type + " selected");
			info.setMoved(EType.basketball, true);
		} else if ((newX > balloonimageX && newX < (balloonimageX + Balloon.X_LENGTH * 2))
				&& (newY > balloonimageY && newY < (balloonimageY + Balloon.Y_LENGTH * 2))) {
			System.out.println("balloon selected");
			info.setMoved(EType.balloon, true);
		} else if ((newX > bowlingballimageX && newX < (bowlingballimageX + BowlingBall.X_LENGTH * 2))
				&& (newY > bowlingballimageY && newY < (bowlingballimageY + BowlingBall.Y_LENGTH * 2))) {
			System.out.println("bowling selected");
			info.setMoved(EType.bowlingball, true);
		}
		// else if ((newX > pingpongballimageX && newX basketballimageY<
		// pingpongballimageX +
		// PingPongBall.X_LENGTH*2) && (newY > pingpongballimageY && newY <
		// pingpongballimageY + PingPongBall.Y_LENGTH*2)) {
		// pingpongballmoved = true;
		// }
		else if ((newX > leftrampimageX && newX < leftrampimageX
				+ LeftRamp.X_LENGTH * 2)
				&& (newY > leftrampimageY && newY < leftrampimageY
						+ LeftRamp.Y_LENGTH * 2)) {
			System.out.println("lramp selected");
			info.setMoved(EType.leftRamp, true);
		}

		/** Code for objects being placed into the sandbox */
		else if ((newX > sandboxShiftX && newX < (sandboxShiftX + xsize))
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

			// if (balloonmoved) {
			// if (model.addObjToBoard(EType.balloon,
			// newXi - Balloon.X_LENGTH, newYi - Balloon.Y_LENGTH)) {
			// balloonmoved = false;
			// System.out.println("Balloon = true");
			// }
			// } else if (info.getMoved(EType.basketball)) {
			// if (model.addObjToBoard(EType.basketball, newXi
			// - BasketBall.X_LENGTH, newYi - BasketBall.Y_LENGTH)) {
			// info.setMoved(1, false);
			// System.out.println("BasketBall = true");
			// }
			// }
			// else if (beltmoved) {
			// if (model.addObjToBoard(EType.belt, newXi
			// - Belt.X_LENGTH, newYi - Belt.Y_LENGTH)) {
			// beltmoved = false;
			// System.out.println("Belt = true");
			// }
			// }
			// else if (bowlingballmoved) {
			// if (model.addObjToBoard(EType.bowlingball, newXi
			// - BowlingBall.X_LENGTH, newYi - BowlingBall.Y_LENGTH)) {
			// bowlingballmoved = false;
			// System.out.println("Bowlingball = true");
			// }
			// }
			// else if (bucketmoved) {
			// if (model.addObjToBoard(EType.bucket, newXi
			// - Bucket.X_LENGTH, newYi - Bucket.Y_LENGTH)) {
			// bucketmoved = false;
			// System.out.println("Bucket = true");
			// }
			// }
			// else if (conveyorbeltmoved) {
			// if (model.addObjToBoard(EType.conveyorbelt, newXi
			// - ConveyorBelt.X_LENGTH, newYi - ConveyorBelt.Y_LENGTH)) {
			// beltmoved = false;
			// System.out.println("Conveyor Belt = true");
			// }
			// }
			// else if (gearmoved) {
			// if (model.addObjToBoard(EType.gear, newXi
			// - Gear.X_LENGTH, newYi - Gear.Y_LENGTH)) {
			// gearmoved = false;
			// System.out.println("Gear = true");
			// }
			// }
			// else if (leftrampmoved) {
			//
			// // if (model.addObjToBoard(EType.leftRamp, newXi -
			// // LeftRamp.X_LENGTH, newYi - LeftRamp.Y_LENGTH)) {
			// if (model.addObjToBoard(EType.leftRamp, newXi
			// - LeftRamp.X_LENGTH, newYi - LeftRamp.Y_LENGTH)) {
			// leftrampmoved = false;
			// System.out.println("Left Ramp = true");
			// }
			// }
			// else if (lightmoved) {
			// if (model.addObjToBoard(EType.light, newXi
			// - Light.X_LENGTH, newYi - Light.Y_LENGTH)) {
			// lightmoved = false;
			// System.out.println("Light = true");
			// }
			// }
			// else if (pingpongballmoved) {
			// if (model.addObjToBoard(EType.pingpongball, newXi
			// - PingPongBall.X_LENGTH, newYi - PingPongBall.Y_LENGTH)) {
			// beltmoved = false;
			// System.out.println("Belt = true");
			// }
			// }
			// else if (rightrampmoved) {
			// if (model.addObjToBoard(EType.rightRamp, newXi
			// - RightRamp.X_LENGTH, newYi - RightRamp.Y_LENGTH)) {
			// rightrampmoved = false;
			// System.out.println("Right Ramp = true");
			// }
			// }
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
