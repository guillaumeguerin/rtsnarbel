package gameframework.base;

import gameframework.game.GameConfig;
import gameframework.game.GameMovable;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyMouse extends MouseAdapter implements MoveStrategy {
	protected SpeedVector speedVector = new SpeedVectorDefaultImpl(new Point(0,0));
	protected GameMovable _movable;
	
	public MoveStrategyMouse(GameMovable movable) {
		_movable = movable;
	}

	public SpeedVector getSpeedVector() {
		return speedVector;
	}


	public void mousePressed(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON3) {
			Point p = new Point(event.getX()-GameConfig.SPRITE_SIZE/2, event.getY()-GameConfig.SPRITE_SIZE/2);
			Point p2 = new Point(p.x - _movable.getPosition().x, p.y - _movable.getPosition().y);
			p2 = new Point(p2.x/GameConfig.SPRITE_SIZE, p2.y/GameConfig.SPRITE_SIZE);
			int a = p2.x;
			int b = p2.y;
			if(a != 0)
				a/=a;
			if(b != 0)
				b/=b;
			if(p2.x<0)
				a = -a;
			if(p2.y<0)
				b = -b;
			System.out.println("mouse coord x: " + event.getX() + " y: " + event.getY() + " p2.x: " + p2.x + " p2.y: "+ p2.y +" a: " + a + " b: " + b);
			speedVector.setDirection(new Point(a,b));
		}
	}
	
	public void mouseReleased(MouseEvent event) {
		speedVector.setDirection(new Point(0,0));
	}
}
