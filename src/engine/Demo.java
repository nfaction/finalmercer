package engine;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.Line;
import engine.shapes.StaticBody;
import engine.strategies.QuadSpaceStrategy;
import engine.vector.Vector;
import entities.BasketBall;
import entities.BowlingBall;
import entities.PingPongBall;

/**
 * Lines terrain testing extensive
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo {
	protected Frame frame;
	protected String title;
	protected World world = new World(new Vector(0.0f, 10.0f), 10,
			new QuadSpaceStrategy(20, 5));
	private boolean running = true;
	private BufferStrategy strategy;
	protected boolean needsReset;
	private Body box;

	/**
	 * Makes the program run
	 * 
	 * @param argv
	 *            Nothing
	 */
	public static void main(String[] argv) {
		Demo demo = new Demo();
		demo.start();
	}

	/**
	 * Create the demo
	 */
	public Demo() {
		this.title = "Demo";
	}

	/**
	 * Start the simulation running
	 */
	public void start() {
		miniGUI();
		world.clear();
		world.setGravity(0, 10);
		init(world);

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

			// render
			long beforeRender = System.currentTimeMillis();
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, 500, 500);

			draw(g);
			renderGUI(g);
			g.setColor(Color.black);
			g.dispose();
			strategy.show();
			renderTime = System.currentTimeMillis() - beforeRender;

			// update data model
			long beforeLogic = System.currentTimeMillis();
			for (int i = 0; i < 5; i++) {
				world.step();
			}
			logicTime = System.currentTimeMillis() - beforeLogic;

			if (needsReset) {
				world.clear();
				initDemo();
				needsReset = false;
				frameAverage = target;
				yield = 10000f;
			}

			update();
		}
	}

	protected void init(World world) {
		Body land = new StaticBody("Line1", new Line(100, 50));
		land.setPosition(150, 150);
		land.setRestitution(1f);
		world.add(land);
		land = new StaticBody("Line2", new Line(150, -75));
		land.setPosition(250, 300);
		land.setRestitution(1f);
		world.add(land);
		land = new StaticBody("Line3", new Line(150, 75));
		land.setPosition(100, 350);
		land.setRestitution(1f);
		world.add(land);
		land = new StaticBody("Line4", new Line(150, 0));
		land.setPosition(300, 450);
		land.setRestitution(1f);
		world.add(land);
		land = new StaticBody("Floor", new Line(494, 0));
		land.setPosition(5, 475);
		land.setRestitution(1f);
		world.add(land);
		land = new StaticBody("LWall", new Line(0, -475));
		land.setPosition(5, 475);
		land.setRestitution(1f);
		world.add(land);
		land = new StaticBody("RWall", new Line(0, -475));
		land.setPosition(495, 475);
		land.setRestitution(1f);
		world.add(land);

		BasketBall newEntity = new BasketBall("BasketBall");
		newEntity.addObj(world, 200, 50);

		BowlingBall newEntity1 = new BowlingBall();
		newEntity1.addObj(world, 200, 55);

		PingPongBall newPPB = new PingPongBall(" ");
		newPPB.addObj(world, 250, 57);

		// box = new Body("Faller", new Box(50, 50), 1);
		// box.setPosition(200, 50);
		// world.add(box);
	}

	/**
	 * Initialize the demo - clear the world
	 */
	public final void initDemo() {

	}

	/**
	 * Initialize the mini GUI
	 */
	private void miniGUI() {
		frame = new Frame(title);
		frame.setResizable(false);
		frame.setIgnoreRepaint(true);
		frame.setSize(500, 500);
		frame.setLocation(200, 200);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				running = false;
				System.exit(0);
			}
		});
		frame.setVisible(true);
		frame.createBufferStrategy(2);
		strategy = frame.getBufferStrategy();
	}

	/**
	 * Draw a body
	 * 
	 * @param g
	 *            The graphics contact on which to draw
	 * @param body
	 *            The body to be drawn
	 */
	protected void drawBody(Graphics2D g, Body body) {
		if (body.getShape() instanceof Box) {
			drawBoxBody(g, body, (Box) body.getShape());
		}
		if (body.getShape() instanceof Circle) {
			drawCircleBody(g, body, (Circle) body.getShape());
		}
		if (body.getShape() instanceof Line) {
			drawLineBody(g, body, (Line) body.getShape());
		}
	}

	/**
	 * Draw a line into the demo
	 * 
	 * @param g
	 *            The graphics to draw the line onto
	 * @param body
	 *            The body describing the line's position
	 * @param line
	 *            The line to be drawn
	 */
	protected void drawLineBody(Graphics2D g, Body body, Line line) {
		g.setColor(Color.black);

		Vector[] verts = line.getVertices(body.getPosition(), body
				.getRotation());
		g.drawLine((int) verts[0].getX(), (int) verts[0].getY(), (int) verts[1]
				.getX(), (int) verts[1].getY());
	}

	/**
	 * Draw a circle in the world
	 * 
	 * @param g
	 *            The graphics contact on which to draw
	 * @param body
	 *            The body to be drawn
	 * @param circle
	 *            The shape to be drawn
	 */
	protected void drawCircleBody(Graphics2D g, Body body, Circle circle) {
		g.setColor(Color.black);
		float x = body.getPosition().getX();
		float y = body.getPosition().getY();
		float r = circle.getRadius();
		float rot = body.getRotation();
		float xo = (float) (Math.cos(rot) * r);
		float yo = (float) (Math.sin(rot) * r);

		g.drawOval((int) (x - r), (int) (y - r), (int) (r * 2), (int) (r * 2));
	}

	/**
	 * Draw a box in the world
	 * 
	 * @param g
	 *            The graphics contact on which to draw
	 * @param body
	 *            The body to be drawn
	 * @param box
	 *            The shape to be drawn
	 */
	protected void drawBoxBody(Graphics2D g, Body body, Box box) {
		Vector[] pts = box.getPoints(body.getPosition(), body.getRotation());

		Vector v1 = pts[0];
		Vector v2 = pts[1];
		Vector v3 = pts[2];
		Vector v4 = pts[3];

		g.setColor(Color.black);
		g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
		g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
		g.drawLine((int) v3.x, (int) v3.y, (int) v4.x, (int) v4.y);
		g.drawLine((int) v4.x, (int) v4.y, (int) v1.x, (int) v1.y);
	}

	/**
	 * Draw the whole simulation
	 * 
	 * @param g
	 *            The graphics context on which to draw
	 */
	protected void draw(Graphics2D g) {
		BodyList bodies = world.getBodies();

		for (int i = 0; i < bodies.size(); i++) {
			Body body = bodies.get(i);

			drawBody(g, body);
		}

	}

	/**
	 * Retrieve the title of the demo
	 * 
	 * @return The title of the demo
	 */
	public String getTitle() {
		return title;
	}

	protected void update() {
	}

	protected void renderGUI(Graphics2D g) {
		g.setColor(Color.black);
	}

}
