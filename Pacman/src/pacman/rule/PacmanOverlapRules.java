package pacman.rule;

import gameframework.base.ObservableValue;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.util.Vector;

import pacman.GameLevelOne;
import pacman.entity.HealthPack;
import pacman.entity.Horse;
import pacman.entity.NonPlayerEntity;
import soldiers.soldier.*;

public class PacmanOverlapRules extends OverlapRulesApplierDefaultImpl {
	
	protected GameUniverse universe;
	protected Vector<NonPlayerEntity> vNonPlayerEntity = new Vector<NonPlayerEntity>();

	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> nb_enemy;
	private final ObservableValue<Boolean> endOfGame;

	public PacmanOverlapRules(ObservableValue<Integer> nb_enemy, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		this.nb_enemy = nb_enemy;
		this.score = score;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}


	public void addNonPlayerEntity(NonPlayerEntity g) {
		vNonPlayerEntity.addElement(g);
	}


/*	public void overlapRule(Knight p, NonPlayerEntity g) {
		if (!p.isVulnerable()) {
			if (g.isActive()) {
				g.setAlive(false);
				MoveStrategyStraightLine strat = new MoveStrategyStraightLine(
						g.getPosition(), ghostStartPos);
				GameMovableDriverDefaultImpl ghostDriv = (GameMovableDriverDefaultImpl) g
						.getDriver();
				ghostDriv.setStrategy(strat);

			}
		} else {
			if (g.isActive()) {
				if (managePacmanDeath) {
					life.setValue(life.getValue() - 1);
					p.setPosition(pacManStartPos);
					for (NonPlayerEntity nonPlayerEntity : vNonPlayerEntity) {
						nonPlayerEntity.setPosition(ghostStartPos);
					}
					managePacmanDeath = false;
				}
			}
		}
	}*/

	public void overlapRule(InfantryMan s, HealthPack hp) {
		System.out.println(s.getName() + " is healed !");
		if(s.getHealthPoints() != s.getTotalHealthPoints())
			s.heal();
		universe.removeGameEntity(hp);
	}

	public void overlapRule(Horseman s, HealthPack hp) {
		System.out.println(s.getName() + " is healed !");
		if(s.getHealthPoints() != s.getTotalHealthPoints())
			s.heal();
		universe.removeGameEntity(hp);
	}

	public void overlapRule(InfantryMan s, Horse h){
		if (s.getTeam()==0){
			System.out.println(s.getName() + " is riding a horse !");
			universe.removeGameEntity(h);
			GameLevelOne.switchInfantryHorseMan(s);
		}
	}
	public void overlapRule(InfantryMan p1, InfantryMan p2) {
		battle(p1, p2);
	}

	public void overlapRule(InfantryMan p1, Horseman p2) {
		battle(p1, p2);
	}

	public void overlapRule(Horseman p1, Horseman p2) {
		battle(p1, p2);
	}

	public void battle(SoldierAbstract p1, SoldierAbstract p2) {
		if(p1.getTeam() != p2.getTeam()) {
			System.out.println(p1.getName() + " is fighting " + p2.getName() + " !");
			p2.parry(p1.strike());
			p1.parry(p2.strike());
			if(!p1.alive()) {
				if(p1.getTeam() != 0) { // He is not in our team
					GameLevelOne.NUMBER_OF_ENEMIES--;
					nb_enemy.setValue(GameLevelOne.NUMBER_OF_ENEMIES);
				}
				universe.removeGameEntity(p1);
				System.out.println(p1.getName() + " got killed !");
				GameLevelOne.addRandomHealthPack();
			}
			if(!p2.alive()) {
				if(p2.getTeam() != 0) {
					GameLevelOne.NUMBER_OF_ENEMIES--;
					nb_enemy.setValue(GameLevelOne.NUMBER_OF_ENEMIES);
				}
				universe.removeGameEntity(p2);
				System.out.println(p2.getName() + " got killed !");
				GameLevelOne.addRandomHealthPack();
			}
		}
		else
			System.out.println(p1.getName() + " is hugging " + p2.getName() + " !");
	}

}
