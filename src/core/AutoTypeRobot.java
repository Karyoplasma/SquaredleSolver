package core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class AutoTypeRobot extends Robot {

	public AutoTypeRobot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void pressAndRelease(int keyEvent, int delay) throws InterruptedException {
		super.keyPress(keyEvent);
		Thread.sleep(delay);
		super.keyRelease(keyEvent);
		Thread.sleep(100);
	}
	
	public void enterWord(String word) throws InterruptedException {
		for (int i = 0; i < word.length(); i++) {
			this.pressAndRelease(word.charAt(i) - 32, 10);
		}
		this.pressAndRelease(KeyEvent.VK_ENTER, 10);
		Thread.sleep(200);
	}

}
