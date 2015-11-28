package main.java.com.trophonix.mazesolve;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAI implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be executed by a player.");
			return true;
		}
		Player player = (Player)sender;
		
		AI ai = new AI(player.getLocation().getBlock());
		return true;
	}
	
}
