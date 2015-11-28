package main.java.com.trophonix.mazesolve;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {

	public void onEnable() {
		init();
		System.out.println("[MazeSolve] Initialized Successfully!");
	}
	
	public void onDisable() {
		
	}
	
	public void init() {
		getCommand("ai").setExecutor(new CommandAI());
	}
	
}
