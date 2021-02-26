package net.kunmc.lab.gostraightplugin;

import net.kunmc.lab.gostraightplugin.commands.GoStraightCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class GoStraightPlugin extends JavaPlugin {

    public static Axis axis;
    public static boolean enable;

    @Override
    public void onEnable() {
        enable = false;

        GoStraightCommand command = new GoStraightCommand();
        getServer().getPluginCommand("gostraight").setExecutor(command);
        getServer().getPluginCommand("gostraight").setTabCompleter(command);
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {
    }

    public enum Axis {
        X("X"),
        Y("Y"),
        Z("Z");

        private final String displayName;

        Axis(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}
