package gameframework.game;

import java.awt.Point;

import gameframework.base.Movable;
import gameframework.base.MoveStrategy;
import gameframework.base.MoveStrategyDefaultImpl;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.extended.MoveStrategyMouseSelect;

public class GameMovableMouseDriverDefaultImpl implements GameMovableMouseDriver {
	protected MoveStrategyMouseSelect moveStrategy;

	public GameMovableMouseDriverDefaultImpl() {
		moveStrategy = new MoveStrategyMouseSelect();
	}

	public void setStrategy(MoveStrategyMouseSelect strat) {
		moveStrategy = strat;
	}

	@Override
	public Point getPoint() {
		//System.out.println(moveStrategy.getPosition().x + moveStrategy.getPosition().y);
		return moveStrategy.getPositionLeftClick();
	}

	public void setPoint(Point position) {
		// TODO Auto-generated method stub
		moveStrategy.setPositionLeftClick(position);
	}
}
