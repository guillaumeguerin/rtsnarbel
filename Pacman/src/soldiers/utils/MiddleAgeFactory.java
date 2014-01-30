package soldiers.utils;


import soldiers.soldier.*; 
import soldiers.weapon.*;

public class MiddleAgeFactory implements AgeFactory {
	public SoldierAbstract getSimpleSoldier(String name) {
		return new InfantryMan(name);
	}
 
	public SoldierAbstract getComplexSoldier(String name) {
		return new Horseman(name);
	}
 
	public Soldier getDefensiveWeapon(Soldier s) {
		return new SoldierWithShield(s);
	}
 
	public Soldier getOffensiveWeapon(Soldier s) {
		return new SoldierWithSword(s);
	}
	
	public String getDefensiveImage(){
		return SoldierWithShield.getShieldImage();
	}
	
	public String getOffensiveImage(){
		return SoldierWithSword.getSwordImage();
	}

}
