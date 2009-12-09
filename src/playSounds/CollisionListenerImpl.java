package playSounds;

import model.Model;
import engine.World;
import engine.collision.CollisionEvent;
import engine.collision.CollisionListener;
import graphics.Data;


public class CollisionListenerImpl implements CollisionListener{
	
	private PlaySound mySoundPlayer = new PlaySound();
	private Model model = Model.getObj();
 


	public void collisionOccured(CollisionEvent event) {
		String baseDir = System.getProperty("user.dir")	+ "/sounds/";
	
		
		
		if (event.getBodyA().getName().equals("BasketBall") || 
			event.getBodyB().getName().equals("BasketBall"))
		mySoundPlayer.play(baseDir + "Tap.wav");
		
		if (event.getBodyA().getName().equals("BowlingBall") || 
				event.getBodyB().getName().equals("BowlingBall"))
			mySoundPlayer.play(baseDir + "BowlingBall.wav");
		
		if (event.getBodyA().getName().equals("PingPongBall") || 
				event.getBodyB().getName().equals("PingPongBall"))
			mySoundPlayer.play(baseDir + "Tink.wav");
		
		if (event.getBodyA().getName().equals("Balloon") && 
				event.getBodyB().getName().equals("tack")){
			System.out.println("POP");
			mySoundPlayer.play(baseDir + "PoppedBalloon.wav");
			model.getWorld().remove(event.getBodyA());
			
		}
		
		if (event.getBodyA().getName().equals("tack") && 
				event.getBodyB().getName().equals("Balloon")){
			System.out.println("POP");
			mySoundPlayer.play(baseDir + "PoppedBaBlloon.wav");
			model.getWorld().remove(event.getBodyB());
			
		}
		
		
		
		
		
		
		
			
		
		
		

	}

}
