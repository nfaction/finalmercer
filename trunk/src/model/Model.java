package model;

//
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import playSounds.CollisionListenerImpl;
import playSounds.PlaySound;

import engine.Strategy;
import engine.World;
import engine.shapes.*;
import engine.vector.Vector;
import entities.*;
import enums.EType;

public class Model extends Observable implements Serializable {
	/** The world containing the physics model */

	protected static float gravity = 10.00f;
	protected static World world = World.createWorld(new Vector(0.0f, gravity),
			10, new Strategy(1, 5));

	private ArrayList<Entities> objList = new ArrayList<Entities>();
	private ArrayList<Entities> saveObjList = new ArrayList<Entities>();
	private int maxY;
	private int maxX;
	private boolean playedBaloonSound = false;
	private CollisionListenerImpl collisionListenerImpl = new CollisionListenerImpl();

	public Model(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		initWorld();
		world.addListener(collisionListenerImpl);
	}

	private void initWorld() {
		world.clear();
		world.setGravity(0, 0);
		Body ground = new StaticBody("Ground", new Box(maxX, 5.0f));
		ground.setPosition(maxX / 2, maxY);
		ground.setRestitution(1.0f);
		world.add(ground);
		world.enableRestingBodyDetection(1f, 1f, 1f);
	}

	public boolean addObjToBoard(EType objType, float x, float y) {
		Entities newEntity = null;

		// add object first then look for collisions and off the board
		if (objType.equals(EType.basketball)) {
			newEntity = new BasketBall();

		} else if (objType.equals(EType.balloon)) {
			newEntity = new Balloon();

		} else if (objType.equals(EType.bowlingball)) {
			newEntity = new BowlingBall();

		} else if (objType.equals(EType.pingPongBall)) {
			newEntity = new PingPongBall();

		} else if (objType.equals(EType.leftRamp)) {
			newEntity = new LeftRamp();

		} else if (objType.equals(EType.rightRamp)) {
			newEntity = new RightRamp();
			
		} else if (objType.equals(EType.straightRamp)) {
			newEntity = new StraightRamp();

		} else if (objType.equals(EType.battery)) {
			newEntity = new Battery();
			
		} else if (objType.equals(EType.conveyorBelt)) {
			newEntity = new ConveyorBelt();
	
		} else if (objType.equals(EType.gear)) {
			newEntity = new Gear();	
			
		} else if (objType.equals(EType.belt)) {
			newEntity = new Belt();	
			
		} else if (objType.equals(EType.light)) {
			newEntity = new Light();
			
		} else if (objType.equals(EType.pin)) {
			newEntity = new Pin();	
			
		} else if (objType.equals(EType.powerGear)) {
			newEntity = new PowerGear();	
			
		}

		// Code to not allow overlaps on all objects already in world

		/*
		 * for (int i = 0; i < objList.size(); i++) { if (newEntity.getLowerX()
		 * > this.objList.get(i).getUpperX() && newEntity.getLowerX() <
		 * this.objList.get(i).getLowerX() && newEntity.getUpperX() >
		 * this.objList.get(i).getUpperX() && newEntity.getUpperX() <
		 * this.objList.get(i).getLowerX() &&
		 * 
		 * newEntity.getLowerY() > this.objList.get(i).getUpperY() &&
		 * newEntity.getLowerY() < this.objList.get(i).getLowerY() &&
		 * newEntity.getUpperY() > this.objList.get(i).getUpperY() &&
		 * newEntity.getUpperY() < this.objList.get(i).getLowerY()) {
		 * 
		 * notifyObservers(); return false;
		 * 
		 * } }
		 */
		newEntity.addObj(world, x, y);
		world.step();
		// prevent overlaping objects
		if (newEntity.gettouchingBodies() > 0) {
			notifyObservers();
			newEntity.removeObj(world);
			return false;

		}

		// if it will not fit on board remove it from array and world
		if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0
				|| newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY) {
			newEntity.removeObj(world);
			notifyObservers();
			return false;
		} else {
			// newEntity.addObj(world, x, y);
			this.objList.add(newEntity);
			notifyObservers();
			return true;
		}
	}

	public void step() {
		world.setGravity(0, gravity);

		for (int i = 0; i < objList.size(); i++) {
			if (this.objList.get(i).toString().equalsIgnoreCase("Balloon")
					&& playedBaloonSound == false) {
				PlaySound mySoundPlayer = new PlaySound();
				String baseDir = System.getProperty("user.dir") + "/sounds/";
				mySoundPlayer.play(baseDir + "BaloonUp.wav");
				playedBaloonSound = true;
			}
		}

		world.step();

		// updates all the objects
		for (int i = 0; i < this.objList.size(); i++) {
			this.objList.get(i).upDate();
			this.notifyObservers();
		}
	}

	public void reset() {
		objList.clear();
		this.objList = this.saveObjList;
		initWorld();
		for (int i = 0; i < objList.size(); i++) {
			objList.get(i).addObj(world, objList.get(i).getX(),
					objList.get(i).getY());
		}

	}

	public void stop() {
		objList.clear();
		initWorld();
	}

	public ArrayList<Entities> getObjList() {
		return this.objList;
	}

	/**
	 * @return the gravity
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * @param gravity
	 *            the gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;

	}

	public void removeObjFromBoardLocatedAt(int X, int Y) {
		for (int i = 0; i < this.objList.size(); i++) {
			if (X >= this.objList.get(i).getUpperX()
					&& X <= this.objList.get(i).getLowerX()
					&& Y >= this.objList.get(i).getUpperY()
					&& Y <= this.objList.get(i).getLowerY()) {
				this.objList.get(i).removeObj(world);

			}
		}
	}

	public Entities getObjLocatedAt(int X, int Y) {
		for (int i = 0; i < this.objList.size(); i++) {
			if (X >= this.objList.get(i).getUpperX()
					&& X <= this.objList.get(i).getLowerX()
					&& Y >= this.objList.get(i).getUpperY()
					&& Y <= this.objList.get(i).getLowerY()) {
				return this.objList.get(i);

			}
		}
		return null;

	}
	
	
	
	
	
	
}
