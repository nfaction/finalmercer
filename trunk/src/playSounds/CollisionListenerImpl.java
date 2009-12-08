package playSounds;

import engine.collision.CollisionEvent;
import engine.collision.CollisionListener;


public class CollisionListenerImpl implements CollisionListener{
	
	private PlaySound mySoundPlayer = new PlaySound();


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
				event.getBodyB().getName().equals("Pin")){
			mySoundPlayer.play(baseDir + "PoppedBalloon.wav");
			//world.remove(event.getBodyA());
			
		}
		
		if (event.getBodyA().getName().equals("Pin") && 
				event.getBodyB().getName().equals("Balloon")){
			mySoundPlayer.play(baseDir + "PoppedBalloon.wav");
			//world.remove(event.getBodyB());
			
		}
		
		
		
		
		
		
		
			
		
		
		

	}

}
