package gameframework.extended;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;
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

	@Override
	public void mousePressed(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON3) {
			speedVector.setDirection(new Point(event.getX(), event.getY()));
		}
	}
	
	public void mouseReleased(KeyEvent event) {
		speedVector.setDirection(new Point(0,0));
	}
}
