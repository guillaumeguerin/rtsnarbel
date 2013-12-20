package soldiers.soldier;

import java.util.ArrayList;
import java.util.List;

import soldiers.utils.AgeFactory;
import soldiers.utils.ObservableAbstract;
import soldiers.utils.VisitorClassicForArmedUnit;
import soldiers.utils.VisitorFunForArmedUnit;

public class ArmedUnitSquad extends ObservableAbstract<ArmedUnit> implements
		ArmedUnit {
	protected List<ArmedUnit> armedUnitList = new ArrayList<ArmedUnit>();
	protected String name;
	protected AgeFactory age;

	public ArmedUnitSquad(AgeFactory factory, String name) {
		this.age = factory;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addUnit(ArmedUnit s) {
		if (s.getAge() == age) // historical coherence checked
			armedUnitList.add(s);
		else
			throw new BadAgeException();
	}

	public float getHealthPoints() {
		float points = 0;
		for (ArmedUnit s : armedUnitList) {
			points += s.getHealthPoints();
		}
		return points;
	}

	public AgeFactory getAge() {
		return age;
	}

	public boolean alive() {
		boolean alive = false;
		for (ArmedUnit s : armedUnitList) {
			alive = alive || (s.alive());
		}
		return alive;
	}

	public int getStillAliveSoldiers() {
		int stillAlive = 0;
		for (ArmedUnit s : armedUnitList) {
			if (s.alive())
				stillAlive += 1;
		}
		return stillAlive;
	}

	public void heal() {
		for (ArmedUnit s : armedUnitList) {
			s.heal();
		}
	}

	public void addEquipment(String equipmentType) {
		if (alive()) {
			// Every soldier of a unit gets the same equipment
			for (ArmedUnit s : armedUnitList) {
				if (s.alive())
					s.addEquipment(equipmentType);
			}
		}
	}

	public boolean parry(float force) {
		boolean result = false;
		if (alive()) {
			float forcePart = force / getStillAliveSoldiers();
			// Each alive soldier takes an equal part in each strike
			for (ArmedUnit s : armedUnitList) {
				result = (s.parry(forcePart)) || result;
			}
		}
		notify(this);
		return result;
	}

	public float strike() {
		float result = 0;
		if (alive()) {
			for (ArmedUnit s : armedUnitList) {
				result += s.strike();
			}
		}
		return result;
	}

	public void accept(VisitorClassicForArmedUnit v) {
		v.visit(this);
		for (ArmedUnit s : armedUnitList) {
			s.accept(v);
		}
	}

	public <T> T accept(VisitorFunForArmedUnit<T> v) {
		T result = v.visit(this);
		for (ArmedUnit s : armedUnitList) {
			result = v.compos(result, s.accept(v));
		}
		return result;
	}

}
