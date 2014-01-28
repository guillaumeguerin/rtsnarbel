package pacman.rule;

import gameframework.base.ObservableValue;
import gameframework.base.MoveStrategyRandom;
import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.Overlap;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import pacman.entity.Ghost;
import pacman.entity.HealthPack;
import pacman.entity.Horse;
import pacman.entity.Jail;
import pacman.entity.Knight;
import pacman.entity.NonPlayerEntity;
import pacman.entity.Pacgum;
import pacman.entity.Pacman;
import pacman.entity.SuperPacgum;
import pacman.entity.TeleportPairOfPoints;
import soldiers.soldier.Horseman;
import soldiers.soldier.*;

public class PacmanOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Vector<NonPlayerEntity> vNonPlayerEntity = new Vector<NonPlayerEntity>();

	// Time duration during which pacman is invulnerable and during which ghosts
	// can be eaten (in number of cycles)
	static final int INVULNERABLE_DURATION = 60;
	protected Point pacManStartPos;
	protected Point ghostStartPos;
	protected boolean managePacmanDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Boolean> endOfGame;
	private int totalNbGums = 0;
	private int nbEatenGums = 0;

	public PacmanOverlapRules(Point pacPos, Point gPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		pacManStartPos = (Point) pacPos.clone();
		ghostStartPos = (Point) gPos.clone();
		this.life = life;
		this.score = score;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	public void setTotalNbGums(int totalNbGums) {
		this.totalNbGums = totalNbGums;
	}

	public void addNonPlayerEntity(NonPlayerEntity g) {
		vNonPlayerEntity.addElement(g);
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		managePacmanDeath = true;
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(Knight p, NonPlayerEntity g) {
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
	}

	public void overlapRule(NonPlayerEntity g, SuperPacgum spg) {
	}

	public void overlapRule(NonPlayerEntity g, Pacgum spg) {
	}

	public void overlapRule(NonPlayerEntity g, TeleportPairOfPoints teleport) {
		g.setPosition(teleport.getDestination());
	}

	public void overlapRule(Knight p, TeleportPairOfPoints teleport) {
		p.setPosition(teleport.getDestination());
	}

	public void overlapRule(NonPlayerEntity g, Jail jail) {
		if (!g.isActive()) {
			g.setAlive(true);
			MoveStrategyRandom strat = new MoveStrategyRandom();
			GameMovableDriverDefaultImpl ghostDriv = (GameMovableDriverDefaultImpl) g
					.getDriver();
			ghostDriv.setStrategy(strat);
			g.setPosition(ghostStartPos);
		}
	}

	public void overlapRule(Knight p, SuperPacgum spg) {
		score.setValue(score.getValue() + 5);
		universe.removeGameEntity(spg);
		pacgumEatenHandler();
		p.setInvulnerable(INVULNERABLE_DURATION);
		for (NonPlayerEntity nonPlayer : vNonPlayerEntity) {
			nonPlayer.setAfraid(INVULNERABLE_DURATION);
		}
	}

	public void overlapRule(Knight p, Pacgum pg) {
		score.setValue(score.getValue() + 1);
		universe.removeGameEntity(pg);
		pacgumEatenHandler();
	}

	/*public void overlapRule(Knight p, HealthPack hp) {
		//score.setValue(score.getValue() + 1);
		System.out.println(p.getName() + " is healed !");
		if(p.getHealthPoints() != p.getTotalHealthPoints())
			p.heal();
		universe.removeGameEntity(hp);
		//pacgumEatenHandler();
	}*/
	
	/*public void overlapRule(Knight p, Horse h) {
		System.out.println(p.getName() + " is riding a horse !");
		universe.removeGameEntity(h);
		p.setSoldier(new Horseman(p.getName()));
	}*/ // Delete it
	
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
		System.out.println(s.getName() + " is riding a horse !");
		universe.removeGameEntity(h);
		//TODO appeler la fonction pour qu'il devienne un horseman
	}
	
	private void pacgumEatenHandler() {
		nbEatenGums++;
		if (nbEatenGums >= totalNbGums) {
			endOfGame.setValue(true);
		}
	}
}
