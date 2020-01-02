
package sk.tsystems.gamestudio.service.ScoreService;

import sk.tsystems.gamestudio.entity.Raiting;

public interface RaitingService {

	void setRaiting(Raiting raiting);

	double getAverageRaiting(String game);

}
