package me.irubbick.spawnteleporter.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.irubbick.spawnteleporter.SpawnTeleporter;
import me.irubbick.spawnteleporter.config.PluginConfig;
import me.irubbick.spawnteleporter.types.Message;

public class Reloadspawn implements CommandExecutor{

	private SpawnTeleporter plugin;
	private PluginConfig config;
	
	public Reloadspawn(SpawnTeleporter plugin, PluginConfig config) {
		this.plugin = plugin;
		this.config = config;
	}
	
	@Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

			if (!sender.hasPermission("ST.spawn.reload")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.NO_PERMISSION)));
				return true;
			}
			
			config.reloadConfig();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.RELOAD_CONFIG)));
			plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.RELOAD_CONFIG)));
			return true;
	}

}
