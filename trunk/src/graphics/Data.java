package graphics;

import entities.*;
import enums.EType;

import java.awt.Image;

public class Data {
	private static Data single;

	private int length;
	private int toolboxShiftX;
	private int toolboxShiftY;

	private EType[] types;
	protected boolean[] moved;
	protected Image[] images;
	protected int[] X_LENGTH;
	protected int[] Y_LENGTH;
	protected int[] imageX;
	protected int[] imageY;

	public static Data getObj() {
		if (single == null) {
			single = new Data(SandboxPanel.toolboxShiftX,
					SandboxPanel.toolboxShiftY);
		}
		return single;

	}

	private Data(int shiftx, int shifty) {
		toolboxShiftX = shiftx;
		toolboxShiftY = shifty;
		init();
	}

	private void init() {
		length = EType.values().length;
		types = EType.values();
		moved = new boolean[length];
		X_LENGTH = new int[length];
		Y_LENGTH = new int[length];
		images = new Image[length];
		imageX = new int[length];
		imageY = new int[length];
		initObj();
		initImageX_Y();
	}

	private void initObj() {
		initObjHellper(new BasketBall());
		initObjHellper(new Balloon());
		initObjHellper(new BowlingBall());
		initObjHellper(new PingPongBall());
		initObjHellper(new LeftRamp());
		initObjHellper(new PowerGear());
		initObjHellper(new ConveyorBelt());
		initObjHellper(new Battery());
		initObjHellper(new Gear());
		initObjHellper(new Tack());
		initObjHellper(new Light());
		initObjHellper(new Weight());
		initObjHellper(new Rock());
		initObjHellper(new Switch());
		initObjHellper(new Rocket());
		initObjHellper(new RightRamp());
		initObjHellper(new Motor());
		initObjHellper(new StraightRamp());
		//initObjHellper(new PowerGear());
		initObjHellper(new HCementWall());
		initObjHellper(new VCementWall());
		initObjHellper(new HRubberWall());
		initObjHellper(new VRubberWall());
		initObjHellper(new Bomb());
		initObjHellper(new Poop());
		initObjHellper(new Mouse());
		initObjHellper(new RubberBall());
		initObjHellper(new StickMan());
		
		// initObjHellper(new );
		// initObjHellper(new );
		// initObjHellper(new );
		// initObjHellper(new );
		// initObjHellper(new );
	}

	private void initObjHellper(Entities ent) {
		EType type;

		type = ent.getObjType();
		X_LENGTH[getLocation(type)] = ent.getXLength();
		Y_LENGTH[getLocation(type)] = ent.getYLength();
		images[getLocation(type)] = ent.getSpriteImage();

	}

	private void initImageX_Y() {
		imageX[getLocation(EType.balloon)] = 40 + toolboxShiftX;
		imageY[getLocation(EType.balloon)] = 40 + toolboxShiftY;

		imageX[getLocation(EType.basketball)] = 85 + toolboxShiftX;
		imageY[getLocation(EType.basketball)] = 40 + toolboxShiftY;

		imageX[getLocation(EType.bowlingball)] = 140 + toolboxShiftX;
		imageY[getLocation(EType.bowlingball)] = 40 + toolboxShiftY;

		imageX[getLocation(EType.pingPongBall)] = 195 + toolboxShiftX;
		imageY[getLocation(EType.pingPongBall)] = 55 + toolboxShiftY;

		//imageX[getLocation(EType.powerGear)] = 0 + toolboxShiftX;
		//imageY[getLocation(EType.powerGear)] = 200 + toolboxShiftY;

		imageX[getLocation(EType.gear)] = 220 + toolboxShiftX;
		imageY[getLocation(EType.gear)] = 190 + toolboxShiftY;
		
		imageX[getLocation(EType.battery)] = 10 + toolboxShiftX;
		imageY[getLocation(EType.battery)] = 280 + toolboxShiftY;

		imageX[getLocation(EType.conveyorBelt)] = 130 + toolboxShiftX;
		imageY[getLocation(EType.conveyorBelt)] = 95 + toolboxShiftY;

		//imageX[getLocation(EType.leftRamp)] = 20 + toolboxShiftX;
		//imageY[getLocation(EType.leftRamp)] = 270 + toolboxShiftY;
		
		//imageX[getLocation(EType.rightRamp)] = 20 + toolboxShiftX;
		//imageY[getLocation(EType.rightRamp)] = 80 + toolboxShiftY;
		
		imageX[getLocation(EType.tack)] = 70 + toolboxShiftX;
		imageY[getLocation(EType.tack)] = 160 + toolboxShiftY;
		
		imageX[getLocation(EType.light)] = 220 + toolboxShiftX;
		imageY[getLocation(EType.light)] = 130 + toolboxShiftY;
		
		imageX[getLocation(EType.weight)] = 180 + toolboxShiftX;
		imageY[getLocation(EType.weight)] = 315 + toolboxShiftY;
		
		imageX[getLocation(EType.Switch)] = 100 + toolboxShiftX;
		imageY[getLocation(EType.Switch)] = 160 + toolboxShiftY;
		
		imageX[getLocation(EType.rock)] = 60 + toolboxShiftX;
		imageY[getLocation(EType.rock)] = 360 + toolboxShiftY;
		
		imageX[getLocation(EType.rocket)] = 80 + toolboxShiftX;
		imageY[getLocation(EType.rocket)] = 260 + toolboxShiftY;

		imageX[getLocation(EType.straightRamp)] = 30 + toolboxShiftX;
		imageY[getLocation(EType.straightRamp)] = 350 + toolboxShiftY;
		
		imageX[getLocation(EType.motor)] = 220 + toolboxShiftX;
		imageY[getLocation(EType.motor)] = 250 + toolboxShiftY;
		
		imageX[getLocation(EType.hCWall)] = 90 + toolboxShiftX;
		imageY[getLocation(EType.hCWall)] = 200 + toolboxShiftY;
		
		imageX[getLocation(EType.vCWall)] = 10 + toolboxShiftX;
		imageY[getLocation(EType.vCWall)] = 145 + toolboxShiftY;

		imageX[getLocation(EType.hRWall)] = 90 + toolboxShiftX;
		imageY[getLocation(EType.hRWall)] = 230 + toolboxShiftY;
		
		imageX[getLocation(EType.vRWall)] = 40 + toolboxShiftX;
		imageY[getLocation(EType.vRWall)] = 145 + toolboxShiftY;

		imageX[getLocation(EType.bomb)] = 110 + toolboxShiftX;
		imageY[getLocation(EType.bomb)] = 448 + toolboxShiftY;
		
		imageX[getLocation(EType.poop)] = 2 + toolboxShiftX;
		imageY[getLocation(EType.poop)] = 380 + toolboxShiftY;
		
		imageX[getLocation(EType.rubberBall)] = 120 + toolboxShiftX;
		imageY[getLocation(EType.rubberBall)] = 145 + toolboxShiftY;
		
		imageX[getLocation(EType.stickMan)] = 50 + toolboxShiftX;
		imageY[getLocation(EType.stickMan)] = 465 + toolboxShiftY;
		
		imageX[getLocation(EType.mouse)] = 120 + toolboxShiftX;
		imageY[getLocation(EType.mouse)] = 168 + toolboxShiftY;
	}

	// =============================================================

	public boolean anyHasMoved() {
		for (int i = 0; i < length; i++) {
			if (moved[i])
				return true;
		}
		return false;
	}

	public EType whoIsMoving() {
		for (int i = 0; i < length; i++) {
			if (moved[i])
				return types[i];
		}
		return null;
	}

	public boolean getMoved(int i) {
		return getMoved(types[i]);
	}

	public boolean getMoved(EType e) {
		return moved[getLocation(e)];
	}

	public void setMoved(int i, boolean set) {

		setMoved(types[i], set);
	}

	public void setMoved(EType e, boolean set) {
		if ((anyHasMoved() && !set) || (!anyHasMoved() && set))
			moved[getLocation(e)] = set;
	}

	// ===============================================================

	public Image getImage(EType e) {
		return images[getLocation(e)];
	}

	public int getXLENGTH(EType e) {
		return X_LENGTH[getLocation(e)];
	}

	public int getYLENGTH(EType e) {
		return Y_LENGTH[getLocation(e)];
	}

	public int getImageX(EType e) {
		return imageX[getLocation(e)];
	}

	public int getImageY(EType e) {
		return imageY[getLocation(e)];
	}

	// =========================================================

	public int getLocation(EType e) {
		for (int i = 0; i < length; i++) {
			if (e.compareTo(types[i]) == 0)
				return i;
		}
		System.out.println("Cannont get location");
		return -1;
	}

	public EType intToEType(int i) {
		return types[i];
	}

	public int length() {
		return EType.values().length;
	}

}
