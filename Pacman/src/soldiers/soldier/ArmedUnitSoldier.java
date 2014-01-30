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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import soldiers.utils.*;

public class ArmedUnitSoldier extends GameMovable implements Drawable, GameEntity, Overlappable,
		ArmedUnit {
	protected Soldier soldier;
	protected List<String> equipments = new ArrayList<String>();
	protected AgeFactory age;

	protected boolean selected = false;
	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 32;
	protected boolean movable = true;
	protected int IDTeam;
	protected String typeSoldier;



	public ArmedUnitSoldier(AgeFactory factory, String soldatType, String name, Canvas defaultCanvas, String image, int IDTeam) {
		this.age = factory;
		String methodName = "get" + soldatType + "Soldier";
		try {
			Method method = factory.getClass().getMethod(methodName,
					String.class);
 			soldier = (SoldierAbstract) method.invoke(factory, name);
		} catch (Exception e) {
			throw new UnknownSoldierTypeException("Unknown soldier type"
					+ e.toString());
		}
		
		spriteManager = new SpriteManagerDefaultImpl(image,
				defaultCanvas, RENDERING_SIZE, 3);
		spriteManager.setTypes(
				//
				"right", "left", "up",
				"down",//
				"static");
		this.IDTeam = IDTeam;
		this.typeSoldier = soldatType;
	}

	public void addEquipment(String equipmentType) {
		if (alive()) { // XXX "else" not treated
			if (equipments.contains(equipmentType)) {
				return; // decoration not applied
			} else {
				String methodName = "get" + equipmentType + "Weapon";
				try {
					Method method = age.getClass().getMethod(methodName,
							Soldier.class);
					soldier = (Soldier) method.invoke(age, soldier);
					equipments.add(equipmentType);
				} catch (Exception e) {
					throw new RuntimeException("Unknown equipment type "
							+ e.toString());
				}
			}
		}
	}

	public String getName() {
		return soldier.getName();
	}

	public float getHealthPoints() {
		return soldier.getHealthPoints();
	}

	public AgeFactory getAge() {
		return age;
	}
	
	public String getType(){
		return typeSoldier;
	}

	public boolean alive() {
		return soldier.alive();
	}

	public void heal() {
		soldier.heal();
	}

	public float strike() {
		return soldier.strike();
	}

	public boolean parry(float force) {
		return soldier.parry(force);
	}

	public void accept(VisitorClassicForArmedUnit v) {
		v.visit(this);
	}

	public <T> T accept(VisitorFunForArmedUnit<T> v) {
		return v.visit(this);
	}

	
	public int getTeam() {
		return IDTeam;
	}
	
	public float getTotalHealthPoints() {
		return soldier.getTotalHealthPoints();
	}
	
	public void setSelected(boolean a) {
		selected = a;
	}
	
	public boolean getSelected() {
		return selected;
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

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
		
		if(selected) {
			g.setColor(Color.yellow);
			g.drawRect(getPosition().x, getPosition().y, GameConfig.SPRITE_SIZE, GameConfig.SPRITE_SIZE);
		}
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
		}
	}
	
	public void oneStepMove() {
		if(selected || IDTeam != 0)
			super.oneStepMove();
	}
	


	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
	
}
