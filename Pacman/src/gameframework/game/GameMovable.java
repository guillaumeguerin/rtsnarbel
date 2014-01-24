package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.base.TravelVector;
import gameframework.base.TravelVectorDefaultImpl;

import java.awt.Point;

public abstract class GameMovable implements Movable {
	GameMovableDriver moveDriver = new GameMovableDriverDefaultImpl();

	Point position = new Point();
	Point destination = new Point();
	TravelVector travelVector = TravelVectorDefaultImpl.createNullVector();

	public void setPosition(Point p) {
		position = (Point) p.clone();
	}

	public Point getPosition() {
		return position;
	}

	public void setDestination(Point p) {
		destination = (Point) p.clone();
	}

	public Point getDestination() {
		return destination;
	}
	
	public void setTravelVector(TravelVector travelVector) {
		this.travelVector = (TravelVector) travelVector.clone();
	}

	public TravelVector getTravelVector() {
		return (TravelVector) travelVector.clone();
	}

	public void setDriver(GameMovableDriver driver) {
		moveDriver = driver;
	}

	public GameMovableDriver getDriver() {
		return moveDriver;
	}

	public void oneStepMove() {
		TravelVector m = moveDriver.getTravelVector(this);
		
		travelVector.setDirection(m.getDirection());
		travelVector.setSpeed(4);
		//System.out.println("src : "+ travelVector.getSource().x + " dist : " + Math.abs(travelVector.getSource().x - travelVector.getDestination().x));
		/*if(Math.abs(travelVector.getSource().x - travelVector.getDestination().x) >= GameConfig.SPRITE_SIZE || Math.abs(travelVector.getSource().y - travelVector.getDestination().y) >= GameConfig.SPRITE_SIZE) {*/
			position.translate((int) travelVector.getDirection().getX()
					* travelVector.getSpeed(), (int) travelVector.getDirection()
					.getY() * travelVector.getSpeed());
				oneStepMoveAddedBehavior();
		/*}*/
	}

	public abstract void oneStepMoveAddedBehavior();
}
