package game.entity;

import java.awt.Canvas;
import java.awt.Graphics;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;

public class Grass implements Drawable, GameEntity {
	protected static DrawableImage image = null;
	int x, y;
	public static final int RENDERING_SIZE = 32;

	public Grass(Canvas defaultCanvas, int xx, int yy) {
		image = new DrawableImage("images/grass.gif", defaultCanvas);
		x = xx;
		y = yy;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, RENDERING_SIZE, RENDERING_SIZE,
				null);
	}


}
