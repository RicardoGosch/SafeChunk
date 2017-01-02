package net.walkingcraft.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.walkingcraft.cmd.CmdSafeChunk;
import net.walkingcraft.event.EventMovePlayer;
import net.walkingcraft.event.EventQuitPlayer;

public class SafeChunk extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("terreno").setExecutor(new CmdSafeChunk(this));
		getCommand("ground").setExecutor(new CmdSafeChunk(this));
		getCommand("land").setExecutor(new CmdSafeChunk(this));
		Bukkit.getPluginManager().registerEvents(new EventMovePlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventQuitPlayer(this), this);
		saveDefaultConfig();
	}

	@Override
	public void onDisable() {
	}
}
