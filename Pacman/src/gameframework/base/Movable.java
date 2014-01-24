package gameframework.base;

import java.awt.Point;

/**
 * Has a current position, a {@link SpeedVector} and a bounding box.
 */
public interface Movable extends ObjectWithBoundedBox {

	public Point getPosition();

	public TravelVector getTravelVector();

	public void setTravelVector(TravelVector m);

	public void oneStepMove();

}
