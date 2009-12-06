package engine;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.Line;
import engine.strategies.QuadSpaceStrategy;
import engine.vector.Vector;
import entities.Balloon;
import entities.BasketBall;
import entities.LeftRamp;
import entities.RightRamp;
import entities.StraightRamp;

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
	protected World world = World.createWorld(new Vector(0.0f, 10.0f), 10,
			new QuadSpaceStrategy(20, 5));
	private boolean running = true;
	private BufferStrategy strategy;
	protected boolean needsReset;
	private Balloon newBalloon;

	/**
	 * Main
	 * 
	 * @param argv
	 *            Nothing
	 */
	public static void main(String[] argv) {
		Demo demo = new Demo();
		demo.start();
	}

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

			update();
		}
	}

	protected void init(World world) {
//		Body land = new StaticBody("Line1", new Line(100, 50));
//		land.setPosition(150, 150);
//		land.setRestitution(1f);
//		world.add(land);
//		land = new StaticBody("Line2", new Line(150, -75));
//		land.setPosition(250, 300);
//		land.setRestitution(1f);
//		world.add(land);
//		land = new StaticBody("Line3", new Line(150, 75));
//		land.setPosition(100, 350);
//		land.setRestitution(1f);
//		world.add(land);
//		land = new StaticBody("Line4", new Line(150, 0));
//		land.setPosition(300, 450);
//		land.setRestitution(1f);
//		world.add(land);
//		land = new StaticBody("Floor", new Line(494, 0));
//		land.setPosition(5, 475);
//		land.setRestitution(1f);
//		world.add(land);
//		land = new StaticBody("LWall", new Line(0, -475));
//		land.setPosition(5, 475);
//		land.setRestitution(1f);
//		world.add(land);
//		land = new StaticBody("RWall", new Line(0, -475));
//		land.setPosition(495, 475);
//		land.setRestitution(1f);
//		world.add(land);
//		land = new StaticBody("Roof", new Line(494, 0));
//		land.setPosition(5, 25);
//		land.setRestitution(1f);
//		world.add(land);

		LeftRamp newGround1 = new LeftRamp();
		newGround1.addObj(world, 300, 200);
		
		RightRamp newGround2 = new RightRamp();
		newGround2.addObj(world, 80, 50);
		
		StraightRamp newGround3 = new StraightRamp();
		newGround3.addObj(world, 150, 130);
		
		BasketBall newEntity = new BasketBall();
		newEntity.addObj(world, 200, 50);
		
//		BowlingBall newEntity1 = new BowlingBall();
//		newEntity1.addObj(world, 200, 55);
//
//		PingPongBall newPPB = new PingPongBall();
//		newPPB.addObj(world, 250, 57);

		newBalloon = new Balloon();
		newBalloon.addObj(world, 270, 450);
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
		frame.setVisible(true);
		frame.createBufferStrategy(2);
		strategy = frame.getBufferStrategy();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				running = false;
				System.exit(0);
			}
		});
	}

	/**
	 * Draw a body
	 * 
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
	 */
	protected void drawCircleBody(Graphics2D g, Body body, Circle circle) {
		g.setColor(Color.black);
		float x = body.getPosition().getX();
		float y = body.getPosition().getY();
		float r = circle.getRadius();

		g.drawOval((int) (x - r), (int) (y - r), (int) (r * 2), (int) (r * 2));
	}

	/**
	 * Draw a box in the world
	 * 
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
	 */
	protected void draw(Graphics2D g) {
		BodyList bodies = world.getBodies();

		for (int i = 0; i < bodies.size(); i++) {
			Body body = bodies.get(i);

			drawBody(g, body);
		}

	}

	/**
	 * Title of the demo
	 */
	public String getTitle() {
		return title;
	}

	protected void update() {
		newBalloon.upDate();
	}

	protected void renderGUI(Graphics2D g) {
		g.setColor(Color.black);
	}

}
