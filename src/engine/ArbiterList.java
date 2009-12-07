package engine;

import java.util.ArrayList;

/**
 * A typed list of <code>Arbiter</code>
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public class ArbiterList {
	/** The elements in the list */
	private ArrayList<Arbiter> elements = new ArrayList<Arbiter>();

	/**
	 * Create an empty list
	 */
	public ArbiterList() {

	}

	/**
	 * Add an arbiter to the list
	 * 
	 * @param arbiter
	 *            The arbiter to add
	 */
	public void add(Arbiter arbiter) {
		elements.add(arbiter);
	}

	/**
	 * Get the size of the list
	 * 
	 * @return The number of elements in the list
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Return the index of a particular arbiter in the list
	 * 
	 * @param arbiter
	 *            The arbiter to search for
	 * @return The index of -1 if not found
	 */
	public int indexOf(Arbiter arbiter) {
		return elements.indexOf(arbiter);
	}

	/**
	 * Remove an abiter from the list
	 * 
	 * @param arbiter
	 *            The arbiter ot remove from the list
	 */
	public void remove(Arbiter arbiter) {
		if (!elements.contains(arbiter)) {
			return;
		}
		elements.set(elements.indexOf(arbiter), elements
				.get(elements.size() - 1));
		elements.remove(elements.size() - 1);
	}

	/**
	 * Get an arbiter at a specified index
	 * 
	 * @param i
	 *            The index of arbiter to retrieve
	 * @return The arbiter at the specified index
	 */
	public Arbiter get(int i) {
		return elements.get(i);
	}

	/**
	 * Remove all the elements from the list
	 */
	public void clear() {
		elements.clear();
	}

	/**
	 * Check if an arbiter is contained within a list
	 * 
	 * @param arb
	 *            The arbiter to check for
	 * @return True if the arbiter is in the list
	 */
	public boolean contains(Arbiter arb) {
		return elements.contains(arb);
	}
}
