package model;

import java.util.ArrayList;
import java.util.Observable;

import playSounds.CollisionListenerImpl;

import engine.World;
import engine.shapes.*;
import engine.strategies.QuadSpaceStrategy;
import engine.vector.Vector;
import entities.*;
import enums.EType;

public class Model extends Observable {
	/** The world containing the physics model */
	protected World world = World.createWorld(new Vector(0.0f, 10.0f), 10,
			new QuadSpaceStrategy(1, 5));
	private ArrayList<Entities> objList = new ArrayList<Entities>();
	private ArrayList<Entities> saveObjList = new ArrayList<Entities>();
	private boolean save;
	private int maxY;
	private int maxX;
	private CollisionListenerImpl collisionListenerImpl = new CollisionListenerImpl();
	

	public Model(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		initWorld();
		world.addListener(collisionListenerImpl);
	}

	private void initWorld() {
		this.save = true;
		world.clear();
		world.setGravity(0, 10);
		Body ground = new StaticBody("Ground", new Box(maxX, 5.0f));
		ground.setPosition(maxX / 2, maxY);
		ground.setRestitution(1.0f);
		world.add(ground);
	}

	// Added to give you integers instead of floats from my end.
	public boolean addObjToBoard(EType objType, int x, int y) {
		return addObjToBoard(objType, new Float(x), new Float(y));
	}

	public boolean addObjToBoard(EType objType, float x, float y) {
		Entities newEntity = null;
		// add object first then look for collisions and off the board
		if (objType.equals(EType.basketball)) {
			newEntity = new BasketBall();

			// } else if (objType.equals(EType.balloon)) {
			// newEntity = new Balloon();

		} else if (objType.equals(EType.bowlingball)) {
			newEntity = new BowlingBall();

			// } else if (objType.equals(EType.bucket)) {
			// newEntity = new Bucket();
			// } else if (objType.equals(EType.candle)) {
			// newEntity = new Candle();
			//
			// } else if (objType.equals(EType.domino)) {
			// newEntity = new Domino();
			//
			// } else if (objType.equals(EType.pingPongBall)) {
			// newEntity = new PingPongBall();
			//
			// } else if (objType.equals(EType.rightRamp)) {
			// newEntity = new RightRamp();
			//
			// } else if (objType.equals(EType.leftRamp)) {
			// newEntity = new LeftRamp();
			//
			// } else {// objType.equals(EType.light)
			// newEntity = new Light();
		}
		// if it will not fit on board remove it from array and world
		if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0
				|| newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY) {
			notifyObservers();;
			return false;
		} else {			
			newEntity.addObj(world, x, y);
			this.objList.add(newEntity);
			notifyObservers();
			return true;
		}
	}

	public void step() {
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
}
