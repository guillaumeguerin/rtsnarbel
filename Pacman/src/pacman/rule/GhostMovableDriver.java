package pacman.rule;

import gameframework.base.Movable;
import gameframework.base.SpeedVector;
import gameframework.base.TravelVector;
import gameframework.game.GameMovableDriverDefaultImpl;

public class GhostMovableDriver extends GameMovableDriverDefaultImpl {

	// A modified random strategy that makes ghosts mostly follow the alleys in
	// one direction.
	// Random speed vectors are (1,0) (0,1) (-1,0) (0,-1), but sometimes speed
	// vectors are reinitialized to (0,0) by GameMovableDriver.
	@Override
	public TravelVector getTravelVector(Movable m) {
		TravelVector currentTravelVector, possibleTravelVector;

		currentTravelVector = m.getTravelVector();
		possibleTravelVector = super.getTravelVector(m);

		int nbTries = 10;
		while (true) {
			nbTries--;
			if ((possibleTravelVector.getDirection().getX() == currentTravelVector
					.getDirection().getX())
					&& (possibleTravelVector.getDirection().getY() != -currentTravelVector
							.getDirection().getY()))
				break;

			if ((possibleTravelVector.getDirection().getX() != -currentTravelVector
					.getDirection().getX())
					&& (possibleTravelVector.getDirection().getY() == currentTravelVector
							.getDirection().getY()))
				break;

			if ((possibleTravelVector.getDirection().getX() != currentTravelVector
					.getDirection().getX())
					&& (possibleTravelVector.getDirection().getY() != currentTravelVector
							.getDirection().getY()))
				break;

			possibleTravelVector = super.getTravelVector(m);
			if (nbTries < 1)
				break;
		}
		return (possibleTravelVector);
	}

}