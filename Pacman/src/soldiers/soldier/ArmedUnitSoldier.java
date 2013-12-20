package soldiers.soldier;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import soldiers.utils.*;

public class ArmedUnitSoldier extends ObservableAbstract<ArmedUnit> implements
		ArmedUnit {
	protected Soldier soldier;
	protected List<String> equipments = new ArrayList<String>();
	protected AgeFactory age;

	public ArmedUnitSoldier(AgeFactory factory, String soldatType, String name) {
		this.age = factory;
		String methodName = "get" + soldatType + "Soldier";
		try {
			Method method = factory.getClass().getMethod(methodName,
					String.class);
 			soldier = (SoldierAbstract) method.invoke(factory, name);
		} catch (Exception e) {
			throw new UnknownSoldierTypeException("Unknown soldier type"
					+ e.toString());
		}
	}

	public void addEquipment(String equipmentType) {
		if (alive()) { // XXX "else" not treated
			if (equipments.contains(equipmentType)) {
				return; // decoration not applied
			} else {
				String methodName = "get" + equipmentType + "Weapon";
				try {
					Method method = age.getClass().getMethod(methodName,
							Soldier.class);
					soldier = (Soldier) method.invoke(age, soldier);
					equipments.add(equipmentType);
				} catch (Exception e) {
					throw new RuntimeException("Unknown equipment type "
							+ e.toString());
				}
			}
		}
	}

	public String getName() {
		return soldier.getName();
	}

	public float getHealthPoints() {
		return soldier.getHealthPoints();
	}

	public AgeFactory getAge() {
		return age;
	}

	public boolean alive() {
		return soldier.alive();
	}

	public void heal() {
		soldier.heal();
	}

	public float strike() {
		return soldier.strike();
	}

	public boolean parry(float force) {
		notify(this);
		return soldier.parry(force);
	}

	public void accept(VisitorClassicForArmedUnit v) {
		v.visit(this);
	}

	public <T> T accept(VisitorFunForArmedUnit<T> v) {
		return v.visit(this);
	}

}
