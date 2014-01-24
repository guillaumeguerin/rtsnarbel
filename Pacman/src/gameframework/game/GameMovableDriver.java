package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.SpeedVector;
import gameframework.base.TravelVector;

/**
 * Applies moveBlocker checker and moving strategies
 */
public interface GameMovableDriver {
	public TravelVector getTravelVector(Movable m);
}
