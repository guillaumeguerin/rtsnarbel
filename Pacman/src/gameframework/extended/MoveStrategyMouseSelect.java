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
public class MoveStrategyMouseSelect extends MouseAdapter  {
	protected Point positionLeftClick = new Point(0, 0);
	protected Point positionRightClick = new Point(0,0);

	public Point getPositionLeftClick() {
		return positionLeftClick;
	}

	public Point getPositionRightClick() {
		return positionLeftClick;
	}

	public void setPositionLeftClick(Point position2) {
		positionLeftClick = position2;
	}

	public void setPositionRightClick(Point position2) {
		positionRightClick = position2;
	}
	
	// javadoc7 : event.getButton return int >> 0 = nobouton, 1 = bouton1(gauche), 2 = bouton2(molette), 3 = bouton3(droit)
	public void mousePressed(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON1) {
			positionLeftClick = new Point(event.getX()-GameConfig.SPRITE_SIZE/2, event.getY()-GameConfig.SPRITE_SIZE/2);
			//System.out.println("mouse coord x:" + position.x + " y: " + position.y);
		}
		else if(event.getButton() == MouseEvent.BUTTON3) {
			positionRightClick = new Point(event.getX()-GameConfig.SPRITE_SIZE/2, event.getY()-GameConfig.SPRITE_SIZE/2);
		}
	}


	public void mouseReleased(MouseEvent event) {
		System.out.println("mouse released");
	}



}
