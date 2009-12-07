package entities;

public class PowerGear extends AbstractGear {
	public PowerGear(){
		super(3f, 10f);
	}
	
	public void upDate(){
		wheel.adjustAngularVelocity(.1f);
	}

}
