package entities;

import java.awt.image.BufferedImage;

import engine.World;
import entities.Entities;
import enums.EType;

public class Belt extends Entities {
	
	private static BufferedImage[] staticSprites;

	public Belt(){
		super(EType.belt);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/"), 5, 5);
		sprite = staticSprites;
		
		

	}


}
