package graphics;

import entities.Balloon;
import entities.BasketBall;
import entities.Battery;
import entities.BowlingBall;
import entities.ConveyorBelt;
import entities.Entities;
import entities.Gear;
import entities.LeftRamp;
import entities.Light;
import entities.PingPongBall;
import entities.PowerGear;
import entities.RightRamp;
import entities.Rock;
import entities.Rocket;
import entities.StraightRamp;
import entities.Switch;
import entities.Tack;
import entities.Weight;
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
		initObjHellper(new StraightRamp());
		initObjHellper(new PowerGear());
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
		imageX[getLocation(EType.balloon)] = 20 + toolboxShiftX;
		imageY[getLocation(EType.balloon)] = 20 + toolboxShiftY;

		imageX[getLocation(EType.basketball)] = 75 + toolboxShiftX;
		imageY[getLocation(EType.basketball)] = 20 + toolboxShiftY;

		imageX[getLocation(EType.bowlingball)] = 140 + toolboxShiftX;
		imageY[getLocation(EType.bowlingball)] = 30 + toolboxShiftY;

		imageX[getLocation(EType.pingPongBall)] = 185 + toolboxShiftX;
		imageY[getLocation(EType.pingPongBall)] = 40 + toolboxShiftY;

		imageX[getLocation(EType.powerGear)] = 0 + toolboxShiftX;
		imageY[getLocation(EType.powerGear)] = 200 + toolboxShiftY;

		imageX[getLocation(EType.gear)] = 50 + toolboxShiftX;
		imageY[getLocation(EType.gear)] = 200 + toolboxShiftY;
		
		imageX[getLocation(EType.battery)] = 0 + toolboxShiftX;
		imageY[getLocation(EType.battery)] = 150 + toolboxShiftY;

		imageX[getLocation(EType.conveyorBelt)] = 100 + toolboxShiftX;
		imageY[getLocation(EType.conveyorBelt)] = 100 + toolboxShiftY;

		imageX[getLocation(EType.leftRamp)] = 20 + toolboxShiftX;
		imageY[getLocation(EType.leftRamp)] = 270 + toolboxShiftY;
		
		imageX[getLocation(EType.tack)] = 130 + toolboxShiftX;
		imageY[getLocation(EType.tack)] = 200 + toolboxShiftY;
		
		imageX[getLocation(EType.light)] = 170 + toolboxShiftX;
		imageY[getLocation(EType.light)] = 200 + toolboxShiftY;
		
		imageX[getLocation(EType.weight)] = 170 + toolboxShiftX;
		imageY[getLocation(EType.weight)] = 325 + toolboxShiftY;
		
		imageX[getLocation(EType.Switch)] = 20 + toolboxShiftX;
		imageY[getLocation(EType.Switch)] = 450 + toolboxShiftY;
		
		imageX[getLocation(EType.rock)] = 20 + toolboxShiftX;
		imageY[getLocation(EType.rock)] = 450 + toolboxShiftY;
		
		imageX[getLocation(EType.rocket)] = 150 + toolboxShiftX;
		imageY[getLocation(EType.rocket)] = 450 + toolboxShiftY;

		imageX[getLocation(EType.straightRamp)] = 50 + toolboxShiftX;
		imageY[getLocation(EType.straightRamp)] = 400 + toolboxShiftY;

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
