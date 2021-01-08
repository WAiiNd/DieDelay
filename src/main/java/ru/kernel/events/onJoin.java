package ru.kernel.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.kernel.DieDelay;

public class onJoin implements Listener {

    @EventHandler
    public void event(PlayerJoinEvent e) {
        DieDelay main = DieDelay.getPlugin(DieDelay.class);

        Player p = e.getPlayer();

        if(p.getGameMode() == GameMode.SPECTATOR && main.startInfo.get(p.getUniqueId()) != null && main.startInfo.get(p.getUniqueId())) {
            if(!main.timeDis.get(p.getUniqueId()).equals("00:00")) {

                p.setFlySpeed(0);

                new BukkitRunnable() {
                    String[] listTime = main.timeDis.get(p.getUniqueId()).split(":");
                    int min = Integer.parseInt(listTime[0]);
                    int sec = Integer.parseInt(listTime[1]);

                    Location spawn = p.getWorld().getSpawnLocation();
                    @Override
                    public void run() {

                        main.timeDis.put(p.getUniqueId(), main.timeDis.get(p.getUniqueId()));

                        if(sec == 0) {
                            if(min == 0) {
                                p.teleport(spawn);
                                p.setGameMode(GameMode.SURVIVAL);
                                main.startInfo.put(p.getUniqueId(), false);
                                p.setFlySpeed(1);
                                cancel();
                            } else {
                                min--;
                                sec = 59;
                            }
                        } else {
                            sec--;
                        }
                        String subtitle = String.format(ChatColor.translateAlternateColorCodes('&', main.getConfig().get("TimerColor") + "%02d:%02d"), min, sec);
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', main.getConfig().get("YouDie").toString()), subtitle, 1, 20, 1);
                    }
                }.runTaskTimer(main, 0, 20);
            }
        }
    }
}
