package entities;

import engine.World;
import engine.body.Body;
import engine.shapes.Circle;

public class BasketBall extends Ball {

	public BasketBall(String objType) {
		super("BasketBall");
	}
	
	private Body bBall;

	protected void init() {
		
		bBall = new Body("BasketBall", new Circle(20.0f), 1);
		bBall.setRestitution(1.0f);
		
		setImagePath("Images/bball.jpg");
	}
	
	public void addObj(World world){
		world.add(bBall);  //I think this needs to be done in Model?? //jeff- no.
	}
}
