package gameframework.base;

import java.awt.Point;

public interface TravelVector extends SpeedVector {

	public void setSource(Point p);
	public Point getSource();
	
	public void setDestination(Point p);
	public Point getDestination();
	
	public SpeedVector getSpeedVector();
	
	public void setSpeed(int i);
}
