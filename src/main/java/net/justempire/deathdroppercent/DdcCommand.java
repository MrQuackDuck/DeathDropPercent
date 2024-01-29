package net.justempire.deathdroppercent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DdcCommand implements CommandExecutor, TabCompleter {
    private final DeathDropPercent plugin;

    private final String GREEN = "&#8EE53F";
    private final String YELLOW = "&#F4CA16";
    private final String RED = "&#DB1731";

    public DdcCommand(DeathDropPercent plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        String messageToSend;

        if (strings.length == 0) {
            messageToSend = GREEN + "[DDC] Provide more arguments, please.";
            sender.sendMessage(MessageColorizer.colorize(messageToSend));
            return true;
        }
        else if (strings[0].equalsIgnoreCase("info")) {
            messageToSend = GREEN + "[DDC] info:\n| Plugin is ";
            if (DeathDropPercent.isEnabled) messageToSend += YELLOW + "enabled";
            else messageToSend += RED + "disabled";

            messageToSend += GREEN + " right now.";
            messageToSend += "\n| Current percentage is " + YELLOW + DeathDropPercent.percentToDrop;
        }
        else if (strings[0].equalsIgnoreCase("enable")) {
            messageToSend = GREEN + "[DDC] Plugin " + YELLOW + "enabled" + GREEN + " successfully!";
            plugin.getConfig().set("isEnabled", true);
            DeathDropPercent.isEnabled = true;
            plugin.saveConfig();
        }
        else if (strings[0].equalsIgnoreCase("disable")) {
            messageToSend = RED + "[DDC] Plugin disabled.";
            plugin.getConfig().set("isEnabled", false);
            DeathDropPercent.isEnabled = false;
            plugin.saveConfig();
        }
        else if (strings[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            DeathDropPercent.isEnabled = (boolean)plugin.getConfig().get("isEnabled");
            DeathDropPercent.percentToDrop = (double)plugin.getConfig().get("percentToDrop");
            messageToSend = GREEN + "[DDC] Plugin reloaded!\nCurrent percentage is " + YELLOW + DeathDropPercent.percentToDrop;
        }
        else {
            messageToSend = RED + "[DDC] That command doesn't exist!";
        }

        sender.sendMessage(MessageColorizer.colorize(messageToSend));

        return true;
    }

    // Chat autocomplete with TAB
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completions = new ArrayList<>();

        if (args.length != 1) return completions;

        commands.add("enable");
        commands.add("disable");
        commands.add("info");
        commands.add("reload");
        StringUtil.copyPartialMatches(args[0], commands, completions);

        return completions;
    }
}
