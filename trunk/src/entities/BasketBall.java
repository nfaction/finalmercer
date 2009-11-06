package entities;

import engine.raw.Body;
import engine.raw.World;
import engine.raw.shapes.Circle;

public class BasketBall extends Ball {

	protected void init(World world) {
		
		Body bowlingBall = new Body("BasketBall", new Circle(20.0f), 1);
		bowlingBall.setRestitution(1.0f);
		
		world.add(bowlingBall);
		
		setHeight(40);
		setLength(40);
		setImage("Images/bball.jpg");
	}
}
