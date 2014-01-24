package gameframework.base;

import java.awt.Point;

public class TravelVectorDefaultImpl implements TravelVector {

	Point src;
	Point dst;
	SpeedVector speedvector;
	
	public TravelVectorDefaultImpl(Point src, Point dst, SpeedVector speedvector) {
		this.src = src;
		this.dst = dst;
		this.speedvector = speedvector;
	}

	public Point getDirection() {
		return speedvector.getDirection();
	}

	public int getSpeed() {
		return speedvector.getSpeed();
	}

	public void setDirection(Point p) {
		speedvector.setDirection(p);
	}

	public void setSpeed(int i) {
		speedvector.setSpeed(i);
	}

	public Object clone() {
		return new TravelVectorDefaultImpl(new Point(src.x, src.y), new Point(dst.x, dst.y), new SpeedVectorDefaultImpl(speedvector.getDirection(), speedvector.getSpeed()));
	}

	public void setSource(Point p) {
		src = p;
	}

	public Point getSource() {
		return src;
	}

	public void setDestination(Point p) {
		dst = p;
	}

	public Point getDestination() {
		return dst;
	}

	public static TravelVector createNullVector() {
		return new TravelVectorDefaultImpl(new Point(0,0), new Point(0,0), SpeedVectorDefaultImpl.createNullVector());
	}

	public SpeedVector getSpeedVector() {
		return speedvector;
	}

	public void printSpeedValues() {
		this.speedvector.printSpeedValues();
	}

}
