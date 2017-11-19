package com.ncatz.yeray697.managepc.control;

public class ParseClientResponse {

	public static final int ORDER_KEEP_ON = 0;
	public static final int ORDER_CLOSE = 1;
    public static final int ORDER_LEFT_CLICK_DOWN = 2;
    public static final int ORDER_LEFT_CLICK_UP = 3;
    public static final int ORDER_RIGHT_CLICK_DOWN = 4;
    public static final int ORDER_RIGHT_CLICK_UP = 5;
    public static final int ORDER_WHEEL_CLICK_DOWN = 6;
    public static final int ORDER_WHEEL_CLICK_UP = 7;
    public static final int ORDER_MOVE_WHEEL = 8;
    public static final int ORDER_MOUSE_MOVE = 9;
    public static final int ORDER_KEY_PRESSED = 10;
    public static final int ORDER_VOLUME_UP = 11;
    public static final int ORDER_VOLUME_DOWN = 12;

    public static final String KEYCODE_DEL = "-1";
    public static final String KEYCODE_SPACE = "-2";
    public static final String KEYCODE_ENTER = "-3";
    
	public static int parseClientResponse(char[] line) {
		String message = new String(line).trim();
		String[] splitedMessage = message.split(";");
		int order = ORDER_KEEP_ON;
		try {
			int message1 = Integer.parseInt(splitedMessage[0]);
			order = message1;
			String[] splitted2;
			switch (message1) {
			case ORDER_LEFT_CLICK_DOWN:
				ManagePCHelper.leftClick(true);
				break;
			case ORDER_LEFT_CLICK_UP:
				ManagePCHelper.leftClick(false);
				break;
			case ORDER_RIGHT_CLICK_DOWN:
				ManagePCHelper.rightClick(true);
				break;
			case ORDER_RIGHT_CLICK_UP:
				ManagePCHelper.rightClick(false);
				break;
			case ORDER_WHEEL_CLICK_UP:
				ManagePCHelper.wheelClick(true);
				break;
			case ORDER_WHEEL_CLICK_DOWN:
				ManagePCHelper.wheelClick(false);
				break;
			case ORDER_MOVE_WHEEL:
				ManagePCHelper.scroll(Integer.parseInt(splitedMessage[1]));
				break;
			case ORDER_MOUSE_MOVE:
				splitted2 = splitedMessage[1].split(",");
				ManagePCHelper.move(Integer.parseInt(splitted2[0]),Integer.parseInt(splitted2[1]));
				break;
			case ORDER_KEY_PRESSED:
				String key = splitedMessage[1];
				switch (key) {
					case KEYCODE_DEL:
						ManagePCHelper.pressDeleteKey();
						break;
					case KEYCODE_SPACE:
						ManagePCHelper.pressSpaceKey();
						break;
					case KEYCODE_ENTER:
						ManagePCHelper.pressEnterKey();
						break;
					default:
						ManagePCHelper.writeChar(key);
						break;
				}
				break;
			case ORDER_VOLUME_UP:
				ManagePCHelper.volumeUp();
				break;
			case ORDER_VOLUME_DOWN:
				ManagePCHelper.volumeDown();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return order;
	}
}
