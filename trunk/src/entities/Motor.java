package entities;

import enums.EType;

public class Motor extends AbstractGear{
	
	public Motor() {
		super(EType.motor, 3f, 10f);
	}

	public void upDate() {
		super.upDate();
		wheel.adjustAngularVelocity(.1f);
	}
}
