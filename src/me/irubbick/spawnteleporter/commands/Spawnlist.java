package me.irubbick.spawnteleporter.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.irubbick.spawnteleporter.SpawnTeleporter;
import me.irubbick.spawnteleporter.config.PluginConfig;
import me.irubbick.spawnteleporter.types.Message;

/**
 * spawnlist command.
 * @author Sentinel
 * @version 0.1
 */
public class Spawnlist implements CommandExecutor{

	@SuppressWarnings("unused")
	private SpawnTeleporter plugin;
	
	private PluginConfig config;
	
	public Spawnlist(SpawnTeleporter plugin, PluginConfig config) {
		this.plugin = plugin;
		this.config = config;
	}
	
	@Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		
		if (!sender.hasPermission("ST.spawn.spawnlist")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.NO_PERMISSION_CUSTOM)));
			return true;
		}
		
		Player player = (Player) sender;
		
		List<String> spawnlist = config.getSpawnlist();
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.SPAWN_LIST)));
		for (String s : spawnlist) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.SPAWN_FORMAT).replaceAll("%data%", s)));
		}
		
		return true;
	}
	
}
