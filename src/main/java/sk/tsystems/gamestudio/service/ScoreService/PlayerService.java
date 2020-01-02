package sk.tsystems.gamestudio.service.ScoreService;

import java.util.List;

import sk.tsystems.gamestudio.entity.Player;

public interface PlayerService {
	void addPlayer(Player player);

	Player getPlayer(String name);

	List<Player> listAllUsers();

}
