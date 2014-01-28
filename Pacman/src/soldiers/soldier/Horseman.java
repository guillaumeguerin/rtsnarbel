package soldiers.soldier;

import java.awt.Canvas;
import java.awt.Rectangle;

public class Horseman extends SoldierAbstract {
	private static final int FORCE = 20;
	private static final int HEALTHPOINTS = 120;

	public Horseman(String name, Canvas defaultCanvas, String image) {
		super(name, HEALTHPOINTS, FORCE, defaultCanvas, image);
	}

	public void heal() { //XXX resurrection allowed
		healthPoints = HEALTHPOINTS;
	}

}