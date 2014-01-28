package gameframework.game;

import java.awt.Point;

import gameframework.base.Movable;
import gameframework.base.SpeedVector;

/**
 * Applies moveBlocker checker and moving strategies
 */
public interface GameMovableMouseDriver {
	public Point getPoint();
}
