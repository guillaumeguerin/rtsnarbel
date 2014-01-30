package gameframework.base;

import java.awt.Point;
import java.util.Random;

/**
 * MoveStrategy which randomly selects one of the four directions (top, bottom,
 * left, right)
 */
public class MoveStrategyRandomLazy implements MoveStrategy {
	SpeedVector currentMove = new SpeedVectorDefaultImpl(new Point(0, 0));
	static Random random = new Random();
	private static final int SPEED_DEFAULT = 8; 
	private int cpt = random.nextInt(50);
	private boolean moveState = true;

	public SpeedVector getSpeedVector() {
		
		int i = random.nextInt(50);
		switch (i) {
		case 0:
			currentMove.setDirection(new Point(1, 0));
			break;
		case 1:
			currentMove.setDirection(new Point(-1, 0));
			break;
		case 2:
			currentMove.setDirection(new Point(0, -1));
			break;
		case 3:
			currentMove.setDirection(new Point(0, 1));
			break;

		}
		
		
		// Sleep character 20  + env 0-49 turns
		if(cpt == 0){
			moveState = !moveState;
			cpt = random.nextInt(51)+20;
		}
		else
			--cpt;
		
		if(!moveState){
			currentMove.setSpeed(0);
			currentMove.setDirection(new Point(0, 0));
		}
		else
			currentMove.setSpeed(SPEED_DEFAULT);
			
		return currentMove;
	}
}
