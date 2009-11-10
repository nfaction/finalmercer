package entities;

import engine.World;
import engine.body.Body;
import engine.shapes.Circle;

public class BasketBall extends Ball {

	public BasketBall(String objType) {
		super("BasketBall");
		// TODO Auto-generated constructor stub
	}

	protected void init(World world) {
		
		Body bBall = new Body("BasketBall", new Circle(20.0f), 1);
		bBall.setRestitution(1.0f);
		
		world.add(bBall);  //I think this needs to be done in Model??
		setImagePath("Images/bball.jpg");
	}
}
