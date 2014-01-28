package soldiers.soldier;

import java.awt.Canvas;

public class InfantryMan extends SoldierAbstract {
	private static final int FORCE = 15;
	private static final int HEALTHPOINTS = 100;

	public InfantryMan(String nom, Canvas defaultCanvas, String image) {
		super(nom, HEALTHPOINTS, FORCE, defaultCanvas, image);
	}

	public void heal() { //XXX resurrection allowed
		healthPoints = HEALTHPOINTS;
	}
}		