package soldiers.utils;

import java.util.ArrayList;
import java.util.List;

import soldiers.soldier.ArmedUnit;

public class ObserverDeadNames implements Observer<ArmedUnit> {

	private static ObserverDeadNames uniqueInstance;

	private ObserverDeadNames() { //inhibition of the default constructor
	}

	public static synchronized ObserverDeadNames getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ObserverDeadNames();
		return uniqueInstance;
	}
	
	//for memory purpose:
	protected List<String> deads = new ArrayList<String>(); //for memory purpose

	public void update(ArmedUnit s) {
		if ((!s.alive()) && (!deads.contains(s.getName()))) {
			deads.add(s.getName());
			System.out.println(s.getName() + " is dead");
		}
	}
}
