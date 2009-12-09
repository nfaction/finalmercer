package entities;

import java.awt.image.BufferedImage;

import engine.World;
import enums.EType;

public class Light extends Entities {

	private static BufferedImage[] staticSprites;
	public Light() {
		super(EType.light);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/BasketBallSpriteSheet.png"), 5, 5);
		sprite = staticSprites;

	}

}
