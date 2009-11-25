package engine.test;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import engine.Arbiter;
import engine.ArbiterList;
import engine.BodyList;
import engine.JointList;
import engine.World;
import engine.collide.Contact;
import engine.joint.*;
import engine.shapes.*;
import engine.strategies.QuadSpaceStrategy;
import engine.vector.*;


/**
 * A common demo box super class.
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public abstract class AbstractDemo {
	/** The frame displaying the demo */
	protected Frame frame;
	/** The title of the current demo */
	protected String title;
	/** The world containing the physics model */
	protected World world = new World(new Vector(0.0f, 10.0f), 10, new QuadSpaceStrategy(20,5));
	/** True if the simulation is running */
	private boolean running = true;
	/** The rendering strategy */
	private BufferStrategy strategy;
	
	/** True if we should reset the demo on the next loop */
	protected boolean needsReset;
	/** True if we should render normals */
	private boolean normals = true;
	/** True if we should render contact points */
	private boolean contacts = true;
	
	/**
	 * Create a new demo
	 * 
	 * @param title The title of the demo
	 */
	public AbstractDemo(String title) {
		this.title = title;
	}
	
	/** 
	 * Retrieve the title of the demo
	 * 
	 * @return The title of the demo
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Notification that a key was pressed
	 * 
	 * @param c The character of key hit
	 */
	protected void keyHit(char c) {
		if (c == 'r') {
			needsReset = true;
		}
		if (c == 'c') {
			normals = !normals;
			contacts = !contacts;
		}
	}
	
	/**
	 * Initialise the GUI 
	 */
	private void initGUI() {
		frame = new Frame(title);
		frame.setResizable(false);
		frame.setIgnoreRepaint(true);
		frame.setSize(500,500);
		
		int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 500) / 2;
		int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 500) / 2;
		
		frame.setLocation(x,y);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				running = false;
				System.exit(0);
			}
		});
		frame.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				keyHit(e.getKeyChar());
			}
			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 27) {
					System.exit(0);
				}
			}
			
		});
		
		frame.setVisible(true);
		frame.createBufferStrategy(2);
		
		strategy = frame.getBufferStrategy();
	}
	
	/**
	 * Start the simulation running
	 */
	public void start() {
		initGUI();
		initDemo();
		
		float target = 1000 / 60.0f;
		float frameAverage = target;
		long lastFrame = System.currentTimeMillis();
		float yield = 10000f;
		float damping = 0.1f;
		
		long renderTime = 0;
		long logicTime = 0;
		
		while (running) {
			// adaptive timing loop from Master Onyx
			long timeNow = System.currentTimeMillis();
			frameAverage = (frameAverage * 10 + (timeNow - lastFrame)) / 11;
			lastFrame = timeNow;
			
			yield+=yield*((target/frameAverage)-1)*damping+0.05f;

			for(int i=0;i<yield;i++) {
				Thread.yield();
			}
			
			// render
			long beforeRender = System.currentTimeMillis();
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.white);
			g.fillRect(0,0,500,500);
			
			draw(g);
			renderGUI(g);
			g.setColor(Color.black);
			g.drawString("FAv: "+frameAverage,10,50);
			g.drawString("FPS: "+(int) (1000 / frameAverage),10,70);
			g.drawString("Yield: "+yield,10,90);
			g.drawString("Arbiters: "+world.getArbiters().size(),10,110);
			g.drawString("Bodies: "+world.getBodies().size(),10,130);
			g.drawString("R: "+renderTime,10,150);
			g.drawString("L: "+logicTime,10,170);
			g.drawString("Energy: "+world.getTotalEnergy(),10,190);
			g.dispose();
			strategy.show();
			renderTime = System.currentTimeMillis() - beforeRender;
			
			// update data model
			long beforeLogic = System.currentTimeMillis();
			for (int i=0;i<5;i++) {
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
	
	/**
	 * Update the demo - just in case we want to add anything over
	 * the top
	 */
	protected void update() {
	}
	
	/**
	 * Demo customisable GUI render
	 * 
	 * @param g The graphics context to use for rendering here
	 */
	protected void renderGUI(Graphics2D g) {
		g.setColor(Color.black);
		g.drawString("R - Restart Demo",15,430);
	}
	
	/**
	 * Draw a specific contact point determined from the simulation
	 * 
	 * @param g The graphics context on which to draw
	 * @param contact The contact to draw
	 */
	protected void drawContact(Graphics2D g, Contact contact) {
		int x = (int) contact.getPosition().getX();
		int y = (int) contact.getPosition().getY();
		if (contacts) {
			g.setColor(Color.blue);
			g.fillOval(x-3,y-3,6,6);
		}
		
		if (normals) {
			int dx = (int) (contact.getNormal().getX() * 10);
			int dy = (int) (contact.getNormal().getY() * 10);
			g.setColor(Color.darkGray);
			g.drawLine(x,y,x+dx,y+dy);
		}
	}
	
	/**
	 * Draw a body 
	 * 
	 * @param g The graphics contact on which to draw
	 * @param body The body to be drawn
	 */
	protected void drawBody(Graphics2D g, Body body) {
		if (body.getShape() instanceof Box) {
			drawBoxBody(g,body,(Box) body.getShape());
		}
		if (body.getShape() instanceof Circle) {
			drawCircleBody(g,body,(Circle) body.getShape());
		}
		if (body.getShape() instanceof Line) {
			drawLineBody(g,body,(Line) body.getShape());
		}
	}
	
	/**
	 * Draw a line into the demo
	 * 
	 * @param g The graphics to draw the line onto
	 * @param body The body describing the line's position
	 * @param line The line to be drawn
	 */
	protected void drawLineBody(Graphics2D g, Body body, Line line) {
		g.setColor(Color.black);

		Vector[] verts = line.getVertices(body.getPosition(), body.getRotation());
		g.drawLine(
				(int) verts[0].getX(),
				(int) verts[0].getY(), 
				(int) verts[1].getX(),
				(int) verts[1].getY());
	}
	
	/**
	 * Draw a circle in the world
	 * 
	 * @param g The graphics contact on which to draw
	 * @param body The body to be drawn
	 * @param circle The shape to be drawn
	 */
	protected void drawCircleBody(Graphics2D g, Body body, Circle circle) {
		g.setColor(Color.black);
		float x = body.getPosition().getX();
		float y = body.getPosition().getY();
		float r = circle.getRadius();
		float rot = body.getRotation();
		float xo = (float) (Math.cos(rot) * r);
		float yo = (float) (Math.sin(rot) * r);
		
		g.drawOval((int) (x-r),(int) (y-r),(int) (r*2),(int) (r*2));
		g.drawLine((int) x,(int) y,(int) (x+xo),(int) (y+yo));
	}
	
	/**
	 * Draw a box in the world
	 * 
	 * @param g The graphics contact on which to draw
	 * @param body The body to be drawn
	 * @param box The shape to be drawn
	 */
	protected void drawBoxBody(Graphics2D g, Body body, Box box) {
		Vector[] pts = box.getPoints(body.getPosition(), body.getRotation());
		
		Vector v1 = pts[0];
		Vector v2 = pts[1];
		Vector v3 = pts[2];
		Vector v4 = pts[3];
		
		g.setColor(Color.black);
		g.drawLine((int) v1.x,(int) v1.y,(int) v2.x,(int) v2.y);
		g.drawLine((int) v2.x,(int) v2.y,(int) v3.x,(int) v3.y);
		g.drawLine((int) v3.x,(int) v3.y,(int) v4.x,(int) v4.y);
		g.drawLine((int) v4.x,(int) v4.y,(int) v1.x,(int) v1.y);
	}

	/**
	 * Draw a joint 
	 * 
	 * @param g The graphics contact on which to draw
	 * @param j The joint to be drawn
	 */
	public void drawJoint(Graphics2D g, Joint j) {
		if (j instanceof FixedJoint) {
			FixedJoint joint = (FixedJoint) j;
			
			g.setColor(Color.red);
			float x1 = joint.getBody1().getPosition().getX();
			float x2 = joint.getBody2().getPosition().getX();
			float y1 = joint.getBody1().getPosition().getY();
			float y2 = joint.getBody2().getPosition().getY();
			
			g.drawLine((int) x1,(int) y1,(int) x2,(int) y2);
		}
		if(j instanceof SlideJoint){
			SlideJoint joint = (SlideJoint) j;
			
			Body b1 = joint.getBody1();
			Body b2 = joint.getBody2();
	
			Vector2D R1 = new Vector2D(b1.getRotation());
			Vector2D R2 = new Vector2D(b2.getRotation());
	
			Vector x1 = b1.getPosition();
			Vector p1 = MathUtil.mul(R1,joint.getAnchor1());
			p1.add(x1);
	
			Vector x2 = b2.getPosition();
			Vector p2 = MathUtil.mul(R2,joint.getAnchor2());
			p2.add(x2);
			
			Vector im = new Vector(p2);
			im.sub(p1);
			im.normalise();
			
			
			
			g.setColor(Color.red);
			g.drawLine((int)p1.x,(int)p1.y,(int)(p1.x+im.x*joint.getMinDistance()),(int)(p1.y+im.y*joint.getMinDistance()));
			g.setColor(Color.blue);
			g.drawLine((int)(p1.x+im.x*joint.getMinDistance()),(int)(p1.y+im.y*joint.getMinDistance()),(int)(p1.x+im.x*joint.getMaxDistance()),(int)(p1.y+im.y*joint.getMaxDistance()));
		}
		if(j instanceof AngleJoint){
			AngleJoint angleJoint = (AngleJoint)j;
			Body b1 = angleJoint.getBody1();
			Body b2 = angleJoint.getBody2();
			float RA = j.getBody1().getRotation() + angleJoint.getRotateA();
			float RB = j.getBody1().getRotation() + angleJoint.getRotateB();
			
			Vector VA = new Vector((float) Math.cos(RA), (float) Math.sin(RA));
			Vector VB = new Vector((float) Math.cos(RB), (float) Math.sin(RB));
			
			Vector2D R1 = new Vector2D(b1.getRotation());
			Vector2D R2 = new Vector2D(b2.getRotation());
			
			Vector x1 = b1.getPosition();
			Vector p1 = MathUtil.mul(R1,angleJoint.getAnchor1());
			p1.add(x1);
	
			Vector x2 = b2.getPosition();
			Vector p2 = MathUtil.mul(R2,angleJoint.getAnchor2());
			p2.add(x2);
			
			g.setColor(Color.red);
			g.drawLine((int)p1.x,(int)p1.y,(int)(p1.x+VA.x*20),(int)(p1.y+VA.y*20));
			g.drawLine((int)p1.x,(int)p1.y,(int)(p1.x+VB.x*20),(int)(p1.y+VB.y*20));
		}
		if (j instanceof BasicJoint) {
			BasicJoint joint = (BasicJoint) j;
			
			Body b1 = joint.getBody1();
			Body b2 = joint.getBody2();
	
			Vector2D R1 = new Vector2D(b1.getRotation());
			Vector2D R2 = new Vector2D(b2.getRotation());
	
			Vector x1 = b1.getPosition();
			Vector p1 = MathUtil.mul(R1,joint.getLocalAnchor1());
			p1.add(x1);
	
			Vector x2 = b2.getPosition();
			Vector p2 = MathUtil.mul(R2,joint.getLocalAnchor2());
			p2.add(x2);
	
			g.setColor(Color.red);
			g.drawLine((int) x1.getX(), (int) x1.getY(), (int) p1.x, (int) p1.y);
			g.drawLine((int) p1.x, (int) p1.y, (int) x2.getX(), (int) x2.getY());
			g.drawLine((int) x2.getX(), (int) x2.getY(), (int) p2.x, (int) p2.y);
			g.drawLine((int) p2.x, (int) p2.y, (int) x1.getX(), (int) x1.getY());
		}
		if(j instanceof RopeJoint){
			RopeJoint joint = (RopeJoint) j;
			
			Body b1 = joint.getBody1();
			Body b2 = joint.getBody2();
	
			Vector2D R1 = new Vector2D(b1.getRotation());
			Vector2D R2 = new Vector2D(b2.getRotation());
	
			Vector x1 = b1.getPosition();
			Vector p1 = MathUtil.mul(R1,joint.getAnchor1());
			p1.add(x1);
	
			Vector x2 = b2.getPosition();
			Vector p2 = MathUtil.mul(R2,joint.getAnchor2());
			p2.add(x2);
			
			g.setColor(Color.red);
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.x, (int) p2.y);
		}
		
	}
	
	/**
	 * Draw the whole simulation
	 * 
	 * @param g The graphics context on which to draw
	 */
	protected void draw(Graphics2D g) {
		BodyList bodies = world.getBodies();
		
		for (int i=0;i<bodies.size();i++) {
			Body body = bodies.get(i);
			
			drawBody(g, body);
		}
		
		JointList joints = world.getJoints();
		
		for (int i=0;i<joints.size();i++) {
			Joint joint = joints.get(i);
			
			drawJoint(g, joint);
		}
		
		ArbiterList arbs = world.getArbiters();
		
		for (int i=0;i<arbs.size();i++) {
			Arbiter arb = arbs.get(i);
			
			Contact[] contacts = arb.getContacts();
			int numContacts = arb.getNumContacts();
			
			for (int j=0;j<numContacts;j++) {
				drawContact(g, contacts[j]);
			}
		}
	}
	
	/**
	 * Initialise the demo - clear the world
	 */
	public final void initDemo() {
		world.clear();
		world.setGravity(0,10);
		
		System.out.println("Initialising:" +getTitle());
		init(world);
	}

	/**
	 * Should be implemented by the demo, add the bodies/joints
	 * to the world.
	 * 
	 * @param world The world in which the simulation is going to run
	 */
	protected abstract void init(World world);
	
}
