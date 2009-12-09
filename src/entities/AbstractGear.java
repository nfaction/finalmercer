package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
	private static BufferedImage[] staticSprites;

	
	private float factor = 3f;
	
	public AbstractGear(EType e) {
		super(e);
		init();
	}
	
	public AbstractGear(EType e, float newFactor){
		super(e);
		factor = newFactor;
		init();
	}
	
	public AbstractGear(EType e, float newFactor, float rotation ){
		super(e);
		factor = newFactor;
		init();
		wheel.adjustAngularVelocity(rotation);
	}

	private void init() {
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/abstractGearSpriteSheet.png"), 3, 3);
		sprite = staticSprites;
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
		return wheel.getPosition().getX();
	}

	@Override
	public float getY() {
		return wheel.getPosition().getY();
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
	public int gettouchingBodies() {
		return this.wheel.getTouchingCount() - 8;
	}

	public BufferedImage getSpriteImage() {
		float rotation = wheel.getRotation();
		rotation = rotation % (2f * (new Float(Math.PI)));

		BufferedImage temp = sprite[0];
		temp = utils.rotate(temp, rotation);
		temp = utils.makeColorTransparent(temp, Color.black);
		return temp;

	}

}
