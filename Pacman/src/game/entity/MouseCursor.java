package game.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameConfig;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.GameMovableMouseDriverDefaultImpl;

public class MouseCursor extends GameMovable implements GameEntity, Overlappable, Drawable{
	protected static DrawableImage image = null;
	public Point position = new Point(0,0);
	public GameMovableMouseDriverDefaultImpl driver;
	
	public MouseCursor(Canvas canvas) {
		image = new DrawableImage("", canvas);
	}
	
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, GameConfig.SPRITE_SIZE, GameConfig.SPRITE_SIZE));
	}

	public void oneStepMove() {
		if(position.x == 0 && position.y ==0) {
			position = driver.getPoint();
			setPosition(position);
		}
		else {
			position = new Point(0,0);
			driver.setPoint(position);
		}
	}
	
	public void oneStepMoveAddedBehavior() {
		
	}

	public void setDriver(GameMovableMouseDriverDefaultImpl mouseDriver) {
		driver = mouseDriver;
		
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), GameConfig.SPRITE_SIZE, GameConfig.SPRITE_SIZE,
				null);

	}


}
