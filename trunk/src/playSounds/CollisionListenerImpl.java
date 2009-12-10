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
		
		if (event.getBodyA().getName().equals("tack") && 
				event.getBodyB().getName().equals("Balloon")){
			mySoundPlayer.play(baseDir + "PingPong.wav");
			event.getBodyB().setMoveable(false);
		}
	}

}
