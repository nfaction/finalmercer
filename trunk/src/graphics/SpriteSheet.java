package graphics;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import entities.Entities;


public class SpriteSheet {
	private BufferedImage sprite;

	public SpriteSheet() {
	}

	/**
	 * This method will get the subimage which represents the card 'c'.
	 * 
	 * @param c
	 *            , the card for which we want an image
	 * @return The image for the card c
	 */
	public BufferedImage getStateImage(Entities e) {

		int x = e.getSpriteX();
		int y = e.getSpriteY();
		int w = e.getXLength() * 2;
		int h = e.getYLength() * 2;
		String imageLocation = e.getImagePath();
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(new File(imageLocation));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		;

		sprite = temp.getSubimage(x, y, w, h);

		return sprite;


	}
}
