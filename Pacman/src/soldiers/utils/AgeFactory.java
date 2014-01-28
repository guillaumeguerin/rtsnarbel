package soldiers.utils;

import soldiers.soldier.Soldier;
import soldiers.soldier.SoldierAbstract;

public interface AgeFactory {
	SoldierAbstract getSimpleSoldier(String name, int IDteam);
	SoldierAbstract getComplexSoldier(String name, int IDteam);
	Soldier getDefensiveWeapon(Soldier s);
	Soldier getOffensiveWeapon(Soldier s);
}
