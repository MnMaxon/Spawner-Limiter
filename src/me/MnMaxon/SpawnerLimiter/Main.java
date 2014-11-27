package me.MnMaxon.SpawnerLimiter;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	public static String dataFolder;
	public static Main plugin;

	@Override
	public void onEnable() {
		plugin = this;
		dataFolder = this.getDataFolder().getAbsolutePath();
		setupConfig();
		Config.Load(dataFolder + "/Data");
		getServer().getPluginManager().registerEvents(new MainListener(), this);
	}

	public static YamlConfiguration setupConfig() {
		cfgSetter("Spawn_Count.BLAZE", 200);
		cfgSetter("Spawn_Count.CAVE_SPIDER", 200);
		cfgSetter("Spawn_Count.CREEPER", 200);
		cfgSetter("Spawn_Count.ENDER_DRAGON", 200);
		cfgSetter("Spawn_Count.ENDERMAN", 200);
		cfgSetter("Spawn_Count.GHAST", 200);
		cfgSetter("Spawn_Count.MAGMA_CUBE", 200);
		cfgSetter("Spawn_Count.SILVERFISH", 200);
		cfgSetter("Spawn_Count.SKELETON", 200);
		cfgSetter("Spawn_Count.SLIME", 200);
		cfgSetter("Spawn_Count.SPIDER", 200);
		cfgSetter("Spawn_Count.WITCH", 200);
		cfgSetter("Spawn_Count.ZOMBIE", 200);
		cfgSetter("Spawn_Count.PIG_ZOMBIE", 200);
		cfgSetter("Spawn_Count.BAT", 200);
		cfgSetter("Spawn_Count.CHICKEN", 200);
		cfgSetter("Spawn_Count.COW", 200);
		cfgSetter("Spawn_Count.HORSE", 200);
		cfgSetter("Spawn_Count.MUSHROOM_COW", 200);
		cfgSetter("Spawn_Count.OCELOT", 200);
		cfgSetter("Spawn_Count.PIG", 200);
		cfgSetter("Spawn_Count.SHEEP", 200);
		cfgSetter("Spawn_Count.SQUID", 200);
		cfgSetter("Spawn_Count.WOLF", 200);
		cfgSetter("Spawn_Count.VILLAGER", 200);
		cfgSetter("Spawn_Count.IRON_GOLEM", 200);
		cfgSetter("Spawn_Count.SNOWMAN", 200);
		return Config.Load(dataFolder + "/Config.yml");
	}

	public static void cfgSetter(String path, Object value) {
		YamlConfiguration cfg = Config.Load(dataFolder + "/Config.yml");
		if (cfg.get(path) == null) {
			cfg.set(path, value);
			Config.Save(cfg, dataFolder + "/Config.yml");
		}
	}
}