package main.java.com.trophonix.mazesolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

public class AI {

	public Block block;
	public boolean finished;

	public Map<Location, Integer> passed;
	public List<Location> dead;

	public BukkitTask task;

	public Location next;

	public AI(Block b) {
		this.block = b;
		block.setType(Material.DIAMOND_BLOCK);
		this.finished = false;
		passed = new HashMap<Location, Integer>();
		dead = new ArrayList<Location>();
		begin();
	}

	public void begin() {
		task = Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("MazeSolve"), new Runnable() {
			@Override
			public void run() {
				if (!finished) {
					test();
				} else {
					Bukkit.getScheduler().cancelTask(task.getTaskId());
				}
			}
		}, 10, 10);
	}

	public void test() {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				Location test = block.getLocation().clone().add(x, 0, z);
				if (!(test.getBlock().getType().equals(Material.AIR))) {
					continue;
				}
				if (passed.containsKey(test)) {
					continue;
				}
				if (dead.contains(test)) {
					continue;
				}
				next = test;
				walk();
				return;
			}
		}
		hardTest();
	}

	public void hardTest() {
		Location leastTravelled = block.getLocation().clone().add(0, 50, 0);
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				Location test = block.getLocation().clone().add(x, 0, z);
				if (!(test.getBlock().getType().equals(Material.AIR))) {
					continue;
				}
				if (passed.containsKey(leastTravelled)) {
					if (passed.get(test) < passed.get(leastTravelled)) {
						leastTravelled = test;
					}
				} else {
					leastTravelled = test;
				}
				break;
			}
		}
		next = leastTravelled;
		walk();
	}

	public void walk() {
		next.getBlock().setType(block.getType());
		block.setType(Material.AIR);
		
		if (passed.containsKey(block) && passed.get(block.getLocation()) > 5) {
			dead.add(block.getLocation());
		}
		
		block = next.getBlock();

		if (passed.containsKey(block.getLocation())) {
			passed.put(block.getLocation(), passed.get(block.getLocation()) + 1);
		} else {
			passed.put(block.getLocation(), 1);
		}

		Block under = block.getLocation().clone().add(0, -1, 0).getBlock();
		if (under.getType().equals(Material.GOLD_BLOCK)) {
			finished = true;
			Bukkit.broadcastMessage(ChatColor.GOLD + "The AI has beaten the maze!");
		}
	}

}
