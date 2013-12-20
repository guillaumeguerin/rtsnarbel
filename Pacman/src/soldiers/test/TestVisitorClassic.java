package soldiers.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import soldier.*; 
import soldiers.soldier.ArmedUnit;
import soldiers.soldier.ArmedUnitSoldier;
import soldiers.soldier.ArmedUnitSquad;
import soldiers.utils.*; 

	
public class TestVisitorClassic {
	@Test
	public void visitors() throws Exception {
		ArmedUnitSquad squad1, squad2;
		ArmedUnit sf1, sf2, sc1, sc2, s;
		AgeFactory age;

	    age = new MiddleAgeFactory();
		squad1 = new ArmedUnitSquad(age, "Lincoln");
		squad2 = new ArmedUnitSquad(age, "Kieffer");
		sf1 = new ArmedUnitSoldier(age, "Simple", "Gogol1");
		sf2 = new ArmedUnitSoldier(age, "Simple", "Gogol2");
		sc1 = new ArmedUnitSoldier(age, "Complex", "Sanchez1");
		sc2 = new ArmedUnitSoldier(age, "Complex", "Sanchez2");
		s = new ArmedUnitSoldier(age, "Complex", "St Georges");

		squad1.addUnit(sf1);
		squad1.addUnit(sf2);
		squad2.addUnit(sc1);
		squad2.addUnit(sc2);
		squad2.addUnit(s);

		VisitorClassicNamer v = new VisitorClassicNamer();
		squad1.accept(v);
		assertEquals("Squad Lincoln : \nInfantryMan Gogol1\nInfantryMan Gogol2\n", v.getNames());
        v.reset();		
		squad2.accept(v);
		assertEquals("Squad Kieffer : \nInfantryMan Sanchez1\nInfantryMan Sanchez2\nInfantryMan St Georges\n", v.getNames());

		VisitorClassicCounter vi = new VisitorClassicCounter();
		squad1.accept(vi);
		assertEquals((Integer)2, vi.getCount());
		vi.reset();
		squad2.accept(vi);
		assertEquals((Integer)3, vi.getCount());
	}


}
 