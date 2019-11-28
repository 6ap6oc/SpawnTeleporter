package me.irubbick.spawnteleporter.types;

/**
 * Перечисление типов сообщений согласно файлу конфигурации.
 * @author Sentinel
 * @version 0.1.14
 */
public enum Message {

	NO_PERMISSION("no permission"),
	NO_PERMISSION_DEFAULT("no permission for default spawnpoint"),
	NO_PERMISSION_CUSTOM("no permission for custom spawnpoint"),
	SPAWN_SET("spawnpoint set"),
	TELEPORTED_TO_SPAWN("teleported to spawnpoint"),
	TELEPORTED_TO_CUSTOMSPAWN("teleported to custom spawnpoint"),
	SPAWN_NOT_FOUND("spawnpoint not found"),
	RELOAD_CONFIG("reload config"),
	SPAWN_LIST("spawnpoint list"),
	SPAWN_FORMAT("spawnpoint format"),
	BROKEN_SPAWN("broken spawnpoint"),
	TP_PROCESSED("teleportation is processed"),
	ADMIN_NOTIFY("admin notify");
	
	private String data;
	
    private Message(String data){
        this.data = data;
    }

    /**
     * Возвращает название строки данных согласно файлу конфигурации
     * @return String
     */
	public String get(){
        return this.data;
    }

	/**
	 * Конвертирует строку в Enum.
	 * @param source - String
	 * @return Message - Enum.
	 */
	public static Message fromString(final String source) {
		
		String s = source.toUpperCase();
		
		switch (s) {
		
		case "NO PERMISSION": {
			return NO_PERMISSION;
		}
		
		case "NO PERMISSION FOR DEFAULT SPAWNPOINT": {
			return NO_PERMISSION_DEFAULT;
		}
		
		case "NO PERMISSION FOR CUSTOM SPAWNPOINT": {
			return NO_PERMISSION_CUSTOM;
		}
		
		case "SPAWNPOINT SET": {
			return SPAWN_SET;
		}
		
		case "TELEPORTED TO SPAWNPOINT": {
			return TELEPORTED_TO_SPAWN;
		}
		
		case "TELEPORTED TO CUSTOM SPAWNPOINT": {
			return TELEPORTED_TO_CUSTOMSPAWN;
		}
		
		case "SPAWNPOINT NOT FOUND": {
			return SPAWN_NOT_FOUND;
		}
		
		case "RELOAD CONFIG": {
			return RELOAD_CONFIG;
		}
		
		case "SPAWNPOINT LIST": {
			return SPAWN_LIST;
		}
		
		case "SPAWNPOINT FORMAT": {
			return SPAWN_FORMAT;
		}
		
		case "BROKEN SPAWNPOINT":{
			return BROKEN_SPAWN;
		}
		
		case "TELEPORTATION IS PROCESSED":{
			return TP_PROCESSED;
		}
		
		case "ADMIN NOTIFY":{
			return ADMIN_NOTIFY;
		}
		default:{
			return null;
		}
		
		}
		
	}
	
}
