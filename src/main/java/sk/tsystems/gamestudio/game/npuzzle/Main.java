package sk.tsystems.gamestudio.game.npuzzle;

import sk.tsystems.gamestudio.game.npuzzle.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.npuzzle.core.Field;

public class Main {

	public static void main(String[] args) {
		ConsoleUI ui = new ConsoleUI();
		Field field = new Field(10, 10);
		ui.play(field);
	}
}
