package me.irubbick.spawnteleporter.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.irubbick.spawnteleporter.SpawnObject;
import me.irubbick.spawnteleporter.SpawnTeleporter;
import me.irubbick.spawnteleporter.config.PluginConfig;
import me.irubbick.spawnteleporter.config.Teleporter;
import me.irubbick.spawnteleporter.types.Message;

/**
 * Spawn command. Usage: /spawn or /spawn [name]
 * @author Sentinel
 * @version 0.1
 */
public class Spawn implements CommandExecutor{

	private SpawnTeleporter plugin;
	
	private PluginConfig config;
	
	public Spawn(SpawnTeleporter plugin, PluginConfig config) {
		this.plugin = plugin;
		this.config = config;
	}

	@Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.AQUA+"Console collapsed to black hole.");
			return true;
		}
		
		if (!sender.hasPermission("ST.spawn.use")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.NO_PERMISSION_DEFAULT)));
			return true;
		}
		
		Player player = (Player) sender;
		
		if (config.hasTpFlag(player)) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.TP_PROCESSED)));
			return true;
		}
		
		if (args.length == 0) {
				config.setTpFlag(player);
				SpawnObject spawn = config.getSpawn("default");
				
				Teleporter tp = new Teleporter(plugin, config);
				tp.teleport(player, spawn);
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.TELEPORTED_TO_SPAWN)));
				this.notifyAdmin(player, "default");
			return true;
		} else {
			
			if (!player.hasPermission("ST.spawn.customspawn")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.NO_PERMISSION_CUSTOM)));
				return true;
			}
			
			SpawnObject spawn = config.getSpawn(args[0]);
			
			if (spawn == null) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.SPAWN_NOT_FOUND).replaceAll("%spawn%", args[0])));
				return true;
			}
			config.setTpFlag(player);
			Teleporter tp = new Teleporter(plugin, config);
			tp.teleport(player, spawn);
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.TELEPORTED_TO_CUSTOMSPAWN).replaceAll("%spawn%", args[0])));
			this.notifyAdmin(player, spawn.getName());
		}
		
		return true;
	}
	
	private void notifyAdmin(Player player, String spawn) {
		
		String source = config.getMessage(Message.ADMIN_NOTIFY).replaceAll("%spawn%", spawn).replaceAll("%player%", player.getName());
		
		Bukkit.getServer().getOnlinePlayers().forEach(pp -> {
			if (pp.hasPermission("ST.admin.notify")) {
				if (pp != player) {
					pp.sendMessage(ChatColor.translateAlternateColorCodes('&', source));
				}
			}
		});
	}
	
}
