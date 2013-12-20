package soldiers.weapon;

import soldiers.soldier.Soldier;

/**
 * @param <W>
 *            Express the link with a specific type of weapon (e.g.,
 *            SoldierWithShield is linked with a Shield, not any kind of
 *            weapon).
 */
public abstract class SoldierArmed<W extends Weapon> implements Soldier {
	protected Soldier soldier;
	protected W weapon;
	private static final float WEARINESS_COEF = 0; // XXX temporarily inhibited

	SoldierArmed(Soldier s, W a) {
		soldier = s;
		weapon = a;
	}

	public String getName() {
		return soldier.getName();
	}

	public float getHealthPoints() {
		return soldier.getHealthPoints();
	}

	public boolean alive() {
		return soldier.alive();
	}

	public void heal() {
		soldier.heal();
	}

	public void fixWeapon() {
		weapon.fix();
	}

	public boolean parry(float force) {
		float effectiveForce = force - weapon.getParryValue();
		if (effectiveForce <= 1) effectiveForce = 1;
		//Effective force is at least 1 (weariness effect)
		weapon.damageCompute(WEARINESS_COEF);
		return soldier.parry(effectiveForce);
	}

	public float strike() {
		float force = soldier.strike() + weapon.getStrikeValue();
		weapon.damageCompute(WEARINESS_COEF);
		return force;
	}
}
