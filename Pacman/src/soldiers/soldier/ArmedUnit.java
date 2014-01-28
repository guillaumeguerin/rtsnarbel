package soldiers.soldier;

import soldiers.utils.VisitorClassicForArmedUnit;
import soldiers.utils.VisitorFunForArmedUnit;
import soldiers.utils.AgeFactory;

public interface ArmedUnit {
//public abstract class ArmedUnit {
	public String getName() ;
	public float getHealthPoints() ;
	public AgeFactory getAge() ;
	public boolean alive() ;
	public void heal();
	public boolean parry(float force) ; // true if the receiver is still alive after the strike
	public float strike() ;
	public void addEquipment(String weaponType) ;
	public void accept(VisitorClassicForArmedUnit v) ;
	public <T> T accept(VisitorFunForArmedUnit<T> v) ;
	/*
	public String getName() {
		return null;
	}
	public float getHealthPoints() {
		return 0;
	}
	public AgeFactory getAge() {
		return null;
	}
	public boolean alive() {
		return false;
	}
	public void heal() {
	}
	public boolean parry(float force) {
		return false;
	} // true if the receiver is still alive after the strike
	public float strike() {
		return 0;
	}
	public void addEquipment(String weaponType) {
	}  
	public void accept(VisitorClassicForArmedUnit v) {
	}
	public <T> T accept(VisitorFunForArmedUnit<T> v) {
		return null;
	}
	*/
}