package playSounds;


import engine.collision.CollisionEvent;
import engine.collision.CollisionListener;
import enums.EType;

public class CollisionListenerImpl implements CollisionListener{
	
	public PlaySound mySoundPlayer = new PlaySound();

	public void collisionOccured(CollisionEvent event) {
		String baseDir = System.getProperty("user.dir")	+ "/sounds/";
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

		
		if (event.getBodyA().getName().equals("BasketBall") || 
			event.getBodyB().getName().equals("BasketBall"))
		mySoundPlayer.play(baseDir + "Tap.wav");
		
		if (event.getBodyA().getName().equals("BowlingBall") || 
				event.getBodyB().getName().equals("BowlingBall"))
			mySoundPlayer.play(baseDir + "BowlingBall.wav");
		
		if (event.getBodyA().getName().equals("Domino") || 
				event.getBodyB().getName().equals("Domino"))
			mySoundPlayer.play(baseDir + "TicTac.wav");
		
		if (event.getBodyA().getName().equals("PingPongBall") || 
				event.getBodyB().getName().equals("PingPongBall"))
			mySoundPlayer.play(baseDir + "Tink.wav");
		
		
		
		
		
			
		
		
		

	}

}
