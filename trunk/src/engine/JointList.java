package engine;

import java.util.ArrayList;

import engine.joint.Joint;

/**
 * A typed list of type <code>Joint</code>
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public class JointList {
	/** The elements in the list */
	private ArrayList<Joint> elements = new ArrayList<Joint>();

	/**
	 * Create an empty list
	 */
	public JointList() {

	}

	/**
	 * Check if a given joint in container within this list
	 * 
	 * @param joint
	 *            The joint to check for
	 * @return True if the joint is contained in this list
	 */
	public boolean contains(Joint joint) {
		return elements.contains(joint);
	}

	/**
	 * Add a joint to the list
	 * 
	 * @param joint
	 *            The joint to add
	 */
	public void add(Joint joint) {
		elements.add(joint);
	}

	/**
	 * Get the size of the list
	 * 
	 * @return The size of the list
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Remove a joint from the list
	 * 
	 * @param joint
	 *            The joint to remove
	 */
	public void remove(Joint joint) {
		elements.remove(joint);
	}

	/**
	 * Get a joint from the list
	 * 
	 * @param i
	 *            The index of the joint to retrieve
	 * @return The joint requested
	 */
	public Joint get(int i) {
		return elements.get(i);
	}

	/**
	 * Empty the list
	 */
	public void clear() {
		elements.clear();
	}
}
