package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.World;
import engine.shapes.*;
import enums.EType;

public class BasketBall extends Entities {

	private Body bBall;
	public int bbX = 0;
	public int bbY = 0;

	public BasketBall() {
		super(EType.basketball, "Images/basketball.gif", 24, 24, 45, 45);
		bBall = new Body("BasketBall", new Circle(20.0f), 2.0f);
		bBall.setRestitution(1.0f);
		bBall.setDamping(.001f);
		bBall.setCanRest(true);
	}

	public int getSpriteX() {
		return bbX;
	}

	public int getSpriteY() {
		return bbY;
	}

	public void setSprite() {

		// not needed
	}

	@Override
	public float getX() {
		return bBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return bBall.getPosition().getY();
	}

	@Override
	public void addObj(World world, float x, float y) {

		bBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(bBall);

	}

	@Override
	public void removeObj(World world) {
		world.remove(bBall);
	}

	@Override
	public int gettouchingBodies() {
		return bBall.getTouchingCount();
	}

	public BufferedImage getSpriteImage() {
		float rotation = bBall.getRotation();
		rotation = rotation % (2f * (new Float(Math.PI)));

		BufferedImage temp = null;
		try {
			temp = ImageIO.read(new File(getImagePath()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		temp = utils.rotate(temp, rotation);
		temp = utils.makeColorTransparent(temp, Color.black);
		return temp;

	}
}
