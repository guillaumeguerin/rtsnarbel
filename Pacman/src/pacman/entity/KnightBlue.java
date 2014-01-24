package pacman.entity;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameConfig;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManager;
import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import soldiers.soldier.InfantryMan;
import soldiers.soldier.Soldier;

public class KnightBlue extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 32;
	protected boolean movable = true;
	protected boolean vulnerable = false;
	protected int vulnerableTimer = 0;
	protected Soldier s = new InfantryMan("toto");

	protected boolean isSelected = true;
	
	public KnightBlue(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/knight2.png",
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"right", "left", "up",
				"down",//
				"static");
	}

	public void setInvulnerable(int timer) {
		vulnerableTimer = timer;
	}

	public boolean isVulnerable() {
		return (vulnerableTimer <= 0);
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getTravelVector().getSpeedVector().getDirection();
		movable = true;
		if (!isVulnerable()) {
			spriteType += "invulnerable-";
		}

		if (tmp.getX() == 1) {
			spriteType += "right";
		} else if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType = "static";
			spriteManager.reset();
			movable = false;
		}
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());

		int ratio = 3;
		g.setColor(Color.red);
		g.fillRect(Math.round(getPosition().x - s.getTotalHealthPoints()/(2*ratio) + GameConfig.SPRITE_SIZE/2), getPosition().y -15 , Math.round(s.getHealthPoints()/ratio), 5);
		g.setColor(Color.black);
		g.drawRect(Math.round(getPosition().x - s.getTotalHealthPoints()/(2*ratio) + GameConfig.SPRITE_SIZE/2), getPosition().y -15 , Math.round(s.getTotalHealthPoints()/ratio), 5);
		g.drawString("Soldat 1", getPosition().x -5, getPosition().y-20);
		
		if(isSelected) {
			g.setColor(Color.yellow);
			g.drawRect(getPosition().x, getPosition().y, GameConfig.SPRITE_SIZE, GameConfig.SPRITE_SIZE);
		}
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (movable) {
			spriteManager.increment();
			if (!isVulnerable()) {
				vulnerableTimer--;
			}
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
}
