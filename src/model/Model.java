package model;

import java.util.ArrayList;

import engine.raw.World;
import engine.raw.forcesource.ForceSource;
import engine.raw.strategies.QuadSpaceStrategy;
import engine.vector.Vector2f;
import entities.BasketBall;
import entities.Entities;

public class Model {
	/** The world containing the physics model */
	protected World world = new World(new Vector2f(0.0f, 10.0f), 10, new QuadSpaceStrategy(1,5));
	private ArrayList<Entities> objList = new ArrayList<Entities>();
	private int maxY;
	private int maxX;

	public Model(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public boolean addObjToBoard(String objType) {
		int objListSize = objList.size()+1;
		Entities thisEntitie;
		//add object first then look for collisions and off the board
		if (objType.equals("BasketBall"))
			this.objList.add(new BasketBall("BasketBall"));
		
		// if it will fit on the board then don't remove
		thisEntitie = objList.get(objListSize);
		if (thisEntitie.getLowerX() < 0 || thisEntitie.getLowerY() < 0 || thisEntitie.getUpperX() > maxX || thisEntitie.getUpperY() > maxY){
			objList.remove(objListSize);
			return false;
		}
		//error here needs to be a body!!
		ForceSource body = null;
		world.add(body);
		return true;

	}

}
