package pacman;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.MoveStrategyRandom;
import gameframework.extended.MoveStrategyKeyboardStop;
import gameframework.extended.MoveStrategyMouseSelect;
import gameframework.extended.MoveStrategyMouseStop;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameConfig;
import gameframework.game.GameLevelDefaultImpl;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;
import java.util.Random;

import pacman.entity.Castle;
import pacman.entity.Ghost;
import pacman.entity.Grass;
import pacman.entity.HealthPack;
import pacman.entity.Horse;
import pacman.entity.House;
import pacman.entity.Jail;
import pacman.entity.Knight;
import pacman.entity.Pacgum;
import pacman.entity.Pacman;
import pacman.entity.SuperPacgum;
import pacman.entity.TeleportPairOfPoints;
import pacman.entity.Tree;
import pacman.entity.Water;
import pacman.rule.GhostMovableDriver;
import pacman.rule.PacmanMoveBlockers;
import pacman.rule.PacmanOverlapRules;
import soldiers.soldier.Horseman;
import soldiers.soldier.InfantryMan;
import soldiers.soldier.Soldier;
import soldiers.soldier.SoldierAbstract;

public class GameLevelOne extends GameLevelDefaultImpl {
	static Canvas canvas;

	public static final int SPRITE_SIZE = GameConfig.SPRITE_SIZE;
	public static final int NUMBER_OF_HORSES = 2;
	public static int NUMBER_OF_ENEMIES = 5;
	public static int HEALTHPACK_DROP_RATE = 5;

	
	
	@Override
	protected void init() {
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new PacmanMoveBlockers());
		
		PacmanOverlapRules overlapRules = new PacmanOverlapRules(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE),
				new Point(14 * SPRITE_SIZE, 15 * SPRITE_SIZE), enemy[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

		int totalNbGums = 0;
		int totalNbHealthPack = 5;
		int [][] tab = GameMap.createRandomMap();
		
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < 31; ++i) {
			for (int j = 0; j < 28; ++j) {
				if (tab[i][j] == 0) {
					universe.addGameEntity(new Grass(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}
				if (tab[i][j] == 1) {
					universe.addGameEntity(new Water(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}
				if (tab[i][j] == 2) {
					universe.addGameEntity(new Tree(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}
				if (tab[i][j] == 3) {
					universe.addGameEntity(new House(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}
				if (tab[i][j] == 4) {
					universe.addGameEntity(new Castle(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}
				/*if (tab[i][j] == 7) {
					universe.addGameEntity(new SuperPacgum(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
					totalNbGums++;
				}*/
				/*if (tab[i][j] == 8) {
					universe.addGameEntity(new Jail(new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}*/
			}
		}
		
		Random generator = new Random();
		for(int i=0; i<totalNbHealthPack; i++) {
			universe.addGameEntity(new HealthPack(canvas, new Point((generator.nextInt(GameConfig.NB_ROWS -2) +1) * SPRITE_SIZE, (generator.nextInt(GameConfig.NB_COLUMNS -2)+1) *  SPRITE_SIZE)));
		}
		
		overlapRules.setTotalNbGums(totalNbGums);

		// Two teleport points definition and inclusion in the universe
		// (west side to east side)
		universe.addGameEntity(new TeleportPairOfPoints(new Point(0 * SPRITE_SIZE, 14 * SPRITE_SIZE), new Point(
				25 * SPRITE_SIZE, 14 * SPRITE_SIZE)));
		// (east side to west side)
		universe.addGameEntity(new TeleportPairOfPoints(new Point(27 * SPRITE_SIZE, 14 * SPRITE_SIZE), new Point(
				2 * SPRITE_SIZE, 14 * SPRITE_SIZE)));
		
		
		// Pacman definition and inclusion in the universe
		SoldierAbstract myPac = new InfantryMan("toto", canvas, "images/knight2.png", 0);
		GameMovableDriverDefaultImpl pacDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboardStop keyStr = new MoveStrategyKeyboardStop();
		//MoveStrategyMouseSelect mouseStr = new MoveStrategyMouseSelect();
		pacDriver.setStrategy(keyStr);
		//pacDriver.setStrategy(mouseStr);
		pacDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStr);
		//canvas.addMouseListener(mouseStr);
		myPac.setDriver(pacDriver);
		myPac.setPosition(new Point((GameConfig.NB_COLUMNS/2 - 3) * SPRITE_SIZE, (GameConfig.NB_ROWS -5) * SPRITE_SIZE));
		universe.addGameEntity(myPac);
		
		/* Enemies */

		SoldierAbstract enemy;
		
		for(int i=0; i< NUMBER_OF_ENEMIES; i++) {
			enemy = new Horseman("Enemy " + (i+1), canvas, "images/knight1.png", 1);
			Point pos = new Point((generator.nextInt(GameConfig.NB_ROWS -2)+1) * SPRITE_SIZE, ((generator.nextInt(GameConfig.NB_COLUMNS/2)-2)+1) * SPRITE_SIZE);
			if(pos.x <1)
				pos.x = 1 * SPRITE_SIZE;
			if(pos.y <1)
				pos.y = 1 * SPRITE_SIZE;
			enemy.setPosition(pos);
			GameMovableDriverDefaultImpl enemyDriv = new GhostMovableDriver();
			MoveStrategyRandom ranStr = new MoveStrategyRandom();
			enemyDriv.setStrategy(ranStr);
			enemyDriv.setmoveBlockerChecker(moveBlockerChecker);
			universe.addGameEntity(enemy);
		}
		
		 
		
		SoldierAbstract myPac3 = new Horseman("Peach", canvas, "images/princess1.png", 1);
		myPac3.setPosition(new Point(15 * SPRITE_SIZE, 16 * SPRITE_SIZE));
		universe.addGameEntity(myPac3);
		
		// Horse definition and inclusion in the universe
		Horse myHorse;
		for (int t = 0; t < NUMBER_OF_HORSES; ++t) {
			GameMovableDriverDefaultImpl ghostDriv = new GhostMovableDriver();
			MoveStrategyRandom ranStr = new MoveStrategyRandom();
			ghostDriv.setStrategy(ranStr);
			ghostDriv.setmoveBlockerChecker(moveBlockerChecker);
			myHorse = new Horse(canvas);
			myHorse.setDriver(ghostDriv);
			myHorse.setPosition(new Point(14 * SPRITE_SIZE, 15 * SPRITE_SIZE));
			universe.addGameEntity(myHorse);
			(overlapRules).addNonPlayerEntity(myHorse);
		}


	}
	
	public static void addRandomHealthPack() {
		Random generator = new Random();
		if(generator.nextInt(HEALTHPACK_DROP_RATE) == 0) {
			Point p = new Point((generator.nextInt(GameConfig.NB_ROWS -2)+1)*GameConfig.NB_ROWS, (generator.nextInt(GameConfig.NB_COLUMNS -2)+1)*GameConfig.NB_COLUMNS);
			HealthPack hp = new HealthPack(canvas, p);
			universe.addGameEntity(hp);
		}
	}
	
	public GameLevelOne(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

}
