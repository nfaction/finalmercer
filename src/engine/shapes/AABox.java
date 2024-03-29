package engine.shapes;

/**
 * An axis oriented used for shape bounds
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class AABox {
	private float width;
	private float height;
	private float offsetx;
	private float offsety;
	
	/**
	 * Create a new bounding box
	 * 
	 * @param width The width of the box
	 * @param height The hieght of the box
	 */
	public AABox(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Create a new AABox
	 * 
	 * @param offsetx The x offset to the body's position
	 * @param offsety The y offset to the body's position
	 * @param width The width of the box
	 * @param height The hieght of the box
	 */
	public AABox(float offsetx, float offsety, float width, float height) {
		this.width = width;
		this.height = height;
		this.offsetx = offsetx;
		this.offsety = offsety;
	}
	
	/**
	 * Get the width of the box
	 * 
	 * @return The width of the box
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the box
	 * 
	 * @return The height of the box
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Get the x offset to the body's position of this bounds
	 * 
	 * @return The x offset to the body's position of this bounds
	 */ 
	public float getOffsetX() {
		return offsetx;
	}
	
	/**
	 * Get the y offset to the body's position of this bounds
	 * 
	 * @return The y offset to this body's position of this bounds
	 */
	public float getOffsetY() {
		return offsety;
	}
	
	/**
	 * Check if this box touches another
	 * 
	 * @param x The x position of this box
	 * @param y The y position of this box
	 * @param other The other box to check against  
	 * @param otherx The other box's x position
	 * @param othery The other box's y position
	 * @return True if the boxes touches
	 */
	public boolean touches(float x, float y, AABox other, float otherx, float othery) {
		float totalWidth = (other.width + width) / 2;
		float totalHeight = (other.height + height) / 2;
		
		float dx = Math.abs((x + offsetx) - (otherx + other.offsetx));
		float dy = Math.abs((y + offsety) - (othery + other.offsety));
		
		return (totalWidth > dx) && (totalHeight > dy);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[AABox "+width+"x"+height+"]";
	}
}
