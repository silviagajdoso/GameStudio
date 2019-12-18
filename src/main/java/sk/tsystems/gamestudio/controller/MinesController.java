package sk.tsystems.gamestudio.controller;

import java.util.Formatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/mines")
public class MinesController {
	private Field field;

	private boolean marking;

	@RequestMapping
	public String index() {
		field = new Field(9, 9, 10);
		return "mines";
	}

	@RequestMapping("/action")
	public String action(int row, int column) {
		if (field.getState() == GameState.PLAYING)
			if (marking)
				field.markTile(row, column);
			else
				field.openTile(row, column);
		return "mines";
	}

	@RequestMapping("/change")
	public String change() {
		marking = !marking;
		return "mines";
	}

	public String getHtmlField() {
		Formatter f = new Formatter();

		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>");
				Tile tile = field.getTile(row, column);
				f.format("<a href='/mines/action?row=%d&column=%d'>", row, column);
				f.format("<img src='/images/mines/%s.png'></a>", getImageName(tile));
				f.format("</a>");
				f.format("</td>\n");
			}
			f.format("</tr>\n");
		}

		f.format("</table>\n");

		return f.toString();
	}

	private String getImageName(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case MARKED:
			return "marked";
		case OPENED:
			if (tile instanceof Clue)
				return "open" + ((Clue) tile).getValue();
			else
				return "mine";
		default:
			throw new IllegalArgumentException();
		}
	}

	public boolean isMarking() {
		return marking;
	}
}
