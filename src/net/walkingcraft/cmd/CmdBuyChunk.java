package net.walkingcraft.cmd;

import java.sql.SQLException;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import economy.system.get.GetCoins;
import economy.system.set.RemoveCoins;

public class CmdBuyChunk {
	public double valorTerreno = 100;

	public CmdBuyChunk(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			// Inst�ncias
			GetCoins coin = new GetCoins();
			// Verifica��es
			// Possui permiss�o
			// J� possui terreno?

			// Execu��es

			if (coin.GetCoinsClass(sender.getName()) >= valorTerreno) {
				Player player = (Player) sender;
				int messageKey = addRegion(player, player.getLocation().getWorld());
				if (messageKey == 0) {
					RemoveCoins remove = new RemoveCoins();
					remove.RemoveCoinsClass(sender.getName(), valorTerreno);

					sender.sendMessage("Terreno comprado com sucesso!");
				} else if (messageKey == 1) {

					sender.sendMessage("J� existe um terreno neste local.");
				} else if (messageKey == 2) {

					sender.sendMessage("Voc� j� possui um terreno!");
				}
			} else {

				sender.sendMessage("Voc� n�o possui dinheiro suficiente!");
			}

		} catch (SQLException e) {
		}

	}

	public int addRegion(Player player, World world) {
		Plugin worldguard = player.getServer().getPluginManager().getPlugin("WorldGuard");
		WorldGuardPlugin wg = (WorldGuardPlugin) worldguard;
		ProtectedRegion rg = wg.getRegionManager(world).getRegion("land_" + player.getName() + "_1");
		ApplicableRegionSet loc = wg.getRegionManager(world).getApplicableRegions(player.getLocation());

		if (rg == null) {
			if (!loc.getRegions().isEmpty()) {
				return 1;
			}
			int sizeHeight = player.getWorld().getMaxHeight() - 1;
			int sizeWidth = 15;
			Block block1 = player.getLocation().getChunk().getBlock(0, 0, 0);
			Block block2 = player.getLocation().getChunk().getBlock(sizeWidth, sizeHeight, sizeWidth);
			BlockVector min = new BlockVector(block1.getLocation().getX(), 1, block1.getLocation().getZ());
			BlockVector max = new BlockVector(block2.getLocation().getX(), sizeHeight, block2.getLocation().getZ());

			ProtectedRegion gg = new ProtectedCuboidRegion("land_" + player.getName() + "_1", max, min);

			DefaultDomain owners = new DefaultDomain();
			owners.addPlayer(player.getName());
			gg.setOwners(owners);
			wg.getRegionManager(player.getWorld()).addRegion(gg);
			return 0;
		} else {
			return 2;
		}
	}

}
