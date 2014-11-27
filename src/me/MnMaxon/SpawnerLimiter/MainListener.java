package me.MnMaxon.SpawnerLimiter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

public class MainListener implements Listener {
	private Map<Entity, Location> creatures = new HashMap<Entity, Location>();

	@EventHandler
	public void mobSpawn(CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(SpawnReason.SPAWNER)) {
			CreatureSpawner spawner = null;
			for (int x = -4; x < 4; x++)
				for (int z = -4; z < 4; z++)
					for (int y = -2; y < 2; y++) {
						Block b = e.getLocation().add(x, y, z).getBlock();
						if (b.getState() instanceof CreatureSpawner) {
							// b.getType().equals(Material.MOB_SPAWNER)
							CreatureSpawner cs = (CreatureSpawner) b.getState();
							cs.getCreatureTypeName();
							e.getEntity().getType().name();
							if (cs.getCreatureTypeName().equalsIgnoreCase(e.getEntity().getType().name())) {
								spawner = cs;
							}
						}
					}
			if (spawner != null)
				creatures.put(e.getEntity(), spawner.getLocation());
		}
	}

	@EventHandler
	public void mobDeath(EntityDeathEvent e) {
		if (creatures.containsKey(e.getEntity())) {
			YamlConfiguration data = Config.Load(Main.dataFolder + "/Data");
			int x = creatures.get(e.getEntity()).getBlockX();
			int y = creatures.get(e.getEntity()).getBlockY();
			int z = creatures.get(e.getEntity()).getBlockZ();
			if (creatures.get(e.getEntity()).getBlock().getState() instanceof CreatureSpawner) {
				if (data.get(x + "." + y + "." + z) == null) {
					data.set(x + "." + y + "." + z + ".Type", e.getEntityType().name());
					data.set(x + "." + y + "." + z + ".Spawned", 0);
				}
				if (!data.get(x + "." + y + "." + z + ".Type").equals(e.getEntityType().name())) {
					data.set(x + "." + y + "." + z + ".Spawned", 0);
				}
				data.set(x + "." + y + "." + z + ".Spawned", data.getInt(x + "." + y + "." + z + ".Spawned") + 1);
			} else {
				data.set(x + "." + y + "." + z, null);
			}
			YamlConfiguration cfg = Config.Load(Main.dataFolder + "/Config.yml");
			if (cfg.get("Spawn_Count." + e.getEntityType().name().toUpperCase()) == null) {
				cfg.set("Spawn_Count." + e.getEntityType().name().toUpperCase(), 200);
				Config.Save(cfg, Main.dataFolder + "/Config.yml");
			}
			if (data.getInt(x + "." + y + "." + z + ".Spawned") >= cfg.getInt("Spawn_Count."
					+ e.getEntityType().name().toUpperCase())) {
				creatures.get(e.getEntity()).getBlock().breakNaturally();
				data.set(x + "." + y + "." + z, null);
			}
			Config.Save(data, Main.dataFolder + "/Data");
			// e.getEntityType().name().equals(anObject)
		}
	}
}
