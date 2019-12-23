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
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.service.ScoreService.CommentService;
import sk.tsystems.gamestudio.service.ScoreService.RaitingService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessnumberController {
	private int number;
	private String message;
	private long startMillis;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private RaitingService raitingService;

	@Autowired
	private MainController mainController;

	@RequestMapping("/guessgame")
	public String index() {
		message = "";
		number = (int) (Math.random() * 100 + 1);
		return "guessgame";
	}

	@RequestMapping("/guessgame/guess")
	public String guess(String value) {
		try {
			int parseValue = Integer.parseInt(value);
			evaluate(parseValue);
			if (isSolved(message) && mainController.isLogged()) {
				scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "guessgame", getScore()));

			}

		} catch (Exception e) {

		}
		return "guessgame";

	}

	public void evaluate(int value) {
		if (value < number) {
			message = "Ou, that's too small";

		} else if (value > number) {
			message = "Thats too big";

		} else {

			message = "You won";

		}
	}

	public String getMessage() {
		return message;
	}

	@RequestMapping("/guessgame/comment")
	public String comment(Comment comment) {

		if (mainController.isLogged()) {
			commentService.addComment(
					new Comment(mainController.getLoggedPlayer().getName(), "guessgame", comment.getContent()));
		}
		return "guessgame";
	}

	@RequestMapping("/guessgame/raiting")
	public String raiting(String raiting) {
		try {
			System.out.println(
					"----------------------------------------------------------------------------------" + raiting);
			int parseRate = Integer.parseInt(raiting);
			raitingService.setRaiting(new Raiting(mainController.getLoggedPlayer().getName(), "guessgame", parseRate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "guessgame";
	}

	public boolean isSolved(String message) {
		if (message.equals("You won")) {
			return true;
		}

		else {
			return false;
		}
	}

	public int getScore() {

		int seconds = (int) ((System.currentTimeMillis() - startMillis) / 1000);
		int score = seconds / 60;
		return score > 0 ? score : 0;

	}

	public List<Comment> getComment() {
		return commentService.getComment("guessgame");
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("guessgame");
	}
}
