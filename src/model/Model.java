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
	private static Model single;
	protected static float gravity = 10.00f;
	protected static World world = World.createWorld(new Vector(0.0f, gravity),
			10, new Strategy(1, 5));

	private ArrayList<Entities> objList = new ArrayList<Entities>();
	private ArrayList<Entities> saveObjList = new ArrayList<Entities>();
	private int maxY;
	private int maxX;
	private boolean playedBaloonSound = false;
	private CollisionListenerImpl collisionListenerImpl;

	private Model(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		initWorld();
		collisionListenerImpl = new CollisionListenerImpl();
		world.addListener(collisionListenerImpl);
		
	}
	
	public static Model getObj(int maxX, int maxY) {
		if (single == null) {
			single = new Model(maxX, maxY);
		}
		return single;

	}
	
	public static Model getObj() {
		return single;
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
			
		} else if (objType.equals(EType.gear)) {
			newEntity = new Gear();	
			
		} else if (objType.equals(EType.belt)) {
			newEntity = new Belt();	
			
		} else if (objType.equals(EType.light)) {
			newEntity = new Light();
			
		} else if (objType.equals(EType.tack)) {
			newEntity = new Tack();	
			
		} else if (objType.equals(EType.powerGear)) {
			newEntity = new PowerGear();
			
		} else if (objType.equals(EType.conveyorBelt)) {
			newEntity = new ConveyorBelt();
						
		}
		
		
		
		// prevent overlapping objects in the world
		newEntity.addObj(world, x, y);
		world.step();
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
	
	public World getWorld(){
		return world;
		
	}

	public void clearModel() {
		objList.clear();
		initWorld();
	}

	public ArrayList<Entities> getObjList() {
		return this.objList;
	}


	/**
	 * @return the gravity
	 */
	public static float getGravity() {
		return gravity;
	}

	/**
	 * @param gravity
	 *            the gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;

	}

	public void removeObjFromBoardAtLocated(int X, int Y) {
		for (int i = 0; i < this.objList.size(); i++) {
			if (X >= this.objList.get(i).getUpperX()
					&& X <= this.objList.get(i).getLowerX()
					&& Y >= this.objList.get(i).getUpperY()
					&& Y <= this.objList.get(i).getLowerY()) {
				this.objList.get(i).removeObj(world);

			}
		}
	}

/*	public Entities getObjAtLocatedAt(int X, int Y) {
		for (int i = 0; i < this.objList.size(); i++) {
			if (X >= this.objList.get(i).getUpperX()
					&& X <= this.objList.get(i).getLowerX()
					&& Y >= this.objList.get(i).getUpperY()
					&& Y <= this.objList.get(i).getLowerY()) {
				return this.objList.get(i);

			}
		}
		return null;

	}*/
	
	public void setStatesForBatteryObjs(){
		//find each battery and then look for each object around them
		for (int i = 0; i < objList.size(); i++){
			if(this.objList.get(i).toString().equalsIgnoreCase("battery")){
			
				for (int j = 0; j < objList.size(); j++){
					if(isOverlapTopLeft(this.objList.get(i),this.objList.get(j)) ||
					isOverlapTopRight(this.objList.get(i),this.objList.get(j))	||
					isOverlapBottomRight(this.objList.get(i),this.objList.get(j)) ||
					isOverlapBottomLeft(this.objList.get(i),this.objList.get(j))){
						if(this.objList.get(i).toString().equalsIgnoreCase("light") ||
						this.objList.get(i).toString().equalsIgnoreCase("powerGear")){
						this.objList.get(i).setState(1);
						this.objList.get(j).setState(1);
					}
				}
					}
			}
			}
		  }
		
		 
		 public boolean isOverlapTopLeft(Entities battery, Entities other){
			 
			 if (other.getLowerX() > battery.getUpperX() &&
				other.getLowerX() <  battery.getLowerX() &&
				other.getLowerY() > battery.getUpperY() &&
				other.getLowerY() < battery.getLowerY()){ 
				 return true;
			 }
			return false;
		}
		 
		 public boolean isOverlapTopRight(Entities battery, Entities other){
			 if (other.getUpperX() > battery.getUpperX() &&
				other.getUpperX() <  battery.getLowerX() &&
				other.getLowerY() > battery.getUpperY() &&
				other.getLowerY() < battery.getLowerY()){ 
				 return true;
					 }
				return true;
			}
		 
		 public boolean isOverlapBottomLeft(Entities battery, Entities other){
			 if (other.getLowerX() > battery.getUpperX() &&
				other.getLowerX() <  battery.getLowerX() &&
				other.getUpperY() > battery.getUpperY() &&
				other.getUpperY() < battery.getLowerY()){ 
				 return true;
							 }
				return true;
			}
		 
		 public boolean isOverlapBottomRight(Entities battery, Entities other){
			 if (other.getUpperX() > battery.getUpperX() &&
				other.getUpperX() <  battery.getLowerX() &&
				other.getUpperY() > battery.getUpperY() &&
				other.getUpperY() < battery.getLowerY()){ 
			 return true;
					 }
			 return false;
			}
	
	
	
}
