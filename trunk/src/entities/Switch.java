package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class Switch extends Entities {

	private Body s;
	private static BufferedImage[] staticSprites;

	public Switch() {
		super(EType.Switch);
		if (staticSprites == null)
			staticSprites = utils.splitImage(utils
					.loadImage("Images/PingPongBall.gif"), 1, 10);
		sprite = staticSprites;

		s = new StaticBody(toString(), new Box(10f, 20f));
		s.setCanRest(true);
		s.setGravityEffected(false);
	}

	@Override
	public void addObj(World world, float x, float y) {
		s.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(s);
	}

	public void upDate() {
		super.upDate();
		if(s.getGravityEffected()){
			state = 1;
		} else {
			state = 0;
		}
	}
	
	@Override
	public float getX() {
		return s.getPosition().getX();
	}

	@Override
	public float getY() {
		return s.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {
		return s.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {
		world.remove(s);
	}
}
