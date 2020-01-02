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
	public String message;

	@Autowired
	private PlayerServiceJPA playerService;

	@RequestMapping("/")
	public String index() {
		message = "";
		return "index";
	}

	@RequestMapping("/login")
	public String login(String name, String passwd) {
		Player playerInDb = playerService.getPlayer(name);
		try {
			if (playerInDb != null) {

				if (passwd.equals(playerInDb.getPasswd())) {

					loggedPlayer = playerInDb;
				} else {
					message = "Wrong password";
					return "index";
				}
			} else {
				message = "User doesn't exist";
				return "index";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public String getMessage() {
		return message;
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}

	public List<Player> listAllUsers() {
		return playerService.listAllUsers();
	}
}
