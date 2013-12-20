package soldiers.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import soldiers.soldier.ArmedUnit;
import soldiers.soldier.ArmedUnitSoldier;
import soldiers.soldier.UnknownSoldierTypeException;
import soldiers.utils.*;

public class TestUnitSoldier {
	ArmedUnit sf, sc;
    AgeFactory age; 
    
	@Before
	public void setUp() throws Exception {
		age = new MiddleAgeFactory();
		sf = new ArmedUnitSoldier(age, "Simple", "Gogol");
		sc = new ArmedUnitSoldier(age, "Complex", "Sanchez");
	}

	@Test(expected = UnknownSoldierTypeException.class)
	public void combat() {
		int i;
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of squad " + sf.getName(), i, 4);

		sf.heal();
 		sf.addEquipment("Defensive");
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of  " + sf.getName() + " with shield", i, 9);

		sf.heal();
		sf.addEquipment("Offensive");
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of " + sf.getName() + " with shield and sword", i, 11);
 		 
        sf.heal();
		for (i = 0; sc.parry(sf.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of " + sc.getName(), i, 3);
 
 		new ArmedUnitSoldier(age, "Poilu", "Gogol"); //exception raised : unknown soldier type
	}
}
