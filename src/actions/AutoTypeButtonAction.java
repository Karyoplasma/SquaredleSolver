package actions;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import core.AutoTypeRobot;
import gui.SquaredleSolverGUI;

public class AutoTypeButtonAction extends AbstractAction {

	private static final long serialVersionUID = -400474646556974660L;
	private SquaredleSolverGUI gui;

	public AutoTypeButtonAction(SquaredleSolverGUI gui) {
		putValue(Action.NAME, "Auto-type");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (!(gui.solutions == null) || !gui.solutions.isEmpty()) {
			try {
				AutoTypeRobot robot = new AutoTypeRobot();
				Thread.sleep(2000);
				for (String word : gui.solutions) {
					robot.enterWord(word);
				}
			} catch (AWTException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}