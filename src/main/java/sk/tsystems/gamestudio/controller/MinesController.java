package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Raiting;
import sk.tsystems.gamestudio.entity.Score;

import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService.CommentService;
import sk.tsystems.gamestudio.service.ScoreService.RaitingService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/mines")
public class MinesController {
	private Field field;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private RaitingService raitingService;

	@Autowired
	private MainController mainController;

	private boolean marking;

	@RequestMapping
	public String index() {
		field = new Field(10, 10, 20);
		return "mines";
	}

	@RequestMapping("/comment")
	public String comment(Comment comment) {

		if (mainController.isLogged()) {
			commentService
					.addComment(new Comment(mainController.getLoggedPlayer().getName(), " mines", comment.getContent()));
		}
		return "mines";
	}

	@RequestMapping("/raiting")
	public String raiting(String raiting) {
		try {
			System.out.println(
					"----------------------------------------------------------------------------------" + raiting);
			int parseRate = Integer.parseInt(raiting);
			raitingService.setRaiting(new Raiting(mainController.getLoggedPlayer().getName(), " mines", parseRate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "mines";
	}

	@RequestMapping("/action")
	public String action(int row, int column) {
		if (field.getState() == GameState.PLAYING)
			if (marking)
				field.markTile(row, column);
			else
				field.openTile(row, column);
		if (field.getState() == GameState.SOLVED && mainController.isLogged()) {
			Score score = new Score(mainController.getLoggedPlayer().getName(), " mines", field.getScore());
			scoreService.addScore(score);
		}

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

	public boolean isSolved() {
		return field.isSolved();
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("mines");
	}

	public List<Comment> getComment() {
		return commentService.getComment("mines");
	}
	public double getAverageRaiting() {
		return raitingService.getAverageRaiting("mines");
	}
}
