package playSounds;


import engine.collision.CollisionEvent;
import engine.collision.CollisionListener;
import enums.EType;

public class CollisionListenerImpl implements CollisionListener{
	
	public PlaySound mySoundPlayer = new PlaySound();

	public void collisionOccured(CollisionEvent event) {
		String baseDir = System.getProperty("user.dir")	+ "/sounds/";

		
		//if (event.getBodyA().getName().equals(EType.pingPongBall) || 
		//	event.getBodyB().getName().equals(EType.pingPongBall))
		mySoundPlayer.play(baseDir + "PingPong.wav");
		

	}

}
