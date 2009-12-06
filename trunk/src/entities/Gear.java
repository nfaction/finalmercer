package entities;

import engine.World;
import engine.joint.FixedJoint;
import engine.joint.Joint;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import enums.EType;

public class Gear extends Entities {
	private Body wheel;
	private Body[] teeth;
	private Joint[] joints;
	
	private float factor = 4f;

	public Gear() {
		super(EType.gear);
		init();
	}

	private void init() {
		wheel = new Body(new Circle(6f*factor), 2);
		teeth = new Body[6];
		joints = new FixedJoint[6];
		for (int i = 0; i < 6; i++) {
			teeth[i] = new Body(new Box(3f*factor, 2f*factor), 2);
			joints[i] = new FixedJoint(wheel, teeth[i]);
			teeth[i].addExcludedBody(wheel);
		}		
	}

	@Override
	public void addObj(World world, float x, float y) {
		setPosition(x,y);
		world.add(wheel);
		for (int i = 0; i < teeth.length; i++) {
			world.add(teeth[i]);
		//	world.add(joints[i]);
		}
		world.add(joints[0]);
	}

	private void setPosition(float x, float y) {
		wheel.setPosition(x, y);

		for (int i = 0; i < teeth.length; i++) {
			teeth[i].setPosition(20+x+15*i, y);
		}
//		for (int i = 0; i < joints.length; i++) {
//			// joints[i];
//		}
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
		world.remove(wheel);
		for (int i = 0; i < teeth.length; i++) {
			world.remove(teeth[i]);
		}
		for (int i = 0; i < joints.length; i++) {
			world.remove(joints[i]);
		}
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
	public int getXLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int gettouchingBodies() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
