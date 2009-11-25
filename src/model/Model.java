package model;

import java.util.ArrayList;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.forcesource.ForceSource;
import engine.shapes.Box;
import engine.strategies.QuadSpaceStrategy;
import engine.vector.Vector;
import entities.Balloon;
import entities.BasketBall;
import entities.BowlingBall;
import entities.Bucket;
import entities.Candle;
import entities.Domino;
import entities.Entities;
import entities.LeftRamp;
import entities.Light;
import entities.PingPongBall;
import entities.RightRamp;

public class Model {
	/** The world containing the physics model */
	protected World world = new World(new Vector(0.0f, 10.0f), 10, new QuadSpaceStrategy(1,5));
	private ArrayList<Entities> objList = new ArrayList<Entities>();
	private int maxY;
	private int maxX;

	public Model(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		
		Body ground = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		ground.setPosition(250.0f, 400);
		ground.setRestitution(1.0f);
		ground.setRotation(.4f);
		world.add(ground);

	}

	public boolean addObjToBoard(String objType,float x, float y) {
		//add object first then look for collisions and off the board
		if (objType.equals("BasketBall")){
			BasketBall newEntity = new BasketBall("BasketBall");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("Balloon")){
			Balloon newEntity = new Balloon("Baloon");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("BowlingBall")){
			BowlingBall newEntity = new BowlingBall();
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("Bucket")){
			Bucket newEntity = new Bucket("Bucket");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("Candle")){
			Candle newEntity = new Candle("Candle");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("Domino")){
			Domino newEntity = new Domino("Domino");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("PingPongBall")){
			PingPongBall newEntity = new PingPongBall("PingPongBall");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("RightRamp")){
			RightRamp newEntity = new RightRamp("RightRamp");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("LeftRamp")){
			LeftRamp newEntity = new LeftRamp("LeftRamp");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		if (objType.equals("Light")){
			Light newEntity = new Light("Light");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
		}
		
		
		
		
		// if it will fit on the board then don't remove
		Entities thisEntitie;
		int objListSize= objList.size();
		thisEntitie = objList.get(objListSize);
		if (thisEntitie.getLowerX() < 0 || thisEntitie.getLowerY() < 0 || thisEntitie.getUpperX() > maxX || thisEntitie.getUpperY() > maxY){
			objList.remove(objListSize);
			return false;
		}

		//ForceSource body = null;
		//world.add(body);
		return true;

	}

}
