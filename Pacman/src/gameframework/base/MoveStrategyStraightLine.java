package gameframework.base;

import java.awt.Point;

public class MoveStrategyStraightLine implements MoveStrategy {

	Point goal, currentPosition;

	public MoveStrategyStraightLine(Point pos, Point goal) {
		this.goal = goal;
		this.currentPosition = pos;
	}

	public TravelVector getTravelVector() {
		double dist = currentPosition.distance(goal);
		int xDirection = (int) Math.rint((goal.getX() - currentPosition.getX())
				/ dist);
		int yDirection = (int) Math.rint((goal.getY() - currentPosition.getY())
				/ dist);
		TravelVector move = new TravelVectorDefaultImpl(new Point(0,0), new Point(0,0), new SpeedVectorDefaultImpl(new Point(xDirection,yDirection)));
		return move;
	}
}
