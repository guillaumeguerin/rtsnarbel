package soldiers.utils;

import soldiers.soldier.*; 
import soldiers.weapon.*;

public class MiddleAgeFactory implements AgeFactory {
	public SoldierAbstract getSimpleSoldier(String name, int IDteam) {
		return new InfantryMan(name, IDteam);
	}
 
	public SoldierAbstract getComplexSoldier(String name, int IDteam) {
		return new Horseman(name, IDteam);
	}
 
	public Soldier getDefensiveWeapon(Soldier s) {
		return new SoldierWithShield(s);
	}
 
	public Soldier getOffensiveWeapon(Soldier s) {
		return new SoldierWithSword(s);
	}
}
