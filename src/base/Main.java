package base;

import display.Display;
import javafx.embed.swing.JFXPanel;

public class Main {
	public static Display.GameWindow window;
	public static JFXPanel jfx = new JFXPanel();
	public static void main(String[] args) {
		window = new Display.GameWindow();
		jfx.setEnabled(false);
	}
}
