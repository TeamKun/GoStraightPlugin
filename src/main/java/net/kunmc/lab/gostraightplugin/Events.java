package net.kunmc.lab.gostraightplugin;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Events implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!GoStraightPlugin.enable) {
            return;
        }

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE ||
                event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        Location location = event.getPlayer().getBedSpawnLocation();

        if (location == null) {
            System.err.println("[ERROR] GoStraightPlugin: event.getPlayer().getBedSpawnLocation() is null.");
            return;
        }

        switch (GoStraightPlugin.axis) {
            case X:
                if (Math.floor(event.getTo().getX()) != Math.floor(location.getX())) {
                    event.getPlayer().setHealth(0.0);
                }
                break;
            case Y:
                if (Math.floor(event.getTo().getY()) != Math.floor(location.getY())) {
                    event.getPlayer().setHealth(0.0);
                }
                break;
            case Z:
                if (Math.floor(event.getTo().getZ()) != Math.floor(location.getZ())) {
                    event.getPlayer().setHealth(0.0);
                }
                break;
        }
    }

}
