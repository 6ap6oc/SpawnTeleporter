package me.irubbick.spawnteleporter.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.irubbick.spawnteleporter.SpawnObject;
import me.irubbick.spawnteleporter.types.Message;

/**
 * 
 * @author Sentinel
 * @version 0.1
 */
public class Teleporter {

	private Plugin plugin;
	private PluginConfig config;
	
	public Teleporter(Plugin plugin, PluginConfig config) {
		this.plugin = plugin;
		this.config = config;
	}

	
	/**
	 * Телепортация игрока с различными свистоперделками.
	 * @param player - Bukkit player
	 * @param spawn - SpawnObject
	 */
	public void teleport(Player player, SpawnObject spawn) {

		if (config.usePotionBeforeTp()) {
			this.applyPotion(player, config.getPotionBeforeTp(), config.getDurationBeforeTp(), config.getAmplifierBeforeTp(), config.hidePotionParticlesBeforeTp());
		}
		
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			
			if (config.useParticleBeforeTp()) {
				this.playParticle(player.getLocation(), config.getParticleBeforeTp(), config.getCountBeforeTp(), config.getOffsetBeforeTp(), config.getExtraBeforeTp());
			}
			
			if (config.useSoundBeforeTp()) {
				this.playsound(player.getLocation(), config.getSoundBeforeTp(), config.getVolumeBeforeTp(), config.getPitchBeforeTp());
			}
			
			if (config.useFixedTp()) {
				boolean result = this.fixedSafeTp(player, spawn);
				if (!result) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.BROKEN_SPAWN).replaceAll("%spawn%", spawn.getName())));
				}
			} else {
				boolean result = this.nonfixedSafeTp(player, spawn);
				if (!result) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMessage(Message.BROKEN_SPAWN).replaceAll("%spawn%", spawn.getName())));
				}
			}
			
			config.removeTpFlag(player);
			
			if (config.useSoundAfterTp()) {
				this.playsound(player.getLocation(), config.getSoundAfterTp(), config.getVolumeAfterTp(), config.getPitchAfterTp());
			}
			
			if (config.useParticleAfterTp()) {
				this.playParticle(player.getLocation(), config.getParticleAfterTp(), config.getCountAfterTp(), config.getOffsetAfterTp(), config.getExtraAfterTp());
			}
			
			if (config.usePotionAfterTp()) {
				this.applyPotion(player, config.getPotionAfterTp(), config.getDurationAfterTp(), config.getAmplifierAfterTp(), config.hidePotionParticlesAfterTp());
			}
			
		}, config.getTeleportDelay());
		
	}
	
	/**
     * Checks if a location is safe (solid ground with 2 breathable blocks).
     * This code is taken from the forum thread: https://www.spigotmc.org/threads/safely-teleport-players.83205/
     * @param location Location to check
     * @return True if location is safe
     */
    @SuppressWarnings("deprecation")
	public boolean isSafeLocation(Location location) {
        Block feet = location.getBlock();
        
        if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
            return false; // not transparent (will suffocate)
        }
        Block head = feet.getRelative(BlockFace.UP);
        if (!head.getType().isTransparent()) {
            return false; // not transparent (will suffocate)
        }
        Block ground = feet.getRelative(BlockFace.DOWN);
        if (!ground.getType().isSolid()) {
            return false; // not solid
        }
        return true;
    }
	
     /** 
     * Attempts to generate a pseudo-random value to safe location.
	 * In the first cycle, pseudo-random coordinates are generated, in the second cycle, the obtained location is checked for safe teleportation.
	 * This solution is far from the fastest, but it works.
     * 
     * @param player
     * @param spawn
     * @return boolean
     */
    public boolean nonfixedSafeTp(Player player, SpawnObject spawn) {
    	
    	boolean result = false;
    	
    	boolean SAFETY_TP = false;
		
		int generationSamples = 0;
		int samplesY = 0;
		
		while (!SAFETY_TP) {
			
			generationSamples++;
			
			int randomX = this.randomValue(config.getTeleportRadius());
			int randomZ = this.randomValue(config.getTeleportRadius());
			int offsetY = (int) spawn.getY()-config.getTeleportRadius();
			
			for (int i = offsetY; i < 256; i++ ) {
				
				samplesY++;
				
				Location loc = spawn.getLocation().clone().add(randomX, 0, randomZ);
				loc.setY(i);
				
				if (this.isSafeLocation(loc)) {
					if (!loc.getChunk().isLoaded()) {
						loc.getChunk().load();
					}
					plugin.getLogger().warning("Safe location found, player teleported to: "+loc.toString());
					player.teleport(loc);
					SAFETY_TP = true;
					result = true;
					break;
				}
				
			}
			
			if (generationSamples == 100) {
				SAFETY_TP = true;
				
			}
			
			if (samplesY == 10000) {
				SAFETY_TP = true;
			}
		}
		
		plugin.getLogger().info("Generation samples: "+generationSamples+"; Y coordinate samples: "+samplesY);
		
		return result;
		
    }
    
    /**
     * 
     * @param player
     * @param spawn
     * @return
     */
    public boolean fixedSafeTp(Player player, SpawnObject spawn) {
    	
    	if (this.isSafeLocation(spawn.getLocation())) {
    		player.teleport(spawn.getLocation());
    		return true;
    	}
    	
    	return this.nonfixedSafeTp(player, spawn);
    }
    
    /**
     * Pseudo random number generation in a given range.
     * @param value - Integer, the spread of number generation is within +/-
     * @return int - Random value of range -value...value
     */
    public  int randomValue(int value){
    	int min = 0 - value;
    	value -= min;
    	return (int) (Math.random() * ++value) + min;
    }
    
	public void playsound(Location loc, String sound, float volume, float pitch) {
		World w = loc.getWorld();
		w.playSound(loc, Sound.valueOf(sound), volume, pitch);
		
	}
	
	public void playeffect(Location loc, String effect, int radius) {
		World w = loc.getWorld();
		w.playEffect(loc, Effect.valueOf(effect), radius);
		
	}

	public void playParticle(Location loc, String particle, int count, double offset, int extra) {
		World w = loc.getWorld();
		
		Location location = loc.clone().add(0, 0.5, 0);
		
		w.spawnParticle(Particle.valueOf(particle), location, count, offset, offset, offset, extra);
		
	}
	
	public void applyPotion(Player player, String potion, int duration, int amplifier, boolean hideparticles ) {
		player.addPotionEffect( new PotionEffect(PotionEffectType.getByName(potion), duration, amplifier), hideparticles);
	}
	
}
