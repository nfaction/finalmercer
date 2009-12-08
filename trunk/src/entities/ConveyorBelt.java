package entities;

import engine.*;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.StaticBody;
import enums.EType;

public class ConveyorBelt extends Entities {

	private Body leftWheel;
	private Body rightWheel;
	private Body box;
	private Body[] belt;

	private float xPos;
	private float yPos;

//////////Info for sprite sheet/////////////////////
	public static final int conveyorBeltWidth = 45;
	public static final int conveyorBeltHeight = 45;
	public static final int Y_LENGTH = 24;
	public static final int X_LENGTH = 24;
	public int bbX = 0;
	public int bbY = 0;
	////////////////////////////////////////////////////

	public int curState = 0;
	public int dir = 0;
	public int count = 0;
	private float speed;

	public ConveyorBelt() {
		super(EType.conveyorBelt);
		initLeftWheel();
		initRightWheel();
		initbox();
		initBelt();
		speed = 1f;
		setImagePath("Images/ConveyorBelt.gif");
	}
	
	public ConveyorBelt(float newSpeed) {
		super(EType.conveyorBelt);
		initLeftWheel();
		initRightWheel();
		initbox();
		initBelt();
		speed = newSpeed;
		setImagePath("Images/ConveyorBelt.gif");
	}

	private void initBelt() {
		belt = new Body[20];
		for (int i = 0; i < 10; i++) {
			belt[i] = new Body(new Circle(1f), 2);
		}

	}

	private void initbox() {
		box = new StaticBody("Box", new Box(100f, 20f));
		box.setDamping(.001f);
		box.setCanRest(true);
		box.setRestitution(1.0f);
	}

	private void initLeftWheel() {
		leftWheel = new StaticBody("leftWheel", new Circle(10f));
		leftWheel.setRestitution(1.0f);
		leftWheel.setDamping(.001f);
		leftWheel.setCanRest(true);
	}

	private void initRightWheel() {
		rightWheel = new StaticBody("rightWheel", new Circle(10f));
		rightWheel.setDamping(.001f);
		rightWheel.setCanRest(true);
		rightWheel.setRestitution(1.0f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		this.xPos = x;
		this.yPos = y;

		leftWheel.setPosition(x - 50, y);
		rightWheel.setPosition(x + 50, y);
		box.setPosition(x, y);

		for (int i = 0; i < 10; i++) {
			belt[i].setPosition(x - 50 + 10 * i, y - 12);
		}

		world.add(rightWheel);
		world.add(leftWheel);
		world.add(box);

		for (int i = 0; i < 10; i++) {
			world.add(belt[i]);
		}
		this.setImageLocations(x, y);
	}

	@Override
	public float getX() {
		return this.xPos;
	}

	@Override
	public float getY() {
		return this.yPos;
	}

	@Override
	public void removeObj(World world) {
		world.remove(rightWheel);
		world.remove(leftWheel);
		world.remove(box);

		for (int i = 0; i < 10; i++) {
			world.remove(belt[i]);
		}
	}

	@Override
	public void upDate() {
		for (int i = 0; i < 10; i++) {
			belt[i].setForce(speed, 0);
			if (belt[i].getPosition().getX() > this.xPos + 55
					|| belt[i].getPosition().getX() < this.xPos - 55
					|| belt[i].getPosition().getY() > this.yPos - 10
					|| belt[i].getPosition().getY() < this.yPos - 12) {
				belt[i].setPosition(this.xPos - 45, this.yPos - 11);
			}

		}
		setImageLocations();
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
		return null;
	}

	@Override
	public int getSpriteHeight() {

		return conveyorBeltHeight;
	}

	@Override
	public int getSpriteWidth() {

		return conveyorBeltWidth;
	}

	@Override
	public void setSprite() {
		// TODO Auto-generated method stub
		
	}

}
