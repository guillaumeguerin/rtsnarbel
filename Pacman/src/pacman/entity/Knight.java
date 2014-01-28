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

import soldiers.soldier.Soldier;

public class Knight extends GameMovable implements Drawable, GameEntity,
		Overlappable, Soldier {
	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 32;
	protected boolean movable = true;
	protected boolean vulnerable = false;
	protected int vulnerableTimer = 0;
	protected Soldier soldier;
	
	
	public Knight(Canvas defaultCanvas, String image, Soldier s) {
		spriteManager = new SpriteManagerDefaultImpl(image,
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"right", "left", "up",
				"down",//
				"static");
		soldier = s;
	}

	public void setSoldier(Soldier s) {
		soldier = s;
	}	
	
	public void setInvulnerable(int timer) {
		vulnerableTimer = timer;
	}

	public boolean isVulnerable() {
		return (vulnerableTimer <= 0);
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
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

		drawLifeBar(g, 3);
		
	}

	public void drawLifeBar(Graphics g, int ratio) {
		g.setColor(Color.red);
		g.fillRect(Math.round(getPosition().x - getTotalHealthPoints()/(2*ratio) + GameConfig.SPRITE_SIZE/2) -15, getPosition().y -15 , Math.round(getHealthPoints()/ratio), 5);
		g.setColor(Color.black);
		g.drawRect(Math.round(getPosition().x - getTotalHealthPoints()/(2*ratio) + GameConfig.SPRITE_SIZE/2) -15, getPosition().y -15 , Math.round(getTotalHealthPoints()/ratio), 5);
		g.drawString(getName(), getPosition().x -5, getPosition().y-20);
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
