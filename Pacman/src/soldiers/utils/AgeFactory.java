package soldiers.utils;

import java.awt.Canvas;

import soldiers.soldier.Soldier;
import soldiers.soldier.SoldierAbstract;

public interface AgeFactory {
	SoldierAbstract getSimpleSoldier(String name);
	SoldierAbstract getComplexSoldier(String name);
	Soldier getDefensiveWeapon(Soldier s);
	Soldier getOffensiveWeapon(Soldier s);
}
