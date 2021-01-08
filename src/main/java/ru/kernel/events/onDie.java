package ru.kernel.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.kernel.DieDelay;

public class onDie implements Listener {

    @EventHandler
    public void event(PlayerRespawnEvent e) {

        DieDelay main = DieDelay.getPlugin(DieDelay.class);

        Player p = e.getPlayer();

        Location spawn = p.getWorld().getSpawnLocation();

        p.setGameMode(GameMode.SPECTATOR);
        main.startInfo.put(p.getUniqueId(), true);

        p.setFlySpeed(0);
        // 1 sec = 20tick
        new BukkitRunnable() {
            int total = main.getConfig().getInt("TotalTime");

            int minutes = Math.round(total / 60);

            int seconds = total % 60;

            @Override
            public void run() {

                main.timeDis.put(p.getUniqueId(), String.format("%02d:%02d", minutes, seconds));

                if(seconds == 0) {
                    if(minutes == 0) {
                        p.teleport(spawn);
                        p.setGameMode(GameMode.SURVIVAL);
                        main.startInfo.put(p.getUniqueId(), false);
                        p.setFlySpeed(1);
                        cancel();
                    } else {
                        minutes--;
                        seconds = 59;
                    }
                } else {
                    seconds--;
                }
                String subtitle = String.format(ChatColor.translateAlternateColorCodes('&', main.getConfig().get("TimerColor") + "%02d:%02d"), minutes, seconds);
                p.sendTitle(ChatColor.translateAlternateColorCodes('&', main.getConfig().get("YouDie").toString()), subtitle, 1, 20, 1);
            }
        }.runTaskTimer(main, 0, 20);
    }
}
