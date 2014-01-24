package gameframework.base;

import gameframework.game.GameConfig;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyKeyboard extends KeyAdapter implements MoveStrategy {
	protected TravelVector travelVector = TravelVectorDefaultImpl.createNullVector();

	public TravelVector getTravelVector() {
		return travelVector;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_RIGHT:
			travelVector.setDirection(new Point(1, 0));
			travelVector.setDestination(new Point(travelVector.getSource().x + GameConfig.SPRITE_SIZE * 3, travelVector.getSource().y ));
			//travelVector.setSpeed(4);
			break;
		case KeyEvent.VK_LEFT:
			travelVector.setDirection(new Point(-1, 0));
			travelVector.setDestination(new Point(travelVector.getSource().x - GameConfig.SPRITE_SIZE * 3, travelVector.getSource().y ));
			//travelVector.setSpeed(4);
			break;
		case KeyEvent.VK_UP:
			travelVector.setDirection(new Point(0, -1));
			travelVector.setDestination(new Point(travelVector.getSource().x, travelVector.getSource().y - GameConfig.SPRITE_SIZE * 3));
			//travelVector.setSpeed(4);
			break;
		case KeyEvent.VK_DOWN:
			travelVector.setDirection(new Point(0, 1));
			travelVector.setDestination(new Point(travelVector.getSource().x, travelVector.getSource().y + GameConfig.SPRITE_SIZE * 3));
			//travelVector.setSpeed(4);
			break;
		}
	}
}
