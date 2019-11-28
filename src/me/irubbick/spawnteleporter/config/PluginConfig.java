package me.irubbick.spawnteleporter.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.irubbick.spawnteleporter.SpawnObject;
import me.irubbick.spawnteleporter.types.Message;
import net.md_5.bungee.api.ChatColor;

/**
 * Class for plugin configuration.
 * @author Sentinel
 * @version 0.1.14
 */
public class PluginConfig {

	private Map<String, SpawnObject> spawnlist = new HashMap<String, SpawnObject>();
	private Map<Message, String> message = new HashMap<Message, String>();
	
	private List<Player> TP_FLAG = new ArrayList<Player>();
	
	private Plugin plugin;
	private Logger log;
	
	private String locale;
	
	/* вообще эти поля надо было вынести в мапу но мне лень :/ */
	
	//private long TELEPORT_DELAY;
	
	//private boolean USE_PARTICLE_BEFORE_TP;
	//private boolean USE_PARTICLE_AFTER_TP;
	
	//private boolean USE_POTION_BEFORE_TP;
	//private boolean USE_POTION_AFTER_TP;
	
	//private String SOUND_BEFORE_TP;
	//private String SOUND_AFTER_TP;
	
	//private int SOUND_BEFORE_VOLUME;
	//private float SOUND_BEFORE_PITCH;
	
	//private int SOUND_AFTER_VOLUME;
	//private int SOUND_AFTER_PITCH;
	
	//private String PARTICLE_BEFORE_TP;
	//private String PARTICLE_AFTER_TP;
	
	//private int EFFECT_BEFORE_TP_COUNT;
	//private int EFFECT_AFTER_TP_COUNT;
	
	//private String POTION_BEFORE_TP;
	//private String POTION_AFTER_TP;
	
	//private int POTION_DURATION_BEFORE_TP;
	//private int POTION_DURATION_AFTER_TP;
	
	//private int POTION_AMPLIFIER_BEFORE_TP;
	//private int POTION_AMPLIFIER_AFTER_TP;
	
	public PluginConfig(Plugin plugin) {
		this.plugin = plugin;
		this.log = plugin.getLogger();
	}

	public boolean loadConfig () {
		
		this.extartConfigData();
		
		this.checkResource("messages_ru.yml");
		this.checkResource("messages_en.yml");
		
		this.loadMessages();
		this.loadSpawnlist();

		return true;
	}
	
	/**
	 * Извлекает данные из файла конфигурации плагина в обьект PluginConfig
	 */
	private void extartConfigData() {
		plugin.saveDefaultConfig();
		FileConfiguration config = plugin.getConfig();
		
		locale = config.getString("locale");
		//TELEPORT_DELAY = config.getLong("teleport delay (tiks)");
		
		//USE_PARTICLE_BEFORE_TP = config.getBoolean("particle effect.before teleport.use");
		//USE_PARTICLE_AFTER_TP = config.getBoolean("particle effect.after teleport.use");
		
		//USE_POTION_BEFORE_TP = config.getBoolean("potion effect.before teleport.use");
		//USE_POTION_AFTER_TP = config.getBoolean("potion effect.after teleport.use");
		
		//SOUND_BEFORE_TP = config.getString("sound effect.before teleport.type");
		//SOUND_AFTER_TP = config.getString("sound effect.after teleport.type");
		
		//SOUND_BEFORE_VOLUME  = config.getInt("sound effect.before teleport.volume");
		//SOUND_BEFORE_PITCH = config.getInt("sound effect.before teleport.pitch");
		
		//SOUND_AFTER_VOLUME  = config.getInt("sound effect.after teleport.volume");
		//SOUND_AFTER_PITCH  = config.getInt("sound effect.after teleport.pitch");
		
		//PARTICLE_BEFORE_TP  = config.getString("particle effect.before teleport.type");
		//PARTICLE_AFTER_TP = config.getString("particle effect.before teleport.type");
		
		//EFFECT_BEFORE_TP_COUNT  = config.getInt("sound effect.before teleport.count");
		//EFFECT_AFTER_TP_COUNT  = config.getInt("sound effect.after teleport.count");
		
		//POTION_BEFORE_TP = config.getString("potion effect.before teleport.type");
		//POTION_AFTER_TP = config.getString("potion effect.after teleport.type");
		
		//POTION_DURATION_BEFORE_TP = config.getInt("potion effect.before teleport.duration (tiks)");
		//POTION_DURATION_AFTER_TP = config.getInt("potion effect.after teleport.duration (tiks)");
		
		//POTION_AMPLIFIER_BEFORE_TP = config.getInt("potion effect.after teleport.amplifier");
		//POTION_AMPLIFIER_AFTER_TP = config.getInt("potion effect.after teleport.amplifier");
		
	}

	/**
	 * Загрузка сообщений с локализацией
	 */
	private void loadMessages() {
		File file = new File(plugin.getDataFolder()+File.separator+"messages_"+locale+".yml");
		
		if (!file.exists()) {
			log.severe(ChatColor.RED+"Could not find "+"messages_"+locale+".yml"+" , disabling plugin.");
			Bukkit.getServer().getPluginManager().disablePlugin(plugin);
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		for (String key : config.getKeys(false)) {
			message.put(Message.fromString(key), config.getString(ChatColor.translateAlternateColorCodes('&', key)));
		}
		
		log.info("Loaded "+message.size()+" plugin messages.");
	}

	/**
	 * Проверка ресурса на существование. Если указанный ресурс отсутствует в рабочей папке плагина - будет извлечен и помещен в неё.
	 * @param source - String
	 */
	private void checkResource(String source) {
		File file = new File(plugin.getDataFolder()+File.separator+source);
		
		if (!file.exists()) {
			plugin.saveResource(source, false);
			log.info("Extracted "+source+" to plugin folder.");
		}
		
	}

	/**
	 * Загрузка перечня спаунов в кеш плагина
	 */
	public void loadSpawnlist() {
		
		File file = new File(plugin.getDataFolder()+File.separator+"spawnlist.yml");
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if (config.contains("spawnlist")) {
			
			for (String source : config.getStringList("spawnlist")) {
				String s[] = source.split(":");
				String tag[] = s[1].split(",");
				
				SpawnObject spawn = new SpawnObject();
				
				spawn.setLocation(s[0], 
						Bukkit.getServer().getWorld(tag[0]), 
						Double.parseDouble(tag[1]), 
						Double.parseDouble(tag[2]), 
						Double.parseDouble(tag[3]), 
						Double.parseDouble(tag[4]), 
						Double.parseDouble(tag[5]));
				
				spawnlist.put(s[0], spawn);
			
			}
			
			log.info("Loaded "+spawnlist.size()+" custom spawnpoints.");
		}
			
		if (!spawnlist.containsKey("default")) {
			log.info("Using default spawnpoint.");
			SpawnObject spawn = new SpawnObject();
			
			World w = Bukkit.getServer().getWorld("world");
			spawn.setLocation(w.getSpawnLocation(), "default" );
			spawnlist.put("default", spawn);
			
			this.saveSpawnList();
			
		} else {
			log.info("Using custom spawnpoint.");
		}	
		
	}
	
	/**
	 * Сохрание кеша в файл конфигураций
	 * @return boolean
	 */
	public boolean saveSpawnList() {
		
		File file = new File(plugin.getDataFolder()+File.separator+"spawnlist.yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.severe("Error creating file in the default plugin folder. See stacktrace.");
				e.printStackTrace();
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		List<String> list = new ArrayList<String>();
		
		for (Entry<String, SpawnObject> entry : spawnlist.entrySet()) {
			SpawnObject spawn = entry.getValue();
			
			/*
			for (String s : spawn.displayinfo()) {
				log.warning(s);
			}
			*/
			
			String source = 
					spawn.getName()+":"
					+spawn.getWorld().getName()+","
					+spawn.getX()+","
					+spawn.getY()+","
					+spawn.getZ()+","
					+spawn.getPitch()+","
					+spawn.getYaw();
					list.add(source);
		}
		
		config.set("spawnlist", list);
		
		try {
			config.save(file);
		} catch (IOException e) {
			log.severe("Exception in save spawnlist to file. See stacktrace.");
			e.printStackTrace();
		}
		
		log.info("Saved "+list.size()+" spawnpoints.");
		
		return true;
	}
	
	/**
	 * Вернет true, если кастомные точки возрождения не используются.
	 * @return boolean
	 */
	public boolean hasDefaultSpawn() {
		return spawnlist.containsKey("default");
	}
	
	/**
	 * Получение обьекта спауна из кеша плагина.
	 * @param name - String
	 * @return SpawnObject
	 */
	public SpawnObject getSpawn(String name) {
		return spawnlist.get(name);
	}
	
	/**
	 * Перезагрузка файла конфигурации
	 */
	public void reloadConfig() {
		spawnlist.clear();
		message.clear();
		plugin.reloadConfig();
		this.loadMessages();
		this.loadSpawnlist();
	}
	
	/**
	 * Получение перечня обьектов спауна из кеша плагина.
	 * @return List<String>
	 */
	public List<String> getSpawnlist() {
		List<String> list = new ArrayList<String>();
		
		for (Entry<String, SpawnObject> entry : spawnlist.entrySet()) {
			SpawnObject spawn = entry.getValue();
			
			list.add("Spawn: "+spawn.getName()
			+" World: "+spawn.getWorld().getName()
			+" Coords: "
					+spawn.getX()+","
					+spawn.getY()+","
					+spawn.getZ()+".");
		}
		return list;
	}
	
	/**
	 * Добавление обьекта спауна в кеш плагина
	 * @param spawn - SpawnObject
	 * @param name - String, spawn name
	 */
	public void addSpawn(SpawnObject spawn, String name) {
		spawnlist.put(name, spawn);		
	}
	
	/**
	 * Получение строки сообщения из файла конфигурации.
	 * @param msg - Message (Enum)
	 * @return String
	 */
	public String getMessage(Message msg) {
		return message.get(msg);
	}
	
	/**
	 * 
	 * @return long
	 */
	public long getTeleportDelay() {
		return plugin.getConfig().getLong("teleport delay (tiks)");
	}

	public boolean useSoundBeforeTp() {
		return plugin.getConfig().getBoolean("sound effect.before teleport.use");
	}

	public boolean useSoundAfterTp() {
		return plugin.getConfig().getBoolean("sound effect.after teleport.use");
	}

	public String getSoundBeforeTp() {
		return plugin.getConfig().getString("sound effect.before teleport.type");
	}

	public float getPitchBeforeTp() {
		return plugin.getConfig().getInt("sound effect.before teleport.pitch");
	}

	public float getVolumeBeforeTp() {
		return plugin.getConfig().getInt("sound effect.before teleport.volume");
	}

	public boolean useParticleBeforeTp() {
		return plugin.getConfig().getBoolean("particle effect.before teleport.use");
		
	}
	
	public boolean useParticleAfterTp() {
		return plugin.getConfig().getBoolean("particle effect.after teleport.use");
	}

	public int getCountBeforeTp() {
		return plugin.getConfig().getInt("particle effect.before teleport.count");
	}
	
	public int getCountAfterTp() {
		return plugin.getConfig().getInt("particle effect.after teleport.count");
	}

	public String getParticleBeforeTp() {
		return plugin.getConfig().getString("particle effect.before teleport.type");
	}
	
	public String getParticleAfterTp() {
		return plugin.getConfig().getString("particle effect.after teleport.type");
	}

	public float getPitchAfterTp() {
		return plugin.getConfig().getInt("sound effect.after teleport.pitch");
	}

	public String getSoundAfterTp() {
		return plugin.getConfig().getString("sound effect.after teleport.type");
	}

	public float getVolumeAfterTp() {
		return plugin.getConfig().getInt("sound effect.after teleport.volume");
	}

	public boolean usePotionBeforeTp() {
		return plugin.getConfig().getBoolean("potion effect.before teleport.use");
	}

	public boolean usePotionAfterTp() {
		return plugin.getConfig().getBoolean("potion effect.after teleport.use");
	}

	public String getPotionBeforeTp() {
		return plugin.getConfig().getString("potion effect.before teleport.type");
	}

	public String getPotionAfterTp() {
		return plugin.getConfig().getString("potion effect.after teleport.type");
	}

	public int getDurationBeforeTp() {
		return plugin.getConfig().getInt("potion effect.before teleport.duration (tiks)");
	}

	public int getDurationAfterTp() {
		return plugin.getConfig().getInt("potion effect.after teleport.duration (tiks)");
	}
	
	public int getAmplifierBeforeTp() {
		return plugin.getConfig().getInt("potion effect.before teleport.amplifier");
	}
	
	public int getAmplifierAfterTp() {
		return plugin.getConfig().getInt("potion effect.after teleport.amplifier");
	}
	
	public boolean hidePotionParticlesBeforeTp() {
		return plugin.getConfig().getBoolean("potion effect.before teleport.hide particle");
	}
	
	public boolean hidePotionParticlesAfterTp() {
		return plugin.getConfig().getBoolean("potion effect.after teleport.hide particle");
	}

	public double getOffsetBeforeTp() {
		return plugin.getConfig().getDouble("particle effect.before teleport.offset");
	}

	public int getExtraBeforeTp() {
		return plugin.getConfig().getInt("particle effect.before teleport.extra");
	}

	public double getOffsetAfterTp() {
		return plugin.getConfig().getDouble("particle effect.after teleport.offset");
	}

	public int getExtraAfterTp() {
		return plugin.getConfig().getInt("particle effect.after teleport.extra");
	}

	public boolean useFixedTp() {
		return plugin.getConfig().getBoolean("use fixed teleport location");
	}
	
	public int getTeleportRadius() {
		return plugin.getConfig().getInt("teleportation radius");
	}
	
	public void setTpFlag(Player player) {
		TP_FLAG.add(player);
	}
	
	public boolean hasTpFlag(Player player) {
		return TP_FLAG.contains(player);
	}
	
	public boolean removeTpFlag(Player player) {
		if (TP_FLAG.contains(player)) {
			TP_FLAG.remove(player);
			return true;
		} else {
			return false;
		}
	}
}
