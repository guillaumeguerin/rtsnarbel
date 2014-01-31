package game;

import game.entity.Bunny;
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
import gameframework.base.MoveStrategyRandomLazy;
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
import java.util.List;
import java.util.Random;

import soldiers.soldier.ArmedUnitSoldier;
import soldiers.utils.AgeFactory;
import soldiers.utils.MiddleAgeFactory;

public class GameLevelOne extends GameLevelDefaultImpl {
	static Canvas canvas;

	public static final int SPRITE_SIZE = GameConfig.SPRITE_SIZE;
	public static final int NUMBER_OF_HORSES = 2;
	public static final int NUMBER_OF_BUNNIES = 3;
	public static int NUMBER_OF_ENEMIES = 7;
	public static int NUMBER_OF_ALLIES = 4;
	public static int NUMBER_OF_PRINCESS = 0; //team 3 test
	public static int HEALTHPACK_DROP_RATE = 5;
	public static int SWORD_DROP_RATE = 5;
	public static int SHIELD_DROP_RATE = 4;
	public static int NB_PLAYER = 1;
	private static int TOTAL_NB_HEALTH_PACK = 5;

	static MoveBlockerChecker moveBlockerChecker;

	@Override
	protected void init() {

		// Check if the number of object is less than the available space
		if (NUMBER_OF_HORSES + NUMBER_OF_BUNNIES + NUMBER_OF_ENEMIES + NUMBER_OF_ALLIES + TOTAL_NB_HEALTH_PACK + GameMap.NB_CASTLES + GameMap.NB_HOUSES + GameMap.NB_TREES + NB_PLAYER <= (GameConfig.NB_COLUMNS-2)*(GameConfig.NB_ROWS-2)){

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

			// Invisible character who is used to select unities, definition and inclusion in the universe.
			MouseCursor myMouse = new MouseCursor(canvas);
			MoveStrategyMouseSelect mouse = new MoveStrategyMouseSelect();
			GameMovableMouseDriverDefaultImpl mouseDriver = new GameMovableMouseDriverDefaultImpl();
			mouseDriver.setStrategy(mouse);
			canvas.addMouseListener(mouse);
			myMouse.setDriver(mouseDriver);
			universe.addGameEntity(myMouse);

			// Allies definition and inclusion in the universe.
			AgeFactory af = new MiddleAgeFactory();

			ArmedUnitSoldier ally;
			for(int i=0; i< NUMBER_OF_ALLIES; ++i) {
				ally = new ArmedUnitSoldier(af, "Simple", "Ally " + (i+1), canvas, "images/knight2.png", 0);
				Point pos = new Point((generator.nextInt(GameConfig.NB_COLUMNS -2)+1) * SPRITE_SIZE, ((((generator.nextInt(GameConfig.NB_ROWS)-2)+1)/3)+2*GameConfig.NB_ROWS/3) * SPRITE_SIZE);
				ally.setPosition(pos);

				while( map [ ((int)ally.getPosition().getY()/SPRITE_SIZE) ][ ((int)ally.getPosition().getX()/SPRITE_SIZE) ] != 0 ){
					pos.setLocation( (1+generator.nextInt(GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE,( ( ( (1+generator.nextInt(GameConfig.NB_ROWS)-2) )/3 )+2*GameConfig.NB_ROWS/3) * SPRITE_SIZE);
					ally.setPosition(pos);
				}	

				MoveStrategyMouse mouseStr = new MoveStrategyMouse(ally);			
				canvas.addMouseListener(mouseStr);

				universe.addGameEntity(initMovableState(ally, new GameMovableDriverDefaultImpl(), mouseStr));
			}

			// Enemies definition and inclusion in the universe.
			ArmedUnitSoldier enemy;
			for(int i=0; i< NUMBER_OF_ENEMIES; ++i) {

				// Define a random chance to be a class or other
				switch(classValue()){
				case SIMPLE:
					enemy = new ArmedUnitSoldier(af, "Simple", "Enemy " + (i+1), canvas, "images/knight1.png", 1);
					break;
				case COMPLEX:
					enemy = new ArmedUnitSoldier(af, "Complex", "Enemy " + (i+1), canvas, "images/knight1.png", 1);
					break;
				default:
					System.err.println("Error : Random soldier class.");
					enemy = new ArmedUnitSoldier(af, "Simple", "ERROR" + (i+1), canvas, "images/knight1.png", 1);
					break;
				}

				// Define a random chance to get stuff at start of game
				switch(stuffValue()){
				case NOTHING:
					break;
				case SWORD:
					enemy.addEquipment("Offensive");
					break;
				case SHIELD:
					enemy.addEquipment("Defensive");
					break;
				case DOUBLE:
					enemy.addEquipment("Offensive");
					enemy.addEquipment("Defensive");
					break;
				default:
					System.err.println("Error : Random stuff.");
					break;
				}

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

				universe.addGameEntity(initMovableState(enemy, new EnemyMovableDriver(), new MoveStrategyRandomLazy()));
			}
			
			// Test: PrincessTeam definition and inclusion in the universe.
			ArmedUnitSoldier princess;
			for(int i=0; i< NUMBER_OF_PRINCESS; ++i) {
				princess = new ArmedUnitSoldier(af, "Simple", "Princess"+(i+1), canvas, "images/princess1.png", 2);
				Point pos = new Point((generator.nextInt(GameConfig.NB_COLUMNS -2)+1) * SPRITE_SIZE, (((generator.nextInt(GameConfig.NB_ROWS)-2)+1)/2) * SPRITE_SIZE);
				if(pos.getY()/SPRITE_SIZE < 1)
					pos.setLocation(pos.getX(), 1*SPRITE_SIZE);
				princess.setPosition(pos);

				while( map [ ((int)princess.getPosition().getY()/SPRITE_SIZE) ][ ((int)princess.getPosition().getX()/SPRITE_SIZE) ] != 0 ){
					pos.setLocation( (1+generator.nextInt(GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE, ( ( (1+generator.nextInt(GameConfig.NB_ROWS)-2) )/2 ) * SPRITE_SIZE);
					if(pos.getY()/SPRITE_SIZE < 1)
						pos.setLocation(pos.getX(), 1*SPRITE_SIZE);
					princess.setPosition(pos);
				}		

				universe.addGameEntity(initMovableState(princess, new EnemyMovableDriver(), new MoveStrategyRandomLazy()));
			}
			// End of princess test
			
			// HealthPacks definition and inclusion in the universe.
			for(int i=0; i<TOTAL_NB_HEALTH_PACK; ++i) {
				universe.addGameEntity(new HealthPack(canvas, defineInitLocation(map, generator)));
			}

			// Horses definition and inclusion in the universe
			Horse myHorse;
			for (int t = 0; t < NUMBER_OF_HORSES; ++t) {
				myHorse = new Horse(canvas);
				myHorse.setPosition(defineInitLocation(map, generator));

				universe.addGameEntity(initMovableState(myHorse, new EnemyMovableDriver(), new MoveStrategyRandomLazy()));
			}
			
			// Neutral bunnies definition and inclusion in the universe
			Bunny myBunny;
			String image = "";
			for (int t = 0; t < NUMBER_OF_BUNNIES; ++t) {
				image = "images/bunny" + generator.nextInt(3) + ".png";
				myBunny = new Bunny(canvas, image);
				Point pos = new Point((generator.nextInt(GameConfig.NB_COLUMNS -2)+1) * SPRITE_SIZE, ((((generator.nextInt(GameConfig.NB_ROWS)-2)+1)/3)+2*GameConfig.NB_ROWS/3) * SPRITE_SIZE);
				myBunny.setPosition(pos);

				while( map [ ((int)myBunny.getPosition().getY()/SPRITE_SIZE) ][ ((int)myBunny.getPosition().getX()/SPRITE_SIZE) ] != 0 ){
					pos.setLocation( (1+generator.nextInt(GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE,( ( ( (1+generator.nextInt(GameConfig.NB_ROWS)-2) )/3 )+2*GameConfig.NB_ROWS/3) * SPRITE_SIZE);
					myBunny.setPosition(pos);
				}	

				universe.addGameEntity(initMovableState(myBunny, new EnemyMovableDriver(), new MoveStrategyRandomLazy()));
			}
		}
	}

	// Define coordinates of the location of a unit
	private Point defineInitLocation(int[][]map, Random generator){
		Point pos = new Point((generator.nextInt(1+GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE, (1+generator.nextInt(GameConfig.NB_ROWS-2)) * SPRITE_SIZE);
		while(map[((int)pos.getY()/SPRITE_SIZE)][((int)pos.getX()/SPRITE_SIZE)] != 0){
			pos.setLocation((1+generator.nextInt(GameConfig.NB_COLUMNS -2)) * SPRITE_SIZE, (1+generator.nextInt(GameConfig.NB_ROWS-2)) * SPRITE_SIZE);
		}
		return pos;
	}

	// Initialize the driver and the strategy of moving entity
	private static GameEntity initMovableState (GameMovable ent, GameMovableDriverDefaultImpl mv_driv, MoveStrategy mv_str){
		mv_driv.setStrategy(mv_str);
		mv_driv.setmoveBlockerChecker(moveBlockerChecker);
		ent.setDriver(mv_driv);

		return (GameEntity)ent;
	}

	// Definition and inclusion in the universe healpacks which droped from fight
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

	// Allies can loot some stuff from fight
	public static void loot(ArmedUnitSoldier s){
		Random generator = new Random();
		if (generator.nextInt(SWORD_DROP_RATE) == 0){
			s.addEquipment("Offensive");
			System.out.println(s.getName() + " loot sword");
		}
		if (generator.nextInt(SHIELD_DROP_RATE) == 0){
			s.addEquipment("Defensive");
			System.out.println(s.getName() + " loot shield");
		}
	}

	// Allies infantrymen can become horsemen if they cross the road of an horse
	public static void switchInfantryHorseMan(ArmedUnitSoldier inf){
		List<String> equipments = inf.getEquipments();
		ArmedUnitSoldier new_horseman = new ArmedUnitSoldier(inf.getAge(), "Complex", inf.getName(), canvas, "images/knight2.png",inf.getTeam());

		for(String equipment : equipments){
			new_horseman.addEquipment(equipment);
		}

		new_horseman.setPosition(inf.getPosition());
		MoveStrategyMouse mouseStr = new MoveStrategyMouse(new_horseman);
		canvas.addMouseListener(mouseStr);
		if(inf.getSelected())
			new_horseman.setSelected(true);
		universe.removeGameEntity(inf);
		universe.addGameEntity(initMovableState(new_horseman, new GameMovableDriverDefaultImpl(), mouseStr));
	}
	
	// Methods and Enums used to train and stuff enemies' soldier
	private SoldierClass classValue(){
		Random generator = new Random();
		int soldierType = generator.nextInt(2);
		if(soldierType < 50)
			return SoldierClass.SIMPLE;
		else
			return SoldierClass.COMPLEX;
	}
	
	private Equipments stuffValue(){
		Random generator = new Random();
		int stuff = generator.nextInt(100);
		if(stuff < 50)
			return Equipments.NOTHING;
		else if (stuff < 73)
			return Equipments.SHIELD;
		else if (stuff < 90)
			return Equipments.SWORD;
		else return Equipments.DOUBLE;
	}

	private enum SoldierClass{
		SIMPLE,
		COMPLEX;
	}
	
	private enum Equipments{
		NOTHING,
		SWORD,
		SHIELD,
		DOUBLE;
	}
	
	
}
