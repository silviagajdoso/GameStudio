package sk.tsystems.gamestudio.game.minesweeper.core;

import java.util.Random;

public class Field {
	private final int rowCount;

	private final int columnCount;

	private final int mineCount;

	private GameState state = GameState.PLAYING;

	private final Tile[][] tiles;

	private int numberOfOpenedTiles;

	public Field(int rowCount, int columnCount, int mineCount) {
		if (rowCount * columnCount <= mineCount)
			throw new IllegalArgumentException("Too many mines");

		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	private void generate() {
		generateMines();
		fillWithClues();
	}

	private void generateMines() {
		Random random = new Random();
		int mine = 0;
		while (mine < mineCount) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				mine++;
			}
		}
	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(countNeighbourMines(row, column));
				}
			}
		}
	}

	private int countNeighbourMines(int row, int column) {
		// int count = 0;
		//
		// if (row > 0) {
		// if (column > 0 && tiles[row - 1][column - 1] instanceof Mine)
		// count++;
		// if (tiles[row - 1][column] instanceof Mine)
		// count++;
		// if (column < columnCount - 1 && tiles[row - 1][column + 1] instanceof Mine)
		// count++;
		// }
		//
		// if(column > 0 && tiles[row][column - 1] instanceof Mine)
		// count++;
		// if(column < columnCount - 1 && tiles[row][column + 1] instanceof Mine)
		// count++;
		//
		// if(row < rowCount - 1) {
		// if (column > 0 && tiles[row + 1][column - 1] instanceof Mine)
		// count++;
		// if (tiles[row + 1][column] instanceof Mine)
		// count++;
		// if (column < columnCount - 1 && tiles[row + 1][column + 1] instanceof Mine)
		// count++;
		// }
		//
		// return count;

		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						if (tiles[aRow][aColumn] instanceof Mine)
							count++;
					}
				}
			}
		}
		return count;
	}

	public void markTile(int row, int column) {
		Tile tile = getTile(row, column);
		if (tile.getState() == TileState.CLOSED)
			tile.setState(TileState.MARKED);
		else if (tile.getState() == TileState.MARKED)
			tile.setState(TileState.CLOSED);
	}

	public void openTile(int row, int column) {
		Tile tile = getTile(row, column);

		if (tile.getState() == TileState.CLOSED) {
			tile.setState(TileState.OPENED);
			numberOfOpenedTiles++;

			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}

			if (tile instanceof Clue && ((Clue) tile).getValue() == 0) {
				openNeighbourTiles(row, column);
			}

			if (isSolved())
				state = GameState.SOLVED;
		}
	}

	private void openNeighbourTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						openTile(aRow, aColumn);
					}
				}
			}
		}
	}

	private boolean isSolved() {
		return numberOfOpenedTiles == rowCount * columnCount - mineCount;
	}
}
