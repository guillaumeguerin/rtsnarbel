package soldiers.utils;


import java.awt.Canvas;

import soldiers.soldier.*; 
import soldiers.weapon.*;

public class MiddleAgeFactory implements AgeFactory {
	public SoldierAbstract getSimpleSoldier(String name, Canvas defaultCanvas, String image, int IDTeam) {
		return new InfantryMan(name, defaultCanvas, image, IDTeam);
	}
 
	public SoldierAbstract getComplexSoldier(String name, Canvas defaultCanvas, String image, int IDTeam) {
		return new Horseman(name, defaultCanvas, image, IDTeam);
	}
 
	public Soldier getDefensiveWeapon(Soldier s) {
		return new SoldierWithShield(s);
	}
 
	public Soldier getOffensiveWeapon(Soldier s) {
		return new SoldierWithSword(s);
	}

}
