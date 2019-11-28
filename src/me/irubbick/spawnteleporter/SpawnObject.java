package me.irubbick.spawnteleporter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Object for custom spawn location
 * @author Sentinel
 * @version 0.1
 */
public class SpawnObject {
	
	/* поле - локация */
	private Location loc;
	
	/* поле - имя локации */
	private String name;
	
	/* поле - мир */
	private World world;
	
	/* поле - координата х */
	private double x;
	
	/* поле - координата y */
	private double y;
	
	/* поле - координата z */
	private double z;
	
	/* поле - направление по вертикали */
	private double pitch;
	
	/* поле - направление по горизонтали */
	private double yaw;
	
	{
		name = "default";
		x = 0;
		y = 0;
		z = 0;
		pitch = 0;
		yaw = 0;
		loc = null;
	}
	
	/**
	 * Отображение сводной информации по обьекту
	 * @return List<String> 
	 */
	public List<String> displayinfo() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("SpawnObject.");
		list.add("Name: "+name);
		list.add("World: "+world);
		list.add("Location: "+loc);
		list.add("X coord: "+x);
		list.add("Y coord: "+y);
		list.add("z coord: "+z);
		list.add("Pitch: "+pitch);
		list.add("Yaw: "+yaw);
		
		return list;
	}
	
	/**
	 * Установка данных точки спауна.
	 * @param name - String, имя точки спауна
	 * @param world - Bukkit World
	 * @param x - double, координата
	 * @param y - double, координата
	 * @param z - double, координата
	 * @param pitch - double, направление взгляда по горизонтали
	 * @param yaw - double, направление взгляда по вертикали
	 */
	public void setLocation(String name, World world, double x, double y, double z, double pitch, double yaw) {
		Location l = new Location(world, x, y, z);
		this.loc = l;
		this.world = loc.getWorld();
		this.name = name;
		this.pitch = pitch;
		this.yaw = yaw;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Установка данных точки спауна.
	 * @param loc - Location
	 * @param name  - String, имя точки спауна
	 */
	public void setLocation(Location loc, String name) {
		this.loc = loc;
		
		this.name = name;
		
		this.pitch = loc.getPitch();
		this.yaw = loc.getYaw();
		
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		
		this.world = loc.getWorld();
	}
	
	/**
	 * Получение локации точки спауна.
	 * @return Location
	 */
	public Location getLocation() {
		return loc;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public double getPitch() {
		return pitch;
	}
	
	public double getYaw() {
		return yaw;
	}
	
	public String getName() {
		return name;
	}
	
	public World getWorld() {
		return world;
	}

}
