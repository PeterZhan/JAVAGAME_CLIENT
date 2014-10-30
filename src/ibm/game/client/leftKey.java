package ibm.game.client;

public class leftKey {
	private static boolean pressed = false;

	public static synchronized boolean isPressed() {
		return pressed;
	}

	public static synchronized void setPressed(boolean pressed) {
		leftKey.pressed = pressed;
	}

	
	
	

}
