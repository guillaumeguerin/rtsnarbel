package soldiers.weapon;
import soldiers.soldier.Soldier;

public class SoldierWithSword extends SoldierArmed<Sword> {

	public SoldierWithSword(Soldier s) {
		super(s, new Sword());
	}
	
	public static String getSwordImage(){
		return "images/sword.png";
	}
}
