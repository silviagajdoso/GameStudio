package sk.tsystems.gamestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Player;

import sk.tsystems.gamestudio.service.ScoreService.PlayerServiceJPA;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MainController {
	private Player loggedPlayer;

	@Autowired
	private PlayerServiceJPA playerService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	/*
	 * @RequestMapping("/registration") public String registration(Player player) {
	 * String name = player.getName(); Player user = playerService.getPlayer(name);
	 * 
	 * if (user == null) { Player p = new Player(player.getName(),
	 * player.getPasswd()); playerService.addPlayer(p); }
	 * 
	 * return "redirect:/"; }
	 */

	public List<Player> listAllUsers() {
		return playerService.listAllUsers();
	}

	@RequestMapping("/login")
	public String login(String name, String passwd) {
		Player playerInDb = playerService.getPlayer(name);
		if (playerInDb != null) {

			if (passwd.equals(playerInDb.getPasswd())) {

				loggedPlayer = playerInDb;
			}
		}

		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout() {
		loggedPlayer = null;
		return "redirect:/";
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}
}
