package game;

import game.entity.Castle;
import game.entity.Grass;
import game.entity.HealthPack;
import game.entity.Horse;
import game.entity.House;
import game.entity.MouseCursor;
import game.entity.Tree;
import game.entity.Water;
import game.rule.EnemyMovableDriver;
import game.rule.GameMoveBlockers;
import game.rule.GameOverlapRules;
import gameframework.base.MoveStrategy;
import gameframework.base.MoveStrategyMouse;
import gameframework.base.MoveStrategyRandom;
import gameframework.extended.MoveStrategyKeyboardStop;
import gameframework.extended.MoveStrategyMouseSelect;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameConfig;
import gameframework.game.GameEntity;
import gameframework.game.GameLevelDefaultImpl;
import gameframework.game.GameMovable;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameMovableMouseDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;
import java.util.Random;

import soldiers.soldier.Horseman;
import soldiers.soldier.InfantryMan;
import soldiers.soldier.SoldierAbstract;

public class GameLevelOne extends GameLevelDefaultImpl {
	static Canvas canvas;

	public static final int SPRITE_SIZE = GameConfig.SPRITE_SIZE;
	public static final int NUMBER_OF_HORSES = 2;
	public static int NUMBER_OF_ENEMIES = 4;
	public static int NUMBER_OF_ALLIES = 4;
	public static int HEALTHPACK_DROP_RATE = 5;
	public static int NB_PLAYER = 1;
	private static int TOTAL_NB_HEALTH_PACK = 5;

	static MoveBlockerChecker moveBlockerChecker;

	@Override
	protected void init() {

		// Check if the number of object is less than the available space
		if (NUMBER_OF_HORSES + NUMBER_OF_ENEMIES + NUMBER_OF_ALLIES + TOTAL_NB_HEALTH_PACK + GameMap.NB_CASTLES + GameMap.NB_HOUSES + GameMap.NB_TREES + NB_PLAYER <= (GameConfig.NB_COLUMNS-2)*(GameConfig.NB_ROWS-2)){
			
			Random generator = new Random();
			
			OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

			moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
			moveBlockerChecker.setMoveBlockerRules(new GameMoveBlockers());

			GameOverlapRules overlapRules = new GameOverlapRules(enemy[0], ally[0], score[0], endOfGame);
			overlapProcessor.setOverlapRules(overlapRules);

			universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
			overlapRules.setUniverse(universe);

			gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
			((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);


			int [][] map = GameMap.createRandomMap();

			// Filling up the universe with basic non movable entities and inclusion in the universe
			for (int i = 0; i < 31; ++i) {
				for (int j = 0; j < 28; ++j) {
					if (map[i][j] == 0) {
						universe.addGameEntity(new Grass(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
					}
					if (map[i][j] == 1) {
						universe.addGameEntity(new Water(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
					}
					if (map[i][j] == 2) {
						universe.addGameEntity(new Tree(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
					}
					if (map[i][j] == 3) {
						universe.addGameEntity(new House(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
					}
					if (map[i][j] == 4) {
						universe.addGameEntity(new Castle(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
					}
				}
			}


			MouseCursor myMouse = new MouseCursor(canvas);
			MoveStrategyMouseSelect mouse = new MoveStrategyMouseSelect();
			GameMovableMouseDriverDefaultImpl mouseDriver = new GameMovableMouseDriverDefaultImpl();
			mouseDriver.setStrategy(mouse);
			//mouseDriver.setmoveBlockerChecker(moveBlockerChecker);
			canvas.addMouseListener(mouse);
			myMouse.setDriver(mouseDriver);
			universe.addGameEntity(myMouse);

			
			/*// Hero definition and inclusion in the universe.
			SoldierAbstract myHero = new InfantryMan("toto", canvas, "images/knight2.png", 0);
			myHero.setPosition(new Point((GameConfig.NB_COLUMNS/2 - 3) * SPRITE_SIZE, (GameConfig.NB_ROWS -5) * SPRITE_SIZE));
			MoveStrategyKeyboardStop keyStr = new MoveStrategyKeyboardStop();			
			canvas.addKeyListener(keyStr);
			universe.addGameEntity(initMovableState(myHero, new GameMovableDriverDefaultImpl(), keyStr));			*/
			
			
			
			// Hero definition and inclusion in the universe.
			/*SoldierAbstract myHero = new InfantryMan("toto", canvas, "images/knight2.png", 0);
			myHero.setPosition(new Point((GameConfig.NB_COLUMNS/2 - 3) * SPRITE_SIZE, (GameConfig.NB_ROWS -5) * SPRITE_SIZE));
			MoveStrategyMouse mouseStr = new MoveStrategyMouse(myHero);			
			canvas.addMouseListener(mouseStr);
			universe.addGameEntity(initMovableState(myHero, new GameMovableDriverDefaultImpl(), mouseStr));			*/
			
			SoldierAbstract ally;
			for(int i=0; i< NUMBER_OF_ALLIES; ++i) {
				ally = new InfantryMan("Ally " + (i+1), canvas, "images/knight2.png", 0);
				
				Point pos = new Point((generator.nextInt(GameConfig.NB_COLUMNS -2)+1) * SPRITE_SIZE, ((((generator.nextInt(GameConfig.NB_ROWS)-2)+1)/2)+GameConfig.NB_ROWS/2) * SPRITE_SIZE);
				ally.setPosition(pos);
				
				while( map [ ((int)ally.getPosition().getY()/SPRITE_SIZE) ][ ((int)ally.getPosition().getX()/SPRITE_SIZE) ] != 0 ){
					pos.setLocation( (1+generator.nextInt(GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE, ( ( (1+generator.nextInt(GameConfig.NB_ROWS)-2) )/2 ) * SPRITE_SIZE);
					ally.setPosition(pos);
				}	
				
				MoveStrategyMouse mouseStr = new MoveStrategyMouse(ally);			
				canvas.addMouseListener(mouseStr);
				
				universe.addGameEntity(initMovableState(ally, new GameMovableDriverDefaultImpl(), mouseStr));
			}
			
			
			
			// HealthPack definition and inclusion in the universe.
			for(int i=0; i<TOTAL_NB_HEALTH_PACK; ++i) {
				universe.addGameEntity(new HealthPack(canvas, defineInitLocation(map, generator)));
			}
			
			// Enemies definition and inclusion in the universe.
			SoldierAbstract enemy;
			for(int i=0; i< NUMBER_OF_ENEMIES; ++i) {
				enemy = new InfantryMan("Enemy " + (i+1), canvas, "images/knight1.png", 1);
				
				Point pos = new Point((generator.nextInt(GameConfig.NB_COLUMNS -2)+1) * SPRITE_SIZE, (((generator.nextInt(GameConfig.NB_ROWS)-2)+1)/2) * SPRITE_SIZE);
				if(pos.getY()/SPRITE_SIZE < 1)
					pos.setLocation(pos.getX(), 1*SPRITE_SIZE);
				enemy.setPosition(pos);
				
				while( map [ ((int)enemy.getPosition().getY()/SPRITE_SIZE) ][ ((int)enemy.getPosition().getX()/SPRITE_SIZE) ] != 0 ){
					pos.setLocation( (1+generator.nextInt(GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE, ( ( (1+generator.nextInt(GameConfig.NB_ROWS)-2) )/2 ) * SPRITE_SIZE);
					if(pos.getY()/SPRITE_SIZE < 1)
						pos.setLocation(pos.getX(), 1*SPRITE_SIZE);
					enemy.setPosition(pos);
				}		
				
				universe.addGameEntity(initMovableState(enemy, new EnemyMovableDriver(), new MoveStrategyRandom()));
			}
			
			// Horse definition and inclusion in the universe
			Horse myHorse;
			for (int t = 0; t < NUMBER_OF_HORSES; ++t) {
				myHorse = new Horse(canvas);
				myHorse.setPosition(defineInitLocation(map, generator));
				
				universe.addGameEntity(initMovableState(myHorse, new EnemyMovableDriver(), new MoveStrategyRandom()));
				//TODO à enlever lors de la suppression de la classe NON_PLAYER_ENTITY
				(overlapRules).addNonPlayerEntity(myHorse);
			}
		}
	}

	private Point defineInitLocation(int[][]map, Random generator){
		Point pos = new Point((generator.nextInt(1+GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE, (1+generator.nextInt(GameConfig.NB_ROWS-2)) * SPRITE_SIZE);
		while(map[((int)pos.getY()/SPRITE_SIZE)][((int)pos.getX()/SPRITE_SIZE)] != 0){
			pos.setLocation((1+generator.nextInt(GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE, (1+generator.nextInt(GameConfig.NB_ROWS-2)) * SPRITE_SIZE);
		}
		return pos;
	}

	private static GameEntity initMovableState (GameMovable ent, GameMovableDriverDefaultImpl mv_driv, MoveStrategy mv_str){
		mv_driv.setStrategy(mv_str);
		mv_driv.setmoveBlockerChecker(moveBlockerChecker);
		ent.setDriver(mv_driv);
		
		return (GameEntity)ent;
	}

	public static void addRandomHealthPack() {
		Random generator = new Random();
		if(generator.nextInt(HEALTHPACK_DROP_RATE) == 0) {
			Point p = new Point((generator.nextInt(GameConfig.NB_ROWS -2)+1)*GameConfig.NB_ROWS, (generator.nextInt(GameConfig.NB_COLUMNS -2)+1)*GameConfig.NB_COLUMNS);
			universe.addGameEntity(new HealthPack(canvas, p));
		}
	}

	public GameLevelOne(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

	public static void switchInfantryHorseMan(InfantryMan inf){
		
		Horseman new_horseman = new Horseman(inf.getName(), canvas, "images/knight2.png",inf.getTeam());
		new_horseman.setPosition(inf.getPosition());
		MoveStrategyMouse mouseStr = new MoveStrategyMouse(new_horseman);
		canvas.addMouseListener(mouseStr);
		if(inf.getSelected())
			new_horseman.setSelected(true);
		universe.addGameEntity(initMovableState(new_horseman, new GameMovableDriverDefaultImpl(), mouseStr));

		universe.removeGameEntity(inf);

	}
}
