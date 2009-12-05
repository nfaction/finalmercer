package entities;

import java.util.Observable;

import engine.World;
import enums.EType;

public abstract class Entities extends Observable {
	/**  X coordinate */
	private float x;
	/** Y coordinate */
	private float y;
	/** Previous Upper X coordinate */
	private float prevUpperX;
	/** Upper X coordinate */
	private float upperX;
	/** Lower X coordinate */
	private float lowerX;
	/** Upper Y coordinate */
	private float upperY;
	/** Lower Y coordinate */
	private float lowerY;

	private int imageX;
	private int imageY;
	
	
	/** Object's state */
	private int state;
	/** Object's length */
	private int length;
	/** Object's height */
	private int height;
	/** Object name / type */
	private EType objType;
	/** Image for specific object type */
	private String imagePath;

	// ///// METHODS /////////////////////////////////////////////////

	public Entities(EType objType) {
		this.objType=objType;
		state = 0;
	}
	
	/**
	 * @return 1 if rolling right -1 left
	 */
	public int rollDirection(){
		if(upperX - prevUpperX > 0)
			return 1;
		else
			return -1;
		
		
		
	}
	
	/**
	 * Sets the location for the image to be printed. Based on x and
	 *            y coordinates in the center of the object.
	 * @param x
	 * @param y
	 */
	protected void setImageLocations(float x, float y) {
		//this.x = x;
		//this.y = y;
		
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ this.getX() + "BBBBBBBBBBBBBBBBBBBBBB");
		this.prevUpperX = upperX;
		this.upperX = this.getX() - (getLength() / 2);
		this.upperY = this.getY() - (getHeight() / 2);
		this.lowerX = this.getX() + (getLength() / 2);
		this.lowerY = this.getY() + (getHeight() / 2);
	}
	
	protected void setImageLocations() {
		setImageLocations(0,0);
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
	 * @return the x
	 */
	public void setImageX(int x){
		this.imageX = x;
	}

	/**
	 * @return the y
	 */
	public void setImageY(int y){
		this.imageY = y;
	}

	/**
	 * @return the x
	 */
	public int getImageX(){
		return imageX;
	}

	/**
	 * @return the y
	 */
	public int getImageY(){
		return imageY;
	}
	
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
	public EType getObjType() {
		return objType;
	}

	public abstract int getSpriteX();
	
	public abstract int getSpriteY();
	
	/**
	 * @param objType
	 *            the objType to set
	 */
	public void setObjType(EType objType) {
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
	
	public abstract void upDate();

	public abstract void addObj(World world, float x, float y);

	public abstract void removeObj(World world);

}
