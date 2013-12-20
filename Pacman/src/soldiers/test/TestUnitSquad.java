package soldiers.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import soldiers.soldier.ArmedUnit;
import soldiers.soldier.ArmedUnitSquad;
import soldiers.soldier.ArmedUnitSoldier;
import soldiers.utils.AgeFactory;
import soldiers.utils.MiddleAgeFactory;

public class TestUnitSquad {
	ArmedUnitSquad squad;
	ArmedUnit sf1, sf2, sc1, sc2, s;
	AgeFactory age;
	
	@Before
	public void setUp() throws Exception {
		age = new MiddleAgeFactory();
		squad = new ArmedUnitSquad(age, "Lincoln");
		sf1 = new ArmedUnitSoldier(age, "Simple", "Gogol1");
		sf2 = new ArmedUnitSoldier(age, "Simple", "Gogol2");
		sc1 = new ArmedUnitSoldier(age, "Complex", "Sanchez1");
		sc2 = new ArmedUnitSoldier(age, "Complex", "Sanchez2");
		s = new ArmedUnitSoldier(age, "Complex", "St Georges");

		squad.addUnit(sf1);
		squad.addUnit(sf2);
		squad.addUnit(sc1);
		squad.addUnit(sc2);
	}

	@Test
	public void combats() {
		int i;
		s.addEquipment("Offensive");
 		for (i = 0; squad.parry(s.strike()); i++) {
 			;
 		}
 		assertEquals("Unexpected death of squad " + squad.getName(), i, 12);

		squad.heal();
		squad.addEquipment("Defensive");
		s.heal();

 		for (i = 0; squad.parry(s.strike()); i++) {
 			;
 		}
 		assertEquals("Unexpected death of squad " + squad.getName()
 				+ " with shield", i, 102);
	}
}
