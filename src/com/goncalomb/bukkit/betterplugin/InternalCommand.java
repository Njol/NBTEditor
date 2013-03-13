package com.goncalomb.bukkit.betterplugin;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.goncalomb.bukkit.lang.Lang;

final class InternalCommand extends Command {
	
	private BetterCommand _command;
	
	public InternalCommand(BetterCommand command, String name) {
		super(name);
		_command = command;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (_command._plugin != null && _command._plugin.isEnabled()) {
			if (_command.hasPermission(sender)) {
				String result = _command.invokeSubCommand(_command, sender, args, 0);
				if (result != null) {
					sender.sendMessage("/" + commandLabel + " " + result);
				}
			} else {
				sender.sendMessage(Lang._("general.commands.no-perm"));
			}
		} else {
			sender.sendMessage("Nop!");
		}
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		if (_command.hasPermission(sender)) {
			BetterSubCommand subCommandEx = _command.getSubCommand(args, 0);
			if (subCommandEx != null) {
				return subCommandEx.getSubCommandNames(sender, args[args.length - 1]);
			}
		}
		return null;
	}
	
}
