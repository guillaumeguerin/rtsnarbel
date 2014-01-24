package pacman.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameConfig;
import gameframework.game.GameEntity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import soldiers.weapon.Shield;

public class ShieldDraw implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image = null;
	protected Point position;
	public static final int RENDERING_SIZE = GameConfig.SPRITE_SIZE;
	public Shield myShield = new Shield();
	
	
	public ShieldDraw(Canvas defaultCanvas, Point pos) {
		image = new DrawableImage("images/shield.png", defaultCanvas);
		position = pos;
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
				null);

	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}
}
