package entities;

import java.awt.image.BufferedImage;
import java.util.Observable;
import engine.World;
import enums.EType;

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
	protected int state;
	/** Object name / type */
	private EType objType;
	/** Image for specific object type */
	private String imagePath;
	protected BufferedImage[] sprite;	
	protected int Y_LENGTH;
	protected int X_LENGTH;


	/**Basic constructor. Must remember to set all the variables on its own
	 * @param objType The Object Type
	 */
	public Entities(EType objType) {
		this.objType=objType;
		state = 0;
	}
	
	

	/**
	 * @param objType The Object Type
	 * @param spritePath Path for the sprite sheet
	 * @param imagePath Path for the "image"
	 * @param x_LENGTH X_LENGTH of the entity
	 * @param y_LENGTH Y_LENGTH of the entity
	 * @param spriteWidth SpriteWidth of the entity
	 * @param spriteHeight SpriteHeight of the entity
	 */
	public Entities(EType objType, String imagePath, int x_LENGTH,
			int y_LENGTH) {
		this.objType=objType;
		this.imagePath = imagePath;
		this.Y_LENGTH = y_LENGTH;
		this.X_LENGTH = x_LENGTH;
		state = 0;
	}



	protected void setImageLocations() {
		setImageLocations(0,0);
	}

	/**
	 * Sets the location for the image to be printed. Based on x and
	 *            y coordinates in the center of the object.
	 * @param x
	 * @param y
	 */
	protected void setImageLocations(float x, float y) {

		this.upperX = (this.getX() - (getXLength() / 2));
		this.upperY = (this.getY() - (getYLength() / 2));
		this.lowerX = (this.getX() + (getXLength() / 2));
		this.lowerY = (this.getY() + (getYLength() / 2));
		
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
	 * @return the objType
	 */
	public EType getObjType() {
		return objType;
	}

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

	public void upDate(){
		setImageLocations();
	}
	
	/**
	 * 
	 * @return The current sprite.
	 */
	public BufferedImage getSpriteImage() {
		
		return sprite[state];
	}
	
	public int getXLength(){
		return this.X_LENGTH;
	}
	
	public int getYLength(){
		return this.Y_LENGTH;
	}
	
	public String toString(){
		return this.objType.toString();
	}
	
	public abstract int getSpriteX();
	
	public abstract int getSpriteY();

	public abstract void addObj(World world, float x, float y);

	public abstract void removeObj(World world);
	
	public abstract int gettouchingBodies();
	
	public abstract float getX();

	public abstract float getY();

}
