package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entities;

public class SpriteSheet {
	
	private BufferedImage spriteSheet;
	
	public SpriteSheet()
	{
		// Use ImageIO to read in the card sheet
		try
		{
			spriteSheet = ImageIO.read(new File("cardSheet.png"));
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
	public BufferedImage getCardImage(Entities e)
	{
		int row = 0;// = getRow();
		int col = 0;// = getCol();

		// The size of 1 cad is 79 x 123
		// The call to getSubimage has the following parameters: startX, startY,
		// width, height
		BufferedImage thisCard = spriteSheet.getSubimage(79 * col, 123 * row,
				79, 123);

		return thisCard;
	}
}
