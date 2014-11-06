package ibm.game.client;

public class rightKey {
	private static boolean pressed = false;

	public static synchronized boolean isPressed() {
		return pressed;
	}

	public static synchronized void setPressed(boolean pressed) {
		rightKey.pressed = pressed;
	}

}
