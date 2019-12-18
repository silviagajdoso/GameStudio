package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.game.npuzzle.core.Field;
import sk.tsystems.gamestudio.game.npuzzle.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService.CommentService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/puzzle")
public class PuzzleController {
	private Field field;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {
		field = new Field(4, 4);
		return "puzzle";
	}

	@RequestMapping("/comment")
	public String comment(Comment comment) {

		if (mainController.isLogged()) {
			commentService.addComment(
					new Comment(mainController.getLoggedPlayer().getName(), "puzzle", comment.getContent()));
		}
		return "puzzle";
	}

	@RequestMapping("/move")
	public String move(int tile) {
		if (!field.isSolved()) {
			field.move(tile);
			if (field.isSolved() && mainController.isLogged()) {
				Score score = new Score(mainController.getLoggedPlayer().getName(), "puzzle", field.getScore());
				scoreService.addScore(score);
			}
		}
		return "puzzle";
	}

	public String getHtmlField() {
		Formatter f = new Formatter();

		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>");
				Tile tile = field.getTile(row, column);
				if (tile != null) {
					f.format("<a href='/puzzle/move?tile=%d'>", tile.getValue());
					f.format("<img src='/images/puzzle/img%d.jpg'></a>", tile.getValue());
					f.format("</a>");
				}
				f.format("</td>\n");
			}
			f.format("</tr>\n");
		}

		f.format("</table>\n");

		return f.toString();
	}

	public boolean isSolved() {
		return field.isSolved();
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("puzzle");
	}

	public List<Comment> getComment() {
		return commentService.getComment("puzzle");
	}

}
