package engine.collision;

import java.util.ArrayList;

import engine.Arbiter;
import engine.ArbiterList;
import engine.BodyList;
import engine.Strategy;
import engine.collide.Contact;
import engine.shapes.Body;
import engine.vector.Vector;


/**
 * A space that will resolve collisions and report them to registered 
 * listeners.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public class CollisionSpace implements CollisionContext {
	protected BodyList bodies = new BodyList(); 
	protected ArbiterList arbiters = new ArbiterList(); 
	protected Strategy collisionStrategy;
	protected ArrayList<CollisionListener> listeners = new ArrayList<CollisionListener>();
	protected float totalTime;

//	/**
//	 * Remove a listener from the space
//	 * 
//	 * @param listener The listener to be removed
//	 */
//	public void removeListener(CollisionListener listener) {
//		listeners.remove(listener);
//	}
	/**
	 * Create a new collision space based on a given strategy for 
	 * partioning the space
	 * 
	 * @param strategy The strategy to use to partion the collision space
	 */
	public CollisionSpace(Strategy strategy) {
		this.collisionStrategy = strategy;
	}
	
	/**
	 * Add a listener to be notified of collisions
	 * 
	 * @param listener The listener to be notified of collisions
	 */
	public void addListener(CollisionListener listener) {
		listeners.add(listener);
	}

	
	/**
	 * Cause collision to occur and be reported.
	 * 
	 * @param dt The amount of time since last collision. This may be used
	 * for swept collision in some future implementation
	 */
	public void collide(float dt) {
		totalTime += dt;
		collisionStrategy.collideBodies(this, bodies, dt);
	}

	/**
	 * Remove all the elements from this space
	 */
	public void clear() {
		bodies.clear();
		arbiters.clear();
	}
	
	/**
	 * Add a body to the simulation
	 * 
	 * @param body The body to be added
	 */
	public void add(Body body) {
		body.setAdded(true);
		bodies.add(body);
	}
	
	/**
	 * Remove a body from the simulation
	 * 
	 * @param body The body to be removed
	 */
	public void remove(Body body) {
		body.setAdded(false);
		bodies.remove(body);
	}
	
	/**
	 * Retrieve a immutable list of bodies in the simulation
	 * 
	 * @return The list of bodies
	 */
	public BodyList getBodies() {
		return bodies;
	}
	
	/**
	 * Notify listeners of a collision
	 * 
	 * @param body1 The first body in the collision
	 * @param body2 The second body in the collision
	 * @param point The point of collision (not always perfect - accepts penetration)
	 * @param normal The normal of collision
	 * @param depth The penetration of of the contact
	 */
	private void notifyCollision(Body body1, Body body2, Vector point, Vector normal, float depth) {
		if (listeners.size() == 0) {
			return;
		}
		
		CollisionEvent event = new CollisionEvent(totalTime,body1,body2,point,normal,depth);
	
		for (int i=0;i<listeners.size();i++) {
			listeners.get(i).collisionOccured(event);
		}
	}
	
	/**
	 * @see engine.collision.CollisionContext#resolve(engine.BodyList, float)
	 */
	public void resolve(BodyList bodyList, float dt) {
		for (int i = 0; i < bodyList.size(); ++i)
		{
			Body bi = bodyList.get(i);
			if (bi.disabled()) {
				continue;
			}
			
			for (int j = i+1; j < bodyList.size(); ++j)
			{
				Body bj = bodyList.get(j);
				if (bj.disabled()) {
					continue;
				}
				if ((bi.getBitmask() & bj.getBitmask()) != 0) {
					continue;
				}
				if (bi.getExcludedList().contains(bj)) {
					continue;
				}
				if (bi.getInvMass() == 0.0f && bj.getInvMass() == 0.0f) {
					continue;
				}
				if (!bi.getShape().getBounds().touches(bi.getPosition().getX(), 
													   bi.getPosition().getY(), 
													   bj.getShape().getBounds(), 
													   bj.getPosition().getX(), 
													   bj.getPosition().getY())) {

					arbiters.remove(new Arbiter(bi,bj));
					continue;
				}
				
				Arbiter newArb = new Arbiter(bi, bj);
				newArb.collide(dt);
				
				if (newArb.getNumContacts() > 0)
				{
					bi.collided(bj);
					bj.collided(bi);
					
					if (arbiters.contains(newArb)) {
						int index = arbiters.indexOf(newArb);
						Arbiter arb = arbiters.get(index);
						arb.update(newArb.getContacts(), newArb.getNumContacts());
					} else {
						Contact c = newArb.getContact(0);
						
						notifyCollision(bi,bj,c.getPosition(),c.getNormal(),c.getSeparation());
						arbiters.add(newArb);
						newArb.init();
					}
				}
				else
				{
					arbiters.remove(newArb);
				}
			}
		}
	}
}
