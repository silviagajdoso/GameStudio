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

public class RegistrationController {

	public String message;

	@Autowired
	private PlayerServiceJPA playerService;

	@RequestMapping("/registration")
	public String index() {
		message = "";
		return "registration";
	}

	@RequestMapping("/registration/signin")
	public String registration(Player player) {
		String name = player.getName();
		String password = player.getPasswd();
		Player user = playerService.getPlayer(name);

		try {
			if (user != null) {
				{
					message = "Username already in use";
					return "registration";
				}

			}
			if (name.length() < 5) {
				message = "Username must have at least 5 characters.";
				return "registration";
			}
			if (password == "") {
				message = "Password can't be empty";
				return "registration";

			} else {
				Player p = new Player(player.getName(), player.getPasswd());
				playerService.addPlayer(p);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/";
	}

	public List<Player> listAllUsers() {
		return playerService.listAllUsers();
	}
}
