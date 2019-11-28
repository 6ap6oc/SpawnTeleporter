package me.irubbick.spawnteleporter.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.irubbick.spawnteleporter.SpawnObject;
import me.irubbick.spawnteleporter.SpawnTeleporter;
import me.irubbick.spawnteleporter.config.PluginConfig;
import me.irubbick.spawnteleporter.types.Message;

/**
 * SetSpawn command. Usage: /setspawn or /setspawn [name]
 * @author Sentinel
 * @version 0.1
 */
public class Setspawn implements CommandExecutor{

	@SuppressWarnings("unused")
	private SpawnTeleporter plugin;
	
	private PluginConfig config;
	
	public Setspawn(SpawnTeleporter plugin, PluginConfig config) {
		this.plugin = plugin;
		this.config = config;
	}

	@Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Console collapsed to black hole.");
			return true;
		}
		
		if (!sender.hasPermission("ST.spawn.set")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.NO_PERMISSION)));
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length == 0) {
			SpawnObject spawn = new SpawnObject();
			spawn.setLocation(player.getLocation(), "default");
			config.addSpawn(spawn, "default");
			config.saveSpawnList();
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.SPAWN_SET).replaceAll("%spawn%", "default")));
		} else {
			SpawnObject spawn = new SpawnObject();
			spawn.setLocation(player.getLocation(), args[0]);
			config.addSpawn(spawn, args[0]);
			config.saveSpawnList();
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.SPAWN_SET).replaceAll("%spawn%", args[0])));
		}

		return true;
	}
	
}
