package entities;

import engine.World;
import engine.joint.FixedJoint;
import engine.joint.Joint;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;

import enums.EType;

public class AbstractGear extends Entities {
	protected Body wheel;
	private Body[] teeth;
	private Joint[] joints;
	
	private float factor = 3f;
	
	public AbstractGear() {
		super(EType.gear);
		init();
		setImagePath("Images/abstractGear.gif");
	}
	
	public AbstractGear(float newFactor){
		super(EType.gear);
		factor = newFactor;
		init();
		setImagePath("Images/abstractGear.gif");
	}
	
	public AbstractGear(float newFactor, float rotation ){
		super(EType.gear);
		factor = newFactor;
		init();
		wheel.adjustAngularVelocity(rotation);
		setImagePath("Images/abstractGear.gif");
	}

	private void init() {
		wheel = new Body(new Circle(6f*factor), 2);
		wheel.setMoveable(false);
		teeth = new Body[8];
		joints = new FixedJoint[8];
		for (int i = 0; i < 8; i++) {
			teeth[i] = new Body(new Box(3f*factor, 2f*factor), 2);
			teeth[i].addExcludedBody(wheel);
		}		
	}

	@Override
	public void addObj(World world, float x, float y) {
		setPosition(x,y);

		world.add(wheel);
		for (int i = 0; i < teeth.length; i++) {
			world.add(teeth[i]);
			world.add(joints[i]);
		}
	}

	private void setPosition(float x, float y) {
		wheel.setPosition(x, y);
		
		float pi = new Float(Math.PI);
		teeth[0].setPosition(x+7.5f*factor, y);
		teeth[1].setPosition(x-7.5f*factor, y);	
		
		teeth[2].setPosition(x+5.5f*factor, y+5.5f*factor);
		teeth[2].setRotation(pi/4);
		teeth[3].setPosition(x-5.5f*factor, y+5.5f*factor);
		teeth[3].setRotation(3*pi/4);
		
		teeth[4].setPosition(x+5.5f*factor, y-5.5f*factor);
		teeth[4].setRotation(3*pi/4);
		teeth[5].setPosition(x-5.5f*factor, y-5.5f*factor);
		teeth[5].setRotation(pi/4);
		
		teeth[6].setPosition(x, y+7.5f*factor);
		teeth[6].setRotation(pi/2);
		teeth[7].setPosition(x, y-7.5f*factor);
		teeth[7].setRotation(pi/2);
		
		for (int i = 0; i < joints.length; i++) {
			joints[i] = new FixedJoint(wheel,teeth[i]);
		}
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
		

	}

	@Override
	public int getSpriteWidth() {

		return absGearWidth;
	}

	@Override
	public int getSpriteHeight() {

		return absGearHeight;
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

	@Override
	public int getSpriteX() {

		return bbX;
	}

	@Override
	public int getSpriteY() {
		// TODO Auto-generated method stub
		return bbY;
	}

	@Override
	public void setSprite() {
		// TODO Auto-generated method stub
		
	}

}
