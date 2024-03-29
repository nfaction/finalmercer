package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
	private float speed;
	private int spritePos;
	private int count;

	private static BufferedImage[] staticSprites;

	public ConveyorBelt() {
		super(EType.conveyorBelt);
		if (staticSprites == null)
			staticSprites = utils.splitImage(utils
					.loadImage("Images/conveyorBeltSpriteSheet.png"), 1, 4);
		sprite = new BufferedImage[7];
		sprite[0] = utils.makeColorTransparent(staticSprites[0], Color.black);
		sprite[1] = utils.makeColorTransparent(staticSprites[1], Color.black);
		sprite[2] = utils.makeColorTransparent(staticSprites[2], Color.black);
		sprite[3] = utils.makeColorTransparent(staticSprites[3], Color.black);
		sprite[4] = utils.makeColorTransparent(utils
				.horizontalflip(staticSprites[1]), Color.black);
		sprite[5] = utils.makeColorTransparent(utils
				.horizontalflip(staticSprites[2]), Color.black);
		sprite[6] = utils.makeColorTransparent(utils
				.horizontalflip(staticSprites[3]), Color.black);

		this.spritePos = 0;
		initLeftWheel();
		initRightWheel();
		initbox();
		initBelt();
		speed = 3f;
		setImagePath("Images/ConveyorBelt.gif");
		count = 0;
		state = 1;
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
		for (int i = 0; i < 20; i++) {
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

		for (int i = 0; i < 20; i++) {
			belt[i].setPosition(x - 50 + 5 * i, y - 12);
		}

		world.add(rightWheel);
		world.add(leftWheel);
		world.add(box);

		for (int i = 0; i < 20; i++) {
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
		super.upDate();
		if (state >= 1 && 3 >= state) {
			for (int i = 0; i < 20; i++) {
				belt[i].setForce(speed, 0);
				if (belt[i].getPosition().getX() > this.xPos + 55
						|| belt[i].getPosition().getX() < this.xPos - 55
						|| belt[i].getPosition().getY() > this.yPos - 10
						|| belt[i].getPosition().getY() < this.yPos - 12) {
					belt[i].setPosition(this.xPos - 45, this.yPos - 11);
				}

			}

		} else if (state >= 4 && 6 >= state) {
			for (int i = 0; i < 20; i++) {
				belt[i].setForce(-speed, 0);
				if (belt[i].getPosition().getX() > this.xPos + 55
						|| belt[i].getPosition().getX() < this.xPos - 55
						|| belt[i].getPosition().getY() > this.yPos - 10
						|| belt[i].getPosition().getY() < this.yPos - 12) {
					belt[i].setPosition(this.xPos + 45, this.yPos - 11);
				}
			}

		} else {

		}

	}

	@Override
	public int gettouchingBodies() {
		int i = 0;

		i += this.leftWheel.getTouchingCount() - 1;
		i += this.rightWheel.getTouchingCount() - 1;
		i += this.box.getTouchingCount() - 2;
		return i;
	}

	public BufferedImage getSpriteImage() {
		if (state >= 1 && 3 >= state) {
			if (count > 20) {
				spritePos++;
				if (spritePos > 3)
					spritePos = 1;
				count = 0;
			}
			count++;
			return sprite[this.spritePos];
		} else if (state >= 4 && 6 >= state) {
			if(spritePos<4)
				spritePos = 4;
			if (count > 20) {
				spritePos++;
				if (spritePos > 6)
					spritePos = 4;
				count = 0;
			}
			count++;
			return sprite[this.spritePos];
		} else {
			return sprite[0];
		}
	}
}
