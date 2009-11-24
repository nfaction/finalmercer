package graphics;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class SandboxPanel extends JPanel{
	
	private SpriteSheet cardImages;
	
	public SandboxPanel(){
		this.setLayout(new BorderLayout());
		this.setSize(950, 550);
		this.setLocation(0, 50);
		this.setBackground(Color.BLUE);
	}
}
