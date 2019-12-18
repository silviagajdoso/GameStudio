package sk.tsystems.gamestudio.game.minesweeper.consoleui;

import java.io.IOException;
import java.util.Scanner;

import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;

public class ConsoleUI {
	private Field field;

	public ConsoleUI(Field field) {
		this.field = field;
	}

	public void play() {
		do {
			printField();
			processInput();
		} while (field.getState() == GameState.PLAYING);

		printField();

		if (field.getState() == GameState.SOLVED)
			System.out.println("You won!");
		else if (field.getState() == GameState.FAILED)
			System.out.println("You failed!");
	}

	private void processInput() {
		System.out.print("Enter command (e.g. OA1, MB3, X): ");
		String line = new Scanner(System.in).nextLine().trim().toUpperCase();
		if ("X".equals(line))
			System.exit(0);
		if (line.length() >= 3) {
			try {
				int row = line.charAt(1) - 'A';
				int column = Integer.parseInt(line.substring(2)) - 1;
				
				if (row >= 0 && row < field.getRowCount() && column >= 0 
						&& column < field.getColumnCount()) {
					if (line.charAt(0) == 'O')
						field.openTile(row, column);
					else if (line.charAt(0) == 'M')
						field.markTile(row, column);
				}
			} catch (NumberFormatException e) {
				System.err.println("Bad input " + line);
			}
		}
	}

	private void printField() {
		System.out.print(" ");
		for (int column = 1; column <= field.getColumnCount(); column++) {
			System.out.print(" ");
			System.out.print(column);
		}
		System.out.println();
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print((char) ('A' + row));
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				System.out.print(" ");
				switch (tile.getState()) {
				case CLOSED:
					System.out.print("-");
					break;
				case MARKED:
					System.out.print("M");
					break;
				case OPENED:
					if (tile instanceof Clue)
						System.out.print(((Clue) tile).getValue());
					else
						System.out.print("X");
					break;
				}
			}
			System.out.println();
		}
	}
}
