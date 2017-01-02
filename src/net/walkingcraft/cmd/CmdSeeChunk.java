package net.walkingcraft.cmd;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.walkingcraft.main.SafeChunk;

public class CmdSeeChunk {

	static int cont;
	static SafeChunk safeChunk;
	static boolean playerActive;

	public CmdSeeChunk(SafeChunk main, CommandSender sender) {
		safeChunk = main;
		cont = 0;
		String playerName = sender.getName();
		Boolean playerFile = null;
		playerFile = safeChunk.getConfig().getBoolean(playerName);
		if (playerFile == null || playerFile == false) {
			playerActive = false;
			main.getConfig().set(playerName, true);
			main.saveConfig();

		} else {
			playerActive = true;
			main.getConfig().set(playerName, false);
			main.saveConfig();
		}
		Player me = (Player) sender;
		// Args
		World world = me.getWorld();
		Chunk chunk = me.getLocation().getChunk();
		int chunkX = chunk.getX();
		int chunkZ = chunk.getZ();

		// Apply
		int blockX;
		int blockZ;

		blockX = chunkX * 16;
		blockZ = chunkZ * 16 - 1;
		for (int value = 0; value < 61; value++) {
			if (value <= 15) {
				blockZ += 1;
			} else if (value <= 30) {
				blockX += 1;
			} else if (value <= 45) {
				blockZ -= 1;
			} else {
				blockX -= 1;
			}
			showPillar(me, world, blockX, blockZ);
		}
	}

	public static void showPillar(Player player, World world, int blockX, int blockZ) {

		int blockY = player.getLocation().getBlockY();
		Location loc = new Location(world, blockX, blockY, blockZ);

		if (playerActive) {
			Material type = Material.AIR;
			addLocation(player, loc, type);
		} else {
			Material type = Material.BIRCH_FENCE;
			addLocation(player, loc, type);
		}

	}

	@SuppressWarnings("deprecation")
	public static void addLocation(Player player, Location location, Material type) {
		if (location.getBlock().getType() == Material.AIR) {
			player.sendBlockChange(location, type, (byte) 1);
		}
		// player.spawnParticle(type, location, 10);
	}

}
