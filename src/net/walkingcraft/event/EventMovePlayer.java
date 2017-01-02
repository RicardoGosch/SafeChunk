package net.walkingcraft.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.walkingcraft.cmd.CmdSeeChunk;
import net.walkingcraft.main.SafeChunk;

public class EventMovePlayer implements Listener {

	SafeChunk safeChunk;

	public EventMovePlayer(SafeChunk main) {

		safeChunk = main;
	}

	@EventHandler
	public void onMovePlayer(PlayerMoveEvent e) {
		Location from = e.getFrom();
		Location to = e.getTo();
		if (from.getBlockX() - to.getBlockX() > 0.1 || from.getBlockX() - to.getBlockX() < -0.1
				|| from.getBlockY() - to.getBlockY() > 0.1 || from.getBlockY() - to.getBlockY() < -0.1
				|| from.getBlockZ() - to.getBlockZ() > 0.1 || from.getBlockZ() - to.getBlockZ() < -0.1) {

			Player player = e.getPlayer();
			String playerName = player.getName();
			Boolean playerFile = false;
			playerFile = safeChunk.getConfig().getBoolean(playerName);
			if (playerFile) {
				new CmdSeeChunk(safeChunk, player);
			}
		}
	}
}