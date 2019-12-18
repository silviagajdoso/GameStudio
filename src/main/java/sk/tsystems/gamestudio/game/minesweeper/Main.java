package sk.tsystems.gamestudio.game.minesweeper;

import sk.tsystems.gamestudio.game.minesweeper.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;

public class Main {
	public static void main(String[] args) {
		Field field = new Field(9, 9, 10);
		ConsoleUI ui = new ConsoleUI(field);
		ui.play();
	}
}
