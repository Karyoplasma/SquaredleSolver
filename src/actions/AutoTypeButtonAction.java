package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingWorker;
import core.AutoTypeRobot;
import gui.SquaredleSolverGUI;

public class AutoTypeButtonAction extends AbstractAction {

	private static final long serialVersionUID = -400474646556974660L;
	private SquaredleSolverGUI gui;
	private SwingWorker<Void, Void> worker;

	public AutoTypeButtonAction(SquaredleSolverGUI gui) {
		putValue(Action.NAME, "Auto-type");
		this.gui = gui;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (worker != null && !worker.isDone()) {
			if (!worker.isCancelled()) {
				gui.getRobot().cancelAutoType();
				worker.cancel(true);
			}
			return;
		}
		if (!(this.gui.getSolutions() == null) || !this.gui.getSolutions().isEmpty()) {
			worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					try {
						AutoTypeRobot robot = gui.getRobot();
						Thread.sleep(2000);
						robot.typeSolutions(gui.getSolutions());
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					return null;
				}

				@Override
				protected void done() {
					gui.toggleButtonText();
				}
			};

			gui.toggleButtonText();
			worker.execute();
		}
	}
}