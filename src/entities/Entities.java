package entities;

import engine.raw.Body;

public abstract class  Entities {
	
	/** Middle coordinates */
	private int x, y;

	/** Upper and lower X,Y coordinates  */
	private int upperX, lowerX, upperY, lowerY;

	/** Object's state  */
	private int state;

	/** Object's length and width  */
	private int length, height;

	/** Object name / type  */
	private String objType;

	/**Image for specific object type*/
	private String imagePath;

	// ///// METHODS /////////////////////////////////////////////////

	public Entities(String objType){
		state = 0;

		
	}
	
	/**
	 * @param x
	 * @param y
	 * Sets the location for the image to be printed. Based on x and y coordinates in the center of the object.
	 */
	public void setImageLocations(int x, int y) {
		this.x = x;
		this.y = y;

		this.upperX = this.x - (getLength() / 2);
		this.upperY = this.y - (getHeight() / 2);
		this.lowerX = this.x + (getLength() / 2);
		this.lowerY = this.x + (getHeight() / 2);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the upperX
	 */
	public int getUpperX() {
		return upperX;
	}

	
	/**
	 * @return the lowerX
	 */
	public int getLowerX() {
		return lowerX;
	}

	
	/**
	 * @return the upperY
	 */
	public int getUpperY() {
		return upperY;
	}

	
	/**
	 * @return the lowerY
	 */
	public int getLowerY() {
		return lowerY;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the width
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the objType
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * @param objType
	 *            the objType to set
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the image
	 */
	public String getImagePath() {
		return imagePath;
	}

}
