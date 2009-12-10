package entities;

import enums.EType;

public class PowerGear extends AbstractGear {
	
	private boolean field = false;
	public PowerGear(){
		super(EType.powerGear, 3f, 10f);
	}
	
	public void upDate(){
		super.upDate();
		wheel.adjustAngularVelocity(.1f);
	}
	
	public boolean inField(){
		
		return field;
	}
	
	public void setField(boolean b) {
		field = b;
	}
}
