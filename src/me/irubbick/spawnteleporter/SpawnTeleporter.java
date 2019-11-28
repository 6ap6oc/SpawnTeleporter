package me.irubbick.spawnteleporter;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import me.irubbick.spawnteleporter.Listener.SpawnListener;
import me.irubbick.spawnteleporter.commands.Reloadspawn;
import me.irubbick.spawnteleporter.commands.Setspawn;
import me.irubbick.spawnteleporter.commands.Spawn;
import me.irubbick.spawnteleporter.commands.Spawnlist;
import me.irubbick.spawnteleporter.commands.Test;
import me.irubbick.spawnteleporter.config.PluginConfig;

/**
 * Coffee dream SpawnTeleporter plugin.
 * @author Sentinel
 * @version 0.1
 * @see SpawnObject
 */
public class SpawnTeleporter extends JavaPlugin {

	private Logger log = getLogger();
	
	private PluginConfig config;
	
	@Override
	public void onEnable() {
		log.info("Loading SpawnTeleporter.");
		
		config = new PluginConfig(this);
		
		config.loadConfig();
		
		getCommand("setspawn").setExecutor(new Setspawn(this, config));
		getCommand("spawn").setExecutor(new Spawn(this, config));
		getCommand("spawnlist").setExecutor(new Spawnlist(this, config));
		getCommand("testspawn").setExecutor(new Test(this, config));
		getCommand("reloadspawn").setExecutor(new Reloadspawn(this, config));
		
		getServer().getPluginManager().registerEvents(new SpawnListener(this, config), this);
		
	}
	
    /**
     * Процедура отключения плагина
     */
	@Override
	public void onDisable() {
		log.info("Disabling SpawnTeleporter...");
	}
	
}
