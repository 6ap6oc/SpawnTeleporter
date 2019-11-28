package me.irubbick.spawnteleporter.commands;

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

public class Test implements CommandExecutor {

	private SpawnTeleporter plugin;
	private PluginConfig config;
	
	public Test(SpawnTeleporter plugin, PluginConfig config) {
		this.plugin = plugin;
		this.config = config;
		
	}

	@Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.AQUA+"Console collapsed to black hole.");
			return true;
		}
		
		if (!sender.hasPermission("ST.spawn.test")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.NO_PERMISSION_DEFAULT)));
			return true;
		}
		
		if (args.length == 0) {
			sender.sendMessage("Usage: /testspawn afer/before/ info [spawnname]");
			return true;
		}
		
		Teleporter tp = new Teleporter(plugin, config);
		
		if (args[0].equalsIgnoreCase("after")) {
			
			sender.sendMessage(ChatColor.AQUA+"Use: "+config.useParticleAfterTp());
			sender.sendMessage(ChatColor.AQUA+"Particle: "+config.getParticleAfterTp());
			sender.sendMessage(ChatColor.AQUA+"Count: "+config.getCountAfterTp());
			sender.sendMessage(ChatColor.AQUA+"Offset: "+config.getOffsetAfterTp());
			sender.sendMessage(ChatColor.AQUA+"Extra: "+config.getExtraAfterTp());
			
			tp.playParticle(config.getSpawn("default").getLocation(), config.getParticleAfterTp(), config.getCountAfterTp(), config.getOffsetAfterTp(), config.getExtraAfterTp());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("before")) {
			
			sender.sendMessage(ChatColor.AQUA+"Use: "+config.useParticleBeforeTp());
			sender.sendMessage(ChatColor.AQUA+"Particle: "+config.getParticleBeforeTp());
			sender.sendMessage(ChatColor.AQUA+"Count: "+config.getCountBeforeTp());
			sender.sendMessage(ChatColor.AQUA+"Offset: "+config.getOffsetBeforeTp());
			sender.sendMessage(ChatColor.AQUA+"Extra: "+config.getExtraBeforeTp());
			
			tp.playParticle(config.getSpawn("default").getLocation(), config.getParticleBeforeTp(), config.getCountBeforeTp(), config.getOffsetBeforeTp(), config.getExtraBeforeTp());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("info")) {
			
			if (args.length != 2) {
				sender.sendMessage("Usage: /testspawn info [spawnname]");
				return true;
			}
			
			SpawnObject spawn = config.getSpawn(args[1]);
			
			if (spawn == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.SPAWN_NOT_FOUND).replaceAll("%spawn%", args[1])));
				return true;
			}
			
			for (String s : spawn.displayinfo()) {
				sender.sendMessage(s);
			}
			
		}
		
		sender.sendMessage("Usage: /testspawn afer/before/ info [spawnname]");
		return true;
	}
	
}
