package gameframework.extended;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.game.GameConfig;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyMouseSelect extends MouseAdapter implements MoveStrategy {
	protected SpeedVector speedVector = new SpeedVectorDefaultImpl(new Point(0,
			0));

	public SpeedVector getSpeedVector() {
		return speedVector;
	}
// javadoc7 : event.getButton return int >> 0 = nobouton, 1 = bouton1(gauche), 2 = bouton2(molette), 3 = bouton3(droit)
	public void mousePressed(MouseEvent event) {
		if(event.getButton() == 3){
			System.out.println("aaaaaaa " + event.getX()/GameConfig.SPRITE_SIZE + " " + event.getY()/GameConfig.SPRITE_SIZE);
			speedVector.setDirection(new Point(event.getX()/GameConfig.SPRITE_SIZE, event.getY()/GameConfig.SPRITE_SIZE));
		}
	}

	/*public void mouseReleased(MouseEvent event) {
		speedVector.setDirection(new Point(0,0));
	}*/
}
