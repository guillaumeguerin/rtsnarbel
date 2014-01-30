package game.rule;

import game.GameLevelOne;
import game.entity.HealthPack;
import game.entity.Horse;
import game.entity.MouseCursor;
import game.entity.NonPlayerEntity;
import gameframework.base.ObservableValue;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import soldiers.soldier.*;

public class GameOverlapRules extends OverlapRulesApplierDefaultImpl {

	protected GameUniverse universe;
	protected Vector<NonPlayerEntity> vNonPlayerEntity = new Vector<NonPlayerEntity>();

	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> nb_enemy;
	private final ObservableValue<Integer> nb_ally;
	private final ObservableValue<Boolean> endOfGame;

	public GameOverlapRules(ObservableValue<Integer> nb_enemy, ObservableValue<Integer> nb_ally, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		this.nb_enemy = nb_enemy;
		this.nb_ally = nb_ally;
		this.score = score;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}


	public void addNonPlayerEntity(NonPlayerEntity g) {
		vNonPlayerEntity.addElement(g);
	}


	public void overlapRule(ArmedUnitSoldier s, HealthPack hp) {
		System.out.println(s.getName() + " is healed !");
		if(s.getHealthPoints() != s.getTotalHealthPoints())
			s.heal();
		universe.removeGameEntity(hp);
	}


	public void overlapRule(ArmedUnitSoldier s, Horse h){
		if (s.getTeam()==0 && s.getType()=="Simple"){
			System.out.println(s.getName() + " is riding a horse !");
			universe.removeGameEntity(h);
			GameLevelOne.switchInfantryHorseMan(s);
		}
	}
	public void overlapRule(ArmedUnitSoldier p1, ArmedUnitSoldier p2) {
		battle(p1, p2);
	}

	public void overlapRule(MouseCursor m, ArmedUnitSoldier p1) {
		m.setPosition(new Point(0,0));
		if(p1.getTeam() == 0) {
			if(p1.getSelected())
				p1.setSelected(false);
			else
				p1.setSelected(true);
		}
	}


	public void battle(ArmedUnitSoldier p1, ArmedUnitSoldier p2) {
		if(p1.getTeam() != p2.getTeam()) {			
			p2.parry(p1.strike());
			p1.parry(p2.strike());
			ArmedUnitSoldier dead = null;
			ArmedUnitSoldier winner = null;
			if (!p1.alive() || !p2.alive()){
				if(!p1.alive()){
					winner = p2;
					dead = p1;
				}
				else{
					dead = p2;
					winner = p1;
				}
				if (dead.getTeam() != 0){ // He is not in our team
					GameLevelOne.NUMBER_OF_ENEMIES--;
					nb_enemy.setValue(GameLevelOne.NUMBER_OF_ENEMIES);
					GameLevelOne.loot(winner);
				}
				if (dead.getTeam() == 0){ // He is in our team
					GameLevelOne.NUMBER_OF_ALLIES--;
					nb_ally.setValue(GameLevelOne.NUMBER_OF_ALLIES);
				}
				universe.removeGameEntity(dead);
				System.out.println(dead.getName() + " got killed !");
				GameLevelOne.addRandomHealthPack();
			}
		}

		/*else
			System.out.println(p1.getName() + " is hugging " + p2.getName() + " !");*/
	}


}
