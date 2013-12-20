package soldiers.weapon;

public abstract class WeaponAbstract implements Weapon {
	protected float defense;
	protected float attack;
	protected float resistance; //multiplicative coef for weapon resistance to damage

	public WeaponAbstract(float defense, float attack, float resistance) {
		this.defense = defense;
		this.attack = attack;
		this.resistance = resistance;
	}

	public float getParryValue() {
		return defense * resistance;
	}

	public float getStrikeValue() {
		return attack * resistance;
	}

	public float getResistanceToDamage() {
		return resistance;
	}
	
	public void damageCompute(float coef) {
		resistance -= coef;
		if (resistance < 0) resistance = 0;
	}
}

