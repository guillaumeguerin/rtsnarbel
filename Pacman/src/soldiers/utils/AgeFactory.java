package soldiers.utils;

import java.awt.Canvas;

import soldiers.soldier.Soldier;
import soldiers.soldier.SoldierAbstract;

public interface AgeFactory {
	SoldierAbstract getSimpleSoldier(String name, Canvas defaultCanvas, String image);
	SoldierAbstract getComplexSoldier(String name, Canvas defaultCanvas, String image);
	Soldier getDefensiveWeapon(Soldier s);
	Soldier getOffensiveWeapon(Soldier s);
}
