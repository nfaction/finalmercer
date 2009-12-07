package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import entities.BasketBall;
import entities.LeftRamp;
import entities.Entities;
import enums.EType;

public class SpriteSheet {
	private BufferedImage sprite;
	private BufferedImage balloonSprite;
	private BufferedImage basketballSprite;
	private BufferedImage beltSprite;
	private BufferedImage bowlingballSprite;
	private BufferedImage bucketSprite;
	private BufferedImage candleSprite;
	private BufferedImage conveyorbeltSprite;
	private BufferedImage dominoSprite;
	private BufferedImage gearSprite;
	private BufferedImage leftrampSprite;
	private BufferedImage lightSprite;
	private BufferedImage pinSprite;
	private BufferedImage pingpongballSprite;
	private BufferedImage rightrampSprite;
	private int count = 0;
	
	
	public SpriteSheet()
	{
		// Use ImageIO to read in the card sheet
		try
		{
			balloonSprite = ImageIO.read(new File("Images/BasketBallSpriteSheet.png"));
			basketballSprite = ImageIO.read(new File("Images/BasketBallSpriteSheet.png"));
			bowlingballSprite = ImageIO.read(new File("Images/BasketBallSpriteSheet.png"));
			leftrampSprite = ImageIO.read(new File("Images/LeftRampSprite.gif"));
			//Add other objects to read in.
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This method will get the subimage which represents the card 'c'.
	 * 
	 * @param c
	 *            , the card for which we want an image
	 * @return The image for the card c
	 */
	public BufferedImage getStateImage(Entities e)
	{
		if(count > 10){
			count = 0;
		}
		if(e.toString().equalsIgnoreCase("Basketball")){
			System.out.println("Sprite image will be set");
			sprite = basketballSprite.getSubimage(e.getSpriteX(count), e.getSpriteY(),
				BasketBall.X_LENGTH * 2, BasketBall.Y_LENGTH * 2);
		}
		else if(e.toString().equalsIgnoreCase("Leftramp")){
			System.out.println("Sprite image will be set left RAMP");
			sprite = leftrampSprite.getSubimage(e.getSpriteX(count), e.getSpriteY(),
				LeftRamp.X_LENGTH * 2, LeftRamp.Y_LENGTH * 2);
		}
		count++;
		return sprite;
	}
}
