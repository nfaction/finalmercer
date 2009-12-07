package entities;

import engine.World;
import engine.joint.AngleJoint;
import engine.joint.BasicJoint;
import engine.joint.FixedJoint;
import engine.joint.Joint;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.StaticBody;
import engine.vector.Vector;
import enums.EType;

public class Gear extends AbstractGear {
	public Gear() {
		super(3);
		wheel.setRotation(1);
	}
}