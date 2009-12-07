package engine.collision;

/**
 * A description of class that can recieve notifications of collisions within a
 * CollisionSpace
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public interface CollisionListener {
	/**
	 * Notification that a collision occured
	 * 
	 * @param event
	 *            The describing the collision
	 */
	public void collisionOccured(CollisionEvent event);
}
