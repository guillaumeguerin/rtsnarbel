package pacman.rule;

import gameframework.base.ObservableValue;
import gameframework.base.MoveStrategyRandom;
import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.Overlap;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import pacman.GameLevelOne;
import pacman.entity.Ghost;
import pacman.entity.HealthPack;
import pacman.entity.Horse;
import pacman.entity.Jail;
import pacman.entity.Knight;
import pacman.entity.MouseCursor;
import pacman.entity.NonPlayerEntity;
import pacman.entity.Pacgum;
import pacman.entity.Pacman;
import pacman.entity.SuperPacgum;
import pacman.entity.TeleportPairOfPoints;
import soldiers.soldier.Horseman;

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

	public void overlapRule(Knight p, HealthPack hp) {
		//score.setValue(score.getValue() + 1);
		System.out.println(p.getName() + " is healed !");
		if(p.getHealthPoints() != p.getTotalHealthPoints())
			p.heal();
		universe.removeGameEntity(hp);
		//pacgumEatenHandler();
	}
	
	
	public void overlapRule(Knight p, Horse h) {
		System.out.println(p.getName() + " is riding a horse !");
		universe.removeGameEntity(h);
		p.setSoldier(new Horseman(p.getName(), p.getTeam()));
	}
	
	public void overlapRule(Knight p1, Knight p2) {
		Random generator = new Random();
		if(p1.getTeam() != p2.getTeam()) {
			System.out.println(p1.getName() + " is fighting " + p2.getName() + " !");
			p2.parry(p1.strike());
			p1.parry(p2.strike());
			if(!p1.alive()) {
				if(p1.getTeam() != 0) { // He is not in our team
					GameLevelOne.NUMBER_OF_ENEMIES--;
					life.setValue(GameLevelOne.NUMBER_OF_ENEMIES);
				}
				universe.removeGameEntity(p1);
				System.out.println(p1.getName() + " got killed !");
				GameLevelOne.addRandomHealthPack();
			}
			if(!p2.alive()) {
				if(p2.getTeam() != 0) {
					GameLevelOne.NUMBER_OF_ENEMIES--;
					life.setValue(GameLevelOne.NUMBER_OF_ENEMIES);
				}
				universe.removeGameEntity(p2);
				System.out.println(p2.getName() + " got killed !");
				GameLevelOne.addRandomHealthPack();
			}
		}
		else
			System.out.println(p1.getName() + " is hugging " + p2.getName() + " !");
	}
	
	public void overlapRule(MouseCursor m, Knight p1) {
		
		m.setPosition(new Point(0,0));
		if(p1.getTeam() == 0) {
			if(p1.getSelected())
				p1.setSelected(false);
			else
				p1.setSelected(true);
		}

	}
	
	private void pacgumEatenHandler() {
		nbEatenGums++;
		if (nbEatenGums >= totalNbGums) {
			endOfGame.setValue(true);
		}
	}
}
