package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import entities.BasketBall;
import entities.Entities;
import enums.EType;

public class SpriteSheet {
	private BufferedImage sprite;
	private BufferedImage basketballSprite;
	private BufferedImage bowlingballSprite;
	private BufferedImage pingpongSprite;
	private BufferedImage ballonSprite;
	
	
	public SpriteSheet()
	{
		// Use ImageIO to read in the card sheet
		try
		{
			basketballSprite = ImageIO.read(new File("Images/BasketBallSpriteSheet.gif"));
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

		if(e.toString().equalsIgnoreCase("Basketball")){
			System.out.println("Sprite image will be set");
			sprite = basketballSprite.getSubimage(e.getSpriteX(), e.getSpriteY(),
				BasketBall.X_LENGHT*2, BasketBall.Y_LENGHT*2);
		}
		return sprite;
	}
}
