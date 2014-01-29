package pacman.rule;

import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;
import pacman.entity.Horse;
import pacman.entity.Water;

public class PacmanMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {

	public void moveBlockerRule(Horse g, Water w) throws IllegalMoveException {
		// The default case is when a ghost is active and not able to cross a
		// wall
		if (g.isActive()) {
			throw new IllegalMoveException();
			// When a ghost is not active, it is able to cross a wall
		}
	}
}
