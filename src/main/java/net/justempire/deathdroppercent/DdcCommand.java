package net.justempire.deathdroppercent;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DdcCommand implements CommandExecutor, TabCompleter {
    private DeathDropPercent plugin;

    public DdcCommand(DeathDropPercent plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "[DDC] Provide more arguments, please.");
            return true;
        }

        if (strings[0].equalsIgnoreCase("info")) {
            String messageToSend = ChatColor.GREEN + "[DDC] Plugin is ";
            if (DeathDropPercent.isEnabled) {
                messageToSend += ChatColor.DARK_GREEN + "enabled";
            } else {
                messageToSend += ChatColor.RED + "disabled";
            }

            messageToSend += ChatColor.GREEN + " right now.";
            messageToSend += "\nCurrent percentage is " + ChatColor.DARK_GREEN + DeathDropPercent.percentToDrop;

            sender.sendMessage(messageToSend);
        }

        if (strings[0].equalsIgnoreCase("enable")) {
            sender.sendMessage(ChatColor.GREEN + "[DDC] Plugin " + ChatColor.DARK_GREEN + "enabled" + ChatColor.GREEN + " successfully!");
            plugin.getConfig().set("isEnabled", true);
            DeathDropPercent.isEnabled = true;
            plugin.saveConfig();
        }

        if (strings[0].equalsIgnoreCase("disable")) {
            sender.sendMessage(ChatColor.RED + "[DDC] Plugin disabled.");
            plugin.getConfig().set("isEnabled", false);
            DeathDropPercent.isEnabled = false;
            plugin.saveConfig();
        }

        if (strings[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            DeathDropPercent.isEnabled = (boolean)plugin.getConfig().get("isEnabled");
            DeathDropPercent.percentToDrop = (double)plugin.getConfig().get("percentToDrop");
            sender.sendMessage(ChatColor.GREEN + "[DDC] Plugin reloaded!\nCurrent percentage of inventory slots to drop on death is " + ChatColor.DARK_GREEN + DeathDropPercent.percentToDrop);
        }

        return true;
    }

    // Chat autocomplete with TAB
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            commands.add("enable");
            commands.add("disable");
            commands.add("info");
            commands.add("reload");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }

        return completions;
    }
}
