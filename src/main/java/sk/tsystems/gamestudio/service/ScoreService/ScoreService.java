package sk.tsystems.gamestudio.service.ScoreService;

import java.util.List;

import sk.tsystems.gamestudio.entity.Score;

public interface ScoreService {
	void addScore(Score score);
;

	List<Score> getTopScores(String game);

;
}


