package gameframework.game;

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

import pacman.GameLevelOne;

/**
 * Create a basic game application with menus and displays of lives and score
 */
public class GameDefaultImpl implements Game, Observer {
	protected static final int NB_ROWS = GameConfig.NB_ROWS;
	protected static final int NB_COLUMNS = GameConfig.NB_COLUMNS;
	protected static final int SPRITE_SIZE = GameConfig.SPRITE_SIZE;
	public static final int NUMBER_OF_ENEMIES = GameLevelOne.NUMBER_OF_ENEMIES;
	public static final int NUMBER_OF_LIVES = GameConfig.NUMBER_OF_LIVES;

	protected CanvasDefaultImpl defaultCanvas = null;
	protected ObservableValue<Integer> score[] = new ObservableValue[NUMBER_OF_ENEMIES];
	protected ObservableValue<Integer> enemy[] = new ObservableValue[NUMBER_OF_ENEMIES];

	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;

	private Frame f;
	private GameLevelDefaultImpl currentPlayedLevel = null;

	protected int levelNumber;
	protected ArrayList<GameLevel> gameLevels;

	protected Label enemyText, scoreText;
	protected Label information;
	protected Label informationValue;
	protected Label enemyValue, scoreValue;
	protected Label currentLevel;
	protected Label currentLevelValue;

	public GameDefaultImpl() {
		for (int i = 0; i < NUMBER_OF_ENEMIES; ++i) {
			score[i] = new ObservableValue<Integer>(0);
			enemy[i] = new ObservableValue<Integer>(0);
		}
		enemyText = new Label("Enemy soldiers remaining :");
		scoreText = new Label("Soldiers in my Team :");
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
		Menu file = new Menu("file");
		MenuItem start = new MenuItem("new game");
		MenuItem save = new MenuItem("save");
		MenuItem restore = new MenuItem("load");
		MenuItem quit = new MenuItem("quit");
		Menu game = new Menu("game");
		MenuItem pause = new MenuItem("pause");
		MenuItem resume = new MenuItem("resume");
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
		scoreValue = new Label(Integer.toString(score[0].getValue()));
		currentLevelValue = new Label(Integer.toString(levelNumber));
		c.add(enemyText);
		c.add(enemyValue);
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

	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public void setLevels(ArrayList<GameLevel> levels) {
		gameLevels = levels;
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				informationValue.setText("You win");
				currentPlayedLevel.interrupt();
				currentPlayedLevel.end();
			}
		} else {
			for (ObservableValue<Integer> enemyObservable : enemy) {
				if (o == enemyObservable) {
					int enemies = ((ObservableValue<Integer>) o).getValue();
					enemyValue.setText(Integer.toString(enemies));
					if (enemies == 0) {
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
