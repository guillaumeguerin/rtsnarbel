package gameframework.base;

import gameframework.game.GameConfig;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategySelectKeyboard extends KeyAdapter implements MoveStrategySelect {
	protected TravelVector travelVector = TravelVectorDefaultImpl.createNullVector();
	
	protected MouseManagement mouse;
	protected boolean select = true;

	public TravelVector getTravelVector() {
		return travelVector;
	}
	

	public void keyPressed(KeyEvent event) {
		//System.out.println("coord souris : " + mouse.getX() + " " + mouse.getY() );
		if(isSelected()) {
			travelVector.setSpeed(0);
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
			travelVector.setSpeed(0);
		}
	}

	
	public boolean isSelected() {
		return select;
	}


	public void setSelected(boolean a) {
		select = a;
	}
}
