package net.walkingcraft.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.walkingcraft.main.SafeChunk;

public class EventQuitPlayer implements Listener {
	SafeChunk safeChunk;

	public EventQuitPlayer(SafeChunk main) {
		safeChunk = main;
	}

	@EventHandler
	public void onQuitPlayer(PlayerQuitEvent e) {
		if (playerEmpty(e.getPlayer())) {
			safeChunk.getConfig().set(e.getPlayer().getName(), false);
			safeChunk.saveConfig();
		}
	}

	public boolean playerEmpty(Player player) {
		Boolean isSet;
		isSet = safeChunk.getConfig().isSet(player.getName());
		if (!isSet) {
			return false;
		}
		return true;
	}
}