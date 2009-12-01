package playSounds;


import engine.collision.CollisionEvent;
import engine.collision.CollisionListener;
import enums.EType;

public class CollisionListenerImpl implements CollisionListener{

	public void collisionOccured(CollisionEvent event) {
		String baseDir = System.getProperty("user.dir")	+ "/sounds/";
		PlaySound mySoundPlayer = new PlaySound();
		
		if (event.getBodyA().getName().equals(EType.pingPongBall) || 
			event.getBodyB().getName().equals(EType.pingPongBall))
		mySoundPlayer.play(baseDir + "PingPong.wav");
		

	}

}
