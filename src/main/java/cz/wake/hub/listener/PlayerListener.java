package cz.wake.hub.listener;

import cz.wake.hub.Main;
import cz.wake.hub.commands.SenderPlayer_command;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class PlayerListener implements Listener {

    SenderPlayer_command sp = new SenderPlayer_command();
    public static ArrayList<Player> players = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().getZ() <= 418 && (p.getLocation().getX() >= -273 || p.getLocation().getX() <= -245)) {
            if(!players.contains(p)){
                players.add(p);
                Main.getInstance().getCraftBalancerManager().connectPlayer(p, "main-lobby");
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        players.remove(p);
                    }
                }, 100L);
            }
        }

        if (p.getLocation().getY() <= 60) {
            p.setFallDistance(0);
            p.teleport(new Location(Bukkit.getWorld("whub"), -259.5, 86.0, 451.5, -180, 0));
            p.sendMessage("§cNope.... :}");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        for(Player p2 : Bukkit.getOnlinePlayers()){
            p2.hidePlayer(p);
            p.hidePlayer(p2);
        }
        p.setHealth(20.0D);
        p.setFoodLevel(20);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        e.setCancelled(true);
        p.sendMessage("§cNelze na prihlasovacim lobby psat!");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDestroy(BlockBreakEvent e){
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e){
        e.setCancelled(true);
    }
}
