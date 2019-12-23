package sk.tsystems.gamestudio;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceJDBC;

public class Main {

	public static void main(String[] args) {


		// scoreService.addScore(new Score("fero", "puzzle", 224));
		for (Score score : scoreService.getTopScores("puzzle"))
			System.out.println(score.getUsername() + " " + score.getValue());
	}
	
	
}
