package me.irubbick.spawnteleporter.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import me.irubbick.spawnteleporter.config.PluginConfig;


public class SpawnListener implements Listener{

	@SuppressWarnings("unused")
	private final Plugin plugin;
	private final PluginConfig config;
	
	public SpawnListener(Plugin plugin, PluginConfig config) {
	    this.plugin = plugin;
	    this.config = config;
	}
	    
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		config.removeTpFlag(player);
	}
	
}
