package pacman.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameConfig;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import soldiers.soldier.Soldier;

public class EnemyKnight extends NonPlayerEntity implements Drawable, GameEntity,
		Overlappable, Soldier {
	protected static DrawableImage image = null;
	protected Soldier soldier;
	protected boolean movable = true;
	protected int afraidTimer = 0;
	protected int maxAfraidTimer = 0;
	protected boolean active = true;
	private final SpriteManagerDefaultImpl spriteManager;
	public static final int RENDERING_SIZE = 32;

	public EnemyKnight(Canvas defaultCanvas, String imgname, Soldier s) {
		spriteManager = new SpriteManagerDefaultImpl(imgname,
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"left",
				"right",
				"up",
				"down",//
				"beginAfraid-left",
				"beginAfraid-right",
				"beginAfraid-up",
				"beginAfraid-down", //
				"endAfraid-left", "endAfraid-right",
				"endAfraid-up",
				"endAfraid-down", //
				"inactive-left", "inactive-right", "inactive-up",
				"inactive-down", //
				"unused");
		soldier = s;
	}

	public boolean isAfraid() {
		return afraidTimer > 0;
	}

	public void setAfraid(int timer) {
		maxAfraidTimer = afraidTimer = timer;
	}

	public boolean isActive() {
		return active;
	}

	public void setAlive(boolean aliveState) {
		active = aliveState;
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if (!isActive()) {
			spriteType = "inactive-";
		} else if (afraidTimer > maxAfraidTimer / 2) {
			spriteType = "beginAfraid-";
		} else if (isAfraid()) {
			spriteType = "endAfraid-";
		}

		if (tmp.getX() == -1) {
			spriteType += "right";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType += "left";
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
		
		drawLifeBar(g,3);
	}
	
	public void drawLifeBar(Graphics g, int ratio) {
		g.setColor(Color.red);
		g.fillRect(Math.round(getPosition().x - getTotalHealthPoints()/(2*ratio) + GameConfig.SPRITE_SIZE/2) -15, getPosition().y -15 , Math.round(getHealthPoints()/ratio), 5);
		g.setColor(Color.black);
		g.drawRect(Math.round(getPosition().x - getTotalHealthPoints()/(2*ratio) + GameConfig.SPRITE_SIZE/2) -15, getPosition().y -15 , Math.round(getTotalHealthPoints()/ratio), 5);
		g.drawString(getName(), getPosition().x -5, getPosition().y-20);
	}

	public void oneStepMoveAddedBehavior() {
		if (movable) {
			spriteManager.increment();
			if (isAfraid()) {
				afraidTimer--;
			}
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

	public String getName() {
		return soldier.getName();
	}

	public float getHealthPoints() {
		return soldier.getHealthPoints();
	}

	public boolean alive() {
		return soldier.alive();
	}

	public void heal() {
		soldier.heal();
	}

	public boolean parry(float force) {
		return soldier.parry(force);
	}

	public float strike() {
		return soldier.strike();
	}

	@Override
	public float getTotalHealthPoints() {
		return soldier.getTotalHealthPoints();
	}

	public int getTeam() {
		return soldier.getTeam();
	}
}
