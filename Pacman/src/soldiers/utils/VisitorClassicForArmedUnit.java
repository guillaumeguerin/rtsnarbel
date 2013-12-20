package soldiers.utils;
import soldiers.soldier.ArmedUnit;
import soldiers.soldier.ArmedUnitSquad;

public interface VisitorClassicForArmedUnit {
	void visit(ArmedUnit s);
	void visit(ArmedUnitSquad a);
}
 