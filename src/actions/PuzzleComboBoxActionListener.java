package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.Puzzle;
import core.SquaredlePuzzleManager;
import gui.SquaredleSolverGUI;

public class PuzzleComboBoxActionListener implements ActionListener {
	
	private SquaredleSolverGUI gui;
	
	public PuzzleComboBoxActionListener(SquaredleSolverGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Puzzle selection = this.gui.getPuzzleSelection();
		SquaredlePuzzleManager manager = new SquaredlePuzzleManager();
		manager.downloadPuzzleConfig();
		manager.readPuzzles();
		if (selection == Puzzle.SQUAREDLE) {
			gui.setBoard(manager.getRegular());
			return;
		}
		if (selection == Puzzle.EXPRESS) {
			gui.setBoard(manager.getExpress());
		}
	}
}
