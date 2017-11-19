package com.ncatz.yeray697.managepc.control;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.ncatz.yeray697.managepc.utils.UtilsSystem;

public class ManagePCHelper {

	private final static int MAX_VOLUME_PERCENTAGE = 100;

	public static void leftClick(boolean down) throws AWTException {
		Robot bot = new Robot();
		if (down) {
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		} else {
			bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
	}
	
	public static void rightClick(boolean down) throws AWTException {
		Robot bot = new Robot();
		if (down) {
			bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		} else {
			bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		}
	}
	
	public static void wheelClick(boolean down) throws AWTException {
		Robot bot = new Robot();
		if (down) {
			bot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
		} else {
			bot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
		}
	}

	public static void move(int x, int y) throws AWTException {
		Point p = MouseInfo.getPointerInfo().getLocation();
		int speed = 20;
		movingMouse = false;
		moveMouse(p.x, p.y, p.x + x, p.y + y, speed);
	}
	
	private static boolean movingMouse;
	
	public static void moveMouse(int sx, int sy, int ex, int ey, int speed) throws AWTException {
        Robot robot = new Robot();
        int mov_x, mov_y;
        int i = 0;
        movingMouse = true;
        int n = 20;
        int MAX_SPEED = 100;
        int realSpeed = MAX_SPEED - speed * 5;
        if (realSpeed > MAX_SPEED)
        	realSpeed = MAX_SPEED;
        else if (realSpeed < 0)
        	realSpeed = 0;
        do {
        	mov_x = ((ex * i) / n) + (sx * (n - i) / n);
            mov_y = ((ey * i) / n) + (sy * (n - i) / n);
            robot.mouseMove(mov_x,mov_y);
            robot.delay(realSpeed);
            i ++;
        } while ((mov_x != ex && mov_y != ey) && movingMouse);
        System.out.println("i:"+ i + ", movingMouse:"+movingMouse);
        movingMouse = false;
    }
	
	public static void scroll(int scroll) throws AWTException {
		Robot bot = new Robot();
		bot.mouseWheel(scroll < 0 ? -1 : 1);
	}

	public static void writeChar(String key) throws AWTException {
		StringSelection stringSelection = new StringSelection(key);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}


	public static void pressDeleteKey() throws AWTException {
		pressReleaseKey(KeyEvent.VK_BACK_SPACE);
	}
	
	public static void pressSpaceKey() throws AWTException {
		pressReleaseKey(KeyEvent.VK_SPACE);
	}

	public static void pressEnterKey() throws AWTException {
		pressReleaseKey(KeyEvent.VK_ENTER);
	}
	
	private static void pressReleaseKey(int key) throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(key);
		robot.keyRelease(key);
	}

	public static void volumeUp() {
		try {
			modifyVolume(5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void volumeDown() {
		try {
			modifyVolume(-5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void modifyVolume(int volume) throws IOException {
		String urlScript = UtilsSystem.getLibFolder() + "ModifyVolume.sh";
		new ProcessBuilder(urlScript,
				String.valueOf(volume),
				String.valueOf(MAX_VOLUME_PERCENTAGE))
				.start();

	}
}
