package entities;

import engine.World;
import enums.EType;

public class Wall extends Entities {

	public Wall(String objType) {
		super(EType.wall);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addObj(World world, float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeObj(World world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upDate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSpriteX(int count) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpriteY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {

		return "wall";
	}

}
