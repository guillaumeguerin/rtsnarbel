package gameframework.game;

import game.GameLevelOne;
import gameframework.base.ObservableValue;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * Create a basic game application with menus and displays of lives and score
 */
public class GameDefaultImpl implements Game, Observer {
	protected static final int NB_ROWS = GameConfig.NB_ROWS;
	protected static final int NB_COLUMNS = GameConfig.NB_COLUMNS;
	protected static final int SPRITE_SIZE = GameConfig.SPRITE_SIZE;
	public static final int NUMBER_OF_ENEMIES = GameLevelOne.NUMBER_OF_ENEMIES;
	public static final int NUMBER_OF_ALLIES = GameLevelOne.NUMBER_OF_ALLIES;
	public static final int NUMBER_OF_LIVES = GameConfig.NUMBER_OF_LIVES;

	protected CanvasDefaultImpl defaultCanvas = null;
	protected ObservableValue<Integer> score[] = new ObservableValue[NUMBER_OF_ENEMIES];
	protected ObservableValue<Integer> enemy[] = new ObservableValue[NUMBER_OF_ENEMIES];
	protected ObservableValue<Integer> ally[] = new ObservableValue[NUMBER_OF_ALLIES];

	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;

	private Frame f;
	private GameLevelDefaultImpl currentPlayedLevel = null;

	protected int levelNumber;
	protected ArrayList<GameLevel> gameLevels;

	protected Label enemyText, allyText, scoreText;
	protected Label information;
	protected Label informationValue;
	protected Label enemyValue, allyValue, scoreValue;
	protected Label currentLevel;
	protected Label currentLevelValue;

	public GameDefaultImpl() {
		for (int i = 0; i < NUMBER_OF_ENEMIES; ++i) {
			score[i] = new ObservableValue<Integer>(0);
			enemy[i] = new ObservableValue<Integer>(0);
		}
		for (int i = 0; i < NUMBER_OF_ALLIES; ++i) {
			score[i] = new ObservableValue<Integer>(0);
			ally[i] = new ObservableValue<Integer>(0);
		}
		enemyText = new Label("Enemy soldiers remaining :");
		allyText = new Label("Soldiers in my team :");
		scoreText = new Label("Score :");
		information = new Label("State:");
		informationValue = new Label("Playing");
		currentLevel = new Label("Level:");
		createGUI();
	}

	public void createGUI() {
		f = new Frame("Default Game");
		f.dispose();

		createMenuBar();
		Container c = createStatusBar();

		defaultCanvas = new CanvasDefaultImpl();
		defaultCanvas.setSize(SPRITE_SIZE * NB_COLUMNS, SPRITE_SIZE * NB_ROWS);
		f.add(defaultCanvas);
		f.add(c, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void createMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		MenuItem start = new MenuItem("New game");
		MenuItem save = new MenuItem("Save");
		MenuItem restore = new MenuItem("Load");
		MenuItem quit = new MenuItem("Quit");
		Menu game = new Menu("Game");
		MenuItem pause = new MenuItem("Pause");
		MenuItem resume = new MenuItem("Resume");
		menuBar.add(file);
		menuBar.add(game);
		f.setMenuBar(menuBar);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restore();
			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		resume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resume();
			}
		});

		file.add(start);
		file.add(save);
		file.add(restore);
		file.add(quit);
		game.add(pause);
		game.add(resume);
	}

	private Container createStatusBar() {
		JPanel c = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		c.setLayout(layout);
		enemyValue = new Label(Integer.toString(enemy[0].getValue()));
		allyValue = new Label(Integer.toString(ally[0].getValue()));
		scoreValue = new Label(Integer.toString(score[0].getValue()));
		currentLevelValue = new Label(Integer.toString(levelNumber));
		c.add(enemyText);
		c.add(enemyValue);
		c.add(allyText);
		c.add(allyValue);
		c.add(scoreText);
		c.add(scoreValue);
		c.add(currentLevel);
		c.add(currentLevelValue);
		c.add(information);
		c.add(informationValue);
		return c;
	}

	public Canvas getCanvas() {
		return defaultCanvas;
	}

	public void start() {
		for (int i = 0; i < NUMBER_OF_ENEMIES; ++i) {
			score[i].addObserver(this);
			enemy[i].addObserver(this);
			enemy[i].setValue(NUMBER_OF_ENEMIES);
			score[i].setValue(0);
		}
		for (int i = 0; i < NUMBER_OF_ALLIES; ++i) {
			score[i].addObserver(this);
			ally[i].addObserver(this);
			ally[i].setValue(NUMBER_OF_ALLIES);
			score[i].setValue(0);
		}
		levelNumber = 0;
		for (GameLevel level : gameLevels) {
			endOfGame = new ObservableValue<Boolean>(false);
			endOfGame.addObserver(this);
			try {
				if (currentPlayedLevel != null && currentPlayedLevel.isAlive()) {
					currentPlayedLevel.interrupt();
					currentPlayedLevel = null;
				}
				currentPlayedLevel = (GameLevelDefaultImpl) level;
				levelNumber++;
				currentLevelValue.setText(Integer.toString(levelNumber));
				currentPlayedLevel.start();
				currentPlayedLevel.join();
			} catch (Exception e) {
			}
		}

	}

	public void restore() {
		System.out.println("restore(): Unimplemented operation");
	}

	public void save() {
		System.out.println("save(): Unimplemented operation");
	}

	public void pause() {
		System.out.println("pause(): Unimplemented operation");
		// currentPlayedLevel.suspend();
	}

	public void resume() {
		System.out.println("resume(): Unimplemented operation");
		// currentPlayedLevel.resume();
	}

	public ObservableValue<Integer>[] score() {
		return score;
	}

	public ObservableValue<Integer>[] enemy() {
		return enemy;
	}

	public ObservableValue<Integer>[] ally() {
		return ally;
	}
	
	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public void setLevels(ArrayList<GameLevel> levels) {
		gameLevels = levels;
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				informationValue.setText("Defeat");
				currentPlayedLevel.interrupt();
				currentPlayedLevel.end();
			}
		} else {
			for (ObservableValue<Integer> enemyObservable : enemy) {
				if (o == enemyObservable) {
					int enemies = ((ObservableValue<Integer>) o).getValue();
					enemyValue.setText(Integer.toString(enemies));
					if (enemies == 0) {
						informationValue.setText("You win");
						//currentPlayedLevel.interrupt();
						//currentPlayedLevel.end();
					}
				}
			}
			for (ObservableValue<Integer> allyObservable : ally) {
				if (o == allyObservable) {
					int allies = ((ObservableValue<Integer>) o).getValue();
					allyValue.setText(Integer.toString(allies));
					if (allies == 0) {
						informationValue.setText("Defeat");
						currentPlayedLevel.interrupt();
						currentPlayedLevel.end();
					}
				}
			}
			for (ObservableValue<Integer> scoreObservable : score) {
				if (o == scoreObservable) {
					scoreValue
							.setText(Integer
									.toString(((ObservableValue<Integer>) o)
											.getValue()));
				}
			}
		}
	}

}
