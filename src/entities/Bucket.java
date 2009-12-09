package entities;

import java.awt.image.BufferedImage;

import engine.World;
import enums.EType;

public class Bucket extends Entities {
	private static BufferedImage[] staticSprites;

	public Bucket(String objType) {
		super(EType.bucket);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/BasketBallSpriteSheet.png"), 5, 5);
		sprite = staticSprites;
		
		
	}

}
