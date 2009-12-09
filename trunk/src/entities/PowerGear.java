package entities;

import enums.EType;

public class PowerGear extends AbstractGear {
	public PowerGear(){
		super(EType.powerGear, 3f, 10f);
	}
	
	public void upDate(){
		super.upDate();
		wheel.adjustAngularVelocity(.1f);
	}
}
