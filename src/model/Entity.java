package model;

public class Entity {
	
	private String eType;
	private int x;
	private int y;
	
	public Entity(String eType,int x, int y){
	this.eType = 	eType;
	this.x = x;
	this.y=y;
		
	}
	/**
	 * @return the etype
	 */

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}


}
