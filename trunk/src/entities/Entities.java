package entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

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
	private String spritePath;	
	protected int Y_LENGTH;
	protected int X_LENGTH;
	
	


	// ///// METHODS /////////////////////////////////////////////////

	public Entities(EType objType) {
		this.objType=objType;
		state = 0;
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
	
	protected void setImageLocations() {
		setImageLocations(0,0);
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
	public void setSpritePath(String imagePath) {
		this.spritePath = imagePath;
	}
	
	public String getSpritePath() {
		return spritePath;
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
		setSprite();
		setImageLocations();
	}
	
	/**
	 * This method will get the subimage which represents the card 'c'.
	 * 
	 * @param c
	 *            , the card for which we want an image
	 * @return The image for the card c
	 */
	public BufferedImage getSpriteImage() {

		BufferedImage sprite;
		int x = getSpriteX();
		int y = getSpriteY();
		int w = getXLength() * 2;
		int h = getYLength() * 2;
		String imageLocation = spritePath;
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(new File(imageLocation));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		sprite = temp.getSubimage(x, y, w, h);

		return sprite;
	}
	
	public int getXLength(){
		return this.X_LENGTH;
	}
	
	public int getYLength(){
		return this.Y_LENGTH;
	}
	
	public abstract int getSpriteWidth();
	
	public abstract int getSpriteHeight();
	
	public abstract int getSpriteX();
	
	public abstract int getSpriteY();
	
	public abstract String toString();
	
	public abstract void setSprite();

	public abstract void addObj(World world, float x, float y);

	public abstract void removeObj(World world);
	
	public abstract int gettouchingBodies();
	
	public abstract float getX();

	public abstract float getY();

}
