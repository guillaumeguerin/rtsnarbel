package gameframework.extended;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.game.GameConfig;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyMouseStop extends MouseAdapter implements MoveStrategy {
	protected SpeedVector speedVector = new SpeedVectorDefaultImpl(new Point(0,
			0));

	public SpeedVector getSpeedVector() {
		return speedVector;
	}

	public void mousePressed(MouseEvent event) {
		System.out.println("aaaaaaa " + event.getX()/GameConfig.SPRITE_SIZE + " " + event.getY()/GameConfig.SPRITE_SIZE);
		speedVector.setDirection(new Point(event.getX()/GameConfig.SPRITE_SIZE, event.getY()/GameConfig.SPRITE_SIZE));
	}
	
	/*public void mouseReleased(MouseEvent event) {
		speedVector.setDirection(new Point(0,0));
	}*/
}
