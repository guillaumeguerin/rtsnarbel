package soldiers.soldier;

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

public abstract class SoldierAbstract extends GameMovable implements Soldier, Drawable, GameEntity, Overlappable {
	
	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 32;
	protected boolean movable = true;
	protected boolean vulnerable = false;
	protected int vulnerableTimer = 0;
	
	protected String name;
	protected float healthPoints;
	protected float totalHealthPoints;
	protected float force;
	protected int IDTeam;

	public SoldierAbstract(String nom, float healthPoints, float force, Canvas defaultCanvas, String image, int IDTeam) {
		this.name = nom;
		this.healthPoints = healthPoints;
		this.force = force;
		
		this.IDTeam = IDTeam;
		spriteManager = new SpriteManagerDefaultImpl(image,
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"right", "left", "up",
				"down",//
				"static");
	}


	public String getName() {
		return name;
	}

	public float getHealthPoints() {
		return healthPoints;
	}

	public float getTotalHealthPoints() {
		return totalHealthPoints;
	}
	
	public boolean alive() {
		return getHealthPoints() > 0;
	}

	public boolean parry(float force) { //default: no parry effect
		healthPoints = (getHealthPoints() > force) ? 
				               getHealthPoints() - force : 0;
	    return healthPoints > 0;
	}

	public float strike() {
		return alive() ? force : 0; 
	}
	
	
	public void setInvulnerable(int timer) {
		vulnerableTimer = timer;
	}
	
	public int getTeam() {
		return IDTeam;
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
}
