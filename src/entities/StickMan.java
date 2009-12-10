package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.joint.BasicJoint;
import engine.joint.FixedJoint;
import engine.joint.Joint;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.vector.Vector;
import enums.EType;

public class StickMan extends Entities {

	private Body man;
	private Body bottom;
	private Body Lside;
	private Body Rside;
	private Joint[] joints;
	private int count = 0;

	private static BufferedImage[] staticSprites;

	public StickMan() {
		super(EType.stickMan);
		if (staticSprites == null)
			staticSprites = utils.splitImage(utils
					.loadImage("Images/stickManSpriteSheet.gif"), 8, 1);
		sprite = staticSprites;

		init();
	}

	private void init() {
		joints = new FixedJoint[5];
		man = new Body("man", new Box(50, 76), 10f);
		Rside = new Body("man", new Box(2, 72 / 2), .1f);
		bottom = new Body("man", new Box(48, 72 / 2), 10f);
		Lside = new Body("man", new Box(2, 72 / 2), .10f);
		//joints[0] = new FixedJoint(Lside, Rside);
		joints[1] = new FixedJoint(Lside, bottom);
		joints[2] = new FixedJoint(Rside, bottom);
		bottom.adjustVelocity(new Vector(-10.0f, 0f));
		Rside.addExcludedBody(Lside);
		Rside.addExcludedBody(bottom);
		Lside.addExcludedBody(Rside);
		Lside.addExcludedBody(bottom);
		bottom.addExcludedBody(Lside);
		bottom.addExcludedBody(Rside);
		
	}

	@Override
	public void addObj(World world, float x, float y) {
		Rside.setPosition(x-24, y - 40);
		bottom.setPosition(x  , y);
		Lside.setPosition(x+24 + 30, y-40);
		// world.add(man);
		world.add(Rside);
		world.add(bottom);
		world.add(Lside);
		for (int i = 1; i < 3; i++)
			world.add(joints[i]);

	}

	@Override
	public float getX() {
		return man.getPosition().getX();
	}

	@Override
	public float getY() {
		return man.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(man);

	}

	@Override
	public int gettouchingBodies() {
		return this.man.getTouchingCount();
	}

	@Override
	public void upDate() {
		super.upDate();
		bottom.adjustVelocity(new Vector(-1.0f, 0f));
		if(count > 20){
			state++;
			if (state > 4)
				state = 0;
			count = 0;
		}
		count++;
		

	
	}

}
