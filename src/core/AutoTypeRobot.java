package core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

public class AutoTypeRobot extends Robot {
	
	private volatile boolean cancel = false;
	
	public AutoTypeRobot() throws AWTException {
		super();
	}

	private void pressAndRelease(int keyEvent, int delay)  {
		try {
			super.keyPress(keyEvent);
			Thread.sleep(delay);
			super.keyRelease(keyEvent);
			Thread.sleep(100);
		} catch (InterruptedException e) {
			return;
		}
		
	}
	
	private void enterWord(String word) throws InterruptedException {

		for (int i = 0; i < word.length(); i++) {
			if (cancel) {
				return;
			}
			this.pressAndRelease(word.charAt(i) - 32, 10);
		}
		this.pressAndRelease(KeyEvent.VK_ENTER, 10);
		Thread.sleep(200);
	}
	
	public void typeSolutions(List<String> solutions) {
		if (solutions == null || solutions.isEmpty() || cancel) {
			return;
		}
		for (String word : solutions) {
			if (cancel) {
				break;
			}
			try {
				this.enterWord(word);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		cancel = false;
	}
	
	public void cancelAutoType() {
		cancel = true;
	}
}
