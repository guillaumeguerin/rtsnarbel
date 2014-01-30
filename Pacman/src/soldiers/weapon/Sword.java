package soldiers.weapon;
public class Sword extends WeaponAbstract {
	private static final float DEFENSE = 1;
	private static final float ATTACK = 12;
	private static final float RESISTANCE = 1;

	public Sword() {
		super(DEFENSE, ATTACK, RESISTANCE);
	}

	public void fix() {
		resistance = RESISTANCE;
	}
}
