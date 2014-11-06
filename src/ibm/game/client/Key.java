package ibm.game.client;

public class Key {

	private boolean[] keys = new boolean[256];

	public Key() {
		for (int i = 0; i < 256; i++) {
			keys[i] = false;

		}

	}

	public void setKeypressed(int code) {
		if (code < 0 || code > 255)
			return;

		keys[code] = true;

	}

	public void setKeyreleased(int code) {
		if (code < 0 || code > 255)
			return;

		keys[code] = false;

	}

	public boolean getKeyPressed(int code) {

		if (code < 0 || code > 255)
			return false;

		return keys[code];

	}

}
