package soldiers.soldier;

public class InfantryMan extends SoldierAbstract {
	private static final int FORCE = 15;
	private static final int HEALTHPOINTS = 100;

	public InfantryMan(String nom, int IDTeam) {
		super(nom, HEALTHPOINTS, FORCE, IDTeam);
	}

	public void heal() { //XXX resurrection allowed
		healthPoints = HEALTHPOINTS;
	}
}		