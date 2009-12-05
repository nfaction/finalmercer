package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import entities.BasketBall;
import entities.Entities;
import enums.EType;

public class SpriteSheet {
	private BufferedImage sprite = null;
	private BufferedImage basketballSprite;
	private BufferedImage bowlingballSprite;
	private BufferedImage pingpongSprite;
	private BufferedImage ballonSprite;
	
	
	public SpriteSheet()
	{
		// Use ImageIO to read in the card sheet
		try
		{
			basketballSprite = ImageIO.read(new File("BasketBallSpriteSheet.gif"));
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
		int row = 0;// = getRow();
		int col = 0;// = getCol();

		// The size of 1 cad is 79 x 123
		// The call to getSubimage has the following parameters: startX, startY,
		// width, height
		if(e.equals(EType.basketball))
			sprite = basketballSprite.getSubimage(0, 0,
				BasketBall.X_LENGHT, BasketBall.Y_LENGHT);

		return sprite;
	}
}
