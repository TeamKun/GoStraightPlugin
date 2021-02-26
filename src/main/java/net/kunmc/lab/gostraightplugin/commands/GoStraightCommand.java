package net.kunmc.lab.gostraightplugin.commands;

import net.kunmc.lab.gostraightplugin.GoStraightPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GoStraightCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("gostraightplugin.gostraight")) {
            sender.sendMessage(ChatColor.RED + "権限がないお^^");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が足りましぇ～ん");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/gostraight <start|end>");
            return true;
        }

        switch (args[0]) {
            case "start":
                return onStart(sender, Arrays.copyOfRange(args, 1, args.length));
            case "end":
                if (!GoStraightPlugin.enable) {
                    sender.sendMessage(ChatColor.RED + "既に終わってるよ～ん");
                    sender.sendMessage(ChatColor.GOLD + "始めるには次のコマンドを入力してね～！");
                    sender.sendMessage(ChatColor.GREEN + "使用方法：/gostraight start <x|y|z>");
                    return true;
                }
                GoStraightPlugin.enable = false;
                break;
        }

        return true;
    }

    private boolean onStart(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が足りましぇ～ん");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/gostraight start <x|y|z>");
            return true;
        }

        if (GoStraightPlugin.enable) {
            sender.sendMessage(ChatColor.RED + "既に始まってるよ～ん");
            sender.sendMessage(ChatColor.GOLD + "終わらせるには次のコマンドを入力してね～！");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/gostraight end");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "x":
                GoStraightPlugin.axis = GoStraightPlugin.Axis.X;
                break;
            case "y":
                GoStraightPlugin.axis = GoStraightPlugin.Axis.Y;
                break;
            case "z":
                GoStraightPlugin.axis = GoStraightPlugin.Axis.Z;
                break;
            default:
                sender.sendMessage(ChatColor.RED + "引数が間違ってるよ～ん");
                sender.sendMessage(ChatColor.GREEN + "使用方法：/gostraight start <x|y|z>");
                return true;
        }

        GoStraightPlugin.enable = true;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendTitle("直進プラグイン",
                    ChatColor.RED + GoStraightPlugin.axis.getDisplayName() + "軸から出たら死んじゃうよ～ん",
                    10, 80, 10);
        });

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result;

        switch (args.length) {
            case 1:
                result = Arrays.asList("start", "end");
                break;
            case 2:
                switch (args[1].toLowerCase()) {
                    case "start":
                        result = Arrays.asList("x", "y", "z");
                        break;
                    default:
                        return Collections.emptyList();
                }
                break;
            default:
                return Collections.emptyList();
        }

        String last = args[args.length - 1].toLowerCase();
        return result.stream()
                .filter(opt -> opt.startsWith(last))
                .collect(Collectors.toList());
    }
}
