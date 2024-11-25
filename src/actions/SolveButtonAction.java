package actions;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import core.SquaredleBoard;
import core.SquaredleSolver;
import core.WordListComparator;
import gui.SquaredleSolverGUI;

public class SolveButtonAction extends AbstractAction {

	private static final long serialVersionUID = -6930914011077533298L;
	private SquaredleSolverGUI gui;

	public SolveButtonAction(SquaredleSolverGUI squaredleSolverGUI) {
		putValue(Action.NAME, "Solve");
		this.gui = squaredleSolverGUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String boardText = this.gui.getBoard();
		if (boardText.equals("")) {
			return;
		}
		try {
			SquaredleBoard board = new SquaredleBoard(boardText);
			SquaredleSolver solver = new SquaredleSolver(board);

			this.gui.setSolutions(solver.solveSquaredle());
			this.arrangeResults(this.gui.getSolutions());
			this.gui.unlockAutoType();
		} catch (IllegalArgumentException boardException) {
			this.gui.setResultText(boardException.getMessage());
		}

	}

	private void arrangeResults(List<String> solutions) {
		String newLine = System.getProperty("line.separator");
		StringBuffer buffer = new StringBuffer();
		Collections.sort(solutions, new WordListComparator());
		int currentSize = 3;
		boolean start = true;
		char currentLetter = '.';

		buffer.append("Found ").append(solutions.size()).append(" candidates:");
		buffer.append(newLine).append(newLine);
		for (String word : solutions) {
			if (word.length() > currentSize) {
				currentSize = word.length();
				currentLetter = '.';
				if (!start) {
					buffer.append(newLine);
				}
				buffer.append(currentSize + "-letter words:").append(newLine);
				start = false;
			}
			if (currentLetter == '.') {
				currentLetter = word.charAt(0);
				buffer.append(word);
				continue;
			}
			if (currentLetter == word.charAt(0)) {
				buffer.append("\t").append(word);
			} else {
				currentLetter = word.charAt(0);
				buffer.append(newLine).append(word);
			}

		}
		this.gui.setResultText(buffer.toString());

	}

}
