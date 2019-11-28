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
	
	/* ���� - ������� */
	private Location loc;
	
	/* ���� - ��� ������� */
	private String name;
	
	/* ���� - ��� */
	private World world;
	
	/* ���� - ���������� � */
	private double x;
	
	/* ���� - ���������� y */
	private double y;
	
	/* ���� - ���������� z */
	private double z;
	
	/* ���� - ����������� �� ��������� */
	private double pitch;
	
	/* ���� - ����������� �� ����������� */
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
	 * ����������� ������� ���������� �� �������
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
	 * ��������� ������ ����� ������.
	 * @param name - String, ��� ����� ������
	 * @param world - Bukkit World
	 * @param x - double, ����������
	 * @param y - double, ����������
	 * @param z - double, ����������
	 * @param pitch - double, ����������� ������� �� �����������
	 * @param yaw - double, ����������� ������� �� ���������
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
	 * ��������� ������ ����� ������.
	 * @param loc - Location
	 * @param name  - String, ��� ����� ������
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
	 * ��������� ������� ����� ������.
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
