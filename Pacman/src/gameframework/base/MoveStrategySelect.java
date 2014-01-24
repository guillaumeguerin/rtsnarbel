package gameframework.base;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface MoveStrategySelect extends MoveStrategy {
	boolean isSelected();
	void setSelected(boolean a);
	//void keyPressed(KeyEvent event, MouseEvent mouse);
}
