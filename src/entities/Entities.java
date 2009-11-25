package entities;

import java.util.Observable;

import engine.World;
import engine.body.Body;

public abstract class Entities extends Observable {
	/** Upper X coordinate */
	private float upperX;
	/** Lower X coordinate */
	private float lowerX;
	/** Upper Y coordinate */
	private float upperY;
	/** Lower Y coordinate */
	private float lowerY;

	/** Object's state */
	private int state;

	/** Object's length and width */
	private int length, height;

	/** Object name / type */
	private String objType;

	/** Image for specific object type */
	private String imagePath;

	// ///// METHODS /////////////////////////////////////////////////

	public Entities(String objType) {
		state = 0;
	}

	/**
	 * @param x
	 * @param y
	 *            Sets the location for the image to be printed. Based on x and
	 *            y coordinates in the center of the object.
	 */
	public void setImageLocations(float x, float y) {

		this.upperX = this.getX() - (getLength() / 2);
		this.upperY = this.getY() - (getHeight() / 2);
		this.lowerX = this.getX() + (getLength() / 2);
		this.lowerY = this.getY() + (getHeight() / 2);
	}

	/**
	 * @return the x
	 */
	public abstract float getX();

	/**
	 * @return the y
	 */
	public abstract float getY();

	/**
	 * @return the upperX
	 */
	public float getUpperX() {
		return upperX;
	}

	/**
	 * @return the lowerX
	 */
	public float getLowerX() {
		return lowerX;
	}

	/**
	 * @return the upperY
	 */
	public float getUpperY() {
		return upperY;
	}

	/**
	 * @return the lowerY
	 */
	public float getLowerY() {
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

	public abstract void addObj(World world, float x, float y);
	
	public abstract void removeObj(World world);


}
