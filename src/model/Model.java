package model;

import java.util.ArrayList;


import engine.World;
import engine.shapes.*;
import engine.strategies.QuadSpaceStrategy;
import engine.vector.Vector;
import entities.*;
import enums.EType;

public class Model {
	/** The world containing the physics model */
	protected World world = new World(new Vector(0.0f, 10.0f), 10, new QuadSpaceStrategy(1,5));
	private ArrayList<Entities> objList = new ArrayList<Entities>();
	private int maxY;
	private int maxX;

	public Model(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		
		Body ground = new StaticBody("Ground1", new Box(maxX, 5.0f));
		ground.setPosition(maxX/2, maxY);
		ground.setRestitution(1.0f);
		world.add(ground);
		

	}
	
	// Added to give you integers instead of floats from my end.
	public boolean addObjToBoard(String objType,int x, int y){
		return addObjToBoard(objType,new Float(x),new Float(y));
	}
	
	public boolean addObjToBoard(String objType,float x, float y) {
		//add object first then look for collisions and off the board
		if (objType.equals(EType.basketball)){
			BasketBall newEntity = new BasketBall("BasketBall");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("Balloon")){
			Balloon newEntity = new Balloon("Baloon");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals(EType.bowlingball)){
			BowlingBall newEntity = new BowlingBall();
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("Bucket")){
			Bucket newEntity = new Bucket("Bucket");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("Candle")){
			Candle newEntity = new Candle("Candle");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("Domino")){
			Domino newEntity = new Domino("Domino");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("PingPongBall")){
			PingPongBall newEntity = new PingPongBall("PingPongBall");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("RightRamp")){
			RightRamp newEntity = new RightRamp("RightRamp");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("LeftRamp")){
			LeftRamp newEntity = new LeftRamp("LeftRamp");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		if (objType.equals("Light")){
			Light newEntity = new Light("Light");
			this.objList.add(newEntity);
			newEntity.addObj(world, x, y);
			
			//if it will not fit on board remove it from array and world
			if (newEntity.getLowerX() < 0 || newEntity.getLowerY() < 0 || newEntity.getUpperX() > maxX || newEntity.getUpperY() > maxY){
				objList.remove(objList.size()-1);
				newEntity.removeObj(world);
				return false;
			}
		}
		

		return true;

	}
	
	public void start(){
		world.step();
	}
	
	public void stop(){
		objList.clear();
	}
	
	public ArrayList<Entities> getObjList(){
		return this.objList;
		}

}
