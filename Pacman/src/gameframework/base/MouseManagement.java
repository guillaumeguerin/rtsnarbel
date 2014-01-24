package gameframework.base;

import gameframework.game.GameConfig;
import gameframework.game.GameEntity;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MouseManagement extends MouseAdapter {

	ConcurrentLinkedQueue<GameEntity> gameEntities = null;
	
	/*public MouseManagement(Iterator<GameEntity> iterator) {
		gameEntities = (ConcurrentLinkedQueue<GameEntity>) iterator;
	}*/
	
	public void mousePressed(MouseEvent event) {
		System.out.println("coord x : " + event.getX() + "   y : "+ event.getY());

		Rectangle mouseBoundingBox = new Rectangle(event.getX(),event.getY(),GameConfig.SPRITE_SIZE,GameConfig.SPRITE_SIZE);
		/*for(GameEntity x: gameEntities) {
			
		}*/
	}
		/*int keycode = event.getKeyCode();
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
	}*/
}
