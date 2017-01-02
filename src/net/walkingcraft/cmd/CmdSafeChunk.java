package net.walkingcraft.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.walkingcraft.main.SafeChunk;

public class CmdSafeChunk implements CommandExecutor {
	SafeChunk safeChunk;
	// String world;

	public CmdSafeChunk(SafeChunk main) {
		safeChunk = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			switch (args.length) {
			case 1:
				switch (args[0].toLowerCase()) {
				case "sc":
					if (sender.hasPermission("land.buy")) {
						new CmdSeeChunk(safeChunk, sender);
					} else {
						sender.sendMessage("Comando desconhecido.");
					}
					break;

				case "comprar":
					if (sender.hasPermission("land.buy")) {
						new CmdBuyChunk(sender, cmd, label, args);
					} else {
						sender.sendMessage("Comando desconhecido.");
					}

					break;

				case "buy":
					if (sender.hasPermission("land.buy")) {
						new CmdBuyChunk(sender, cmd, label, args);
					} else {
						sender.sendMessage("Comando desconhecido.");
					}
					break;
				}
				break;
			default:
				sender.sendMessage("/terreno <comprar/deletar>");
				break;
			}
		}
		return false;
	}

}
