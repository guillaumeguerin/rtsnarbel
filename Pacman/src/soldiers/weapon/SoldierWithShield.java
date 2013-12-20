package soldiers.weapon;
import soldiers.soldier.Soldier;

public class SoldierWithShield extends SoldierArmed<Shield> {

	public SoldierWithShield(Soldier s) {
		super(s, new Shield());
	}
}
