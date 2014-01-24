package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.MoveStrategy;
import gameframework.base.MoveStrategyDefaultImpl;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.base.TravelVector;
import gameframework.base.TravelVectorDefaultImpl;

public class GameMovableDriverDefaultImpl implements GameMovableDriver {
	protected MoveBlockerChecker moveBlockerChecker;
	protected MoveStrategy moveStrategy;

	public GameMovableDriverDefaultImpl() {
		moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveStrategy = new MoveStrategyDefaultImpl();
	}

	public void setStrategy(MoveStrategy strat) {
		moveStrategy = strat;
	}

	public void setmoveBlockerChecker(MoveBlockerChecker obst) {
		moveBlockerChecker = obst;
	}

	public TravelVector getTravelVector(Movable m) {
		TravelVector possibleSpeedVector;

		possibleSpeedVector = moveStrategy.getTravelVector();
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			//System.out.println("nouveau vector");
			return possibleSpeedVector;
		}

		// If the strategy did not provide a valid vector, try to keep the
		// current vector.
		possibleSpeedVector = m.getTravelVector();
		
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			//System.out.println("TOTO");
			return possibleSpeedVector;
		}

		return TravelVectorDefaultImpl.createNullVector();
	}
}
