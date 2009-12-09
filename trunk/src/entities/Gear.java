package entities;

import enums.EType;

public class Gear extends AbstractGear {
	public Gear() {
		super(EType.gear,3);
		wheel.setRotation(1);
	}
}