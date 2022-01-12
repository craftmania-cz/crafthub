package cz.wake.hub.listener;

import cz.wake.hub.Main;
import cz.wake.hub.commands.SenderPlayer_command;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerListener implements Listener {

    SenderPlayer_command sp = new SenderPlayer_command();
    public static ArrayList<Player> players = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().getY() <= 60) {
            p.setFallDistance(0);
            p.teleport(new Location(Bukkit.getWorld("whub_spawn"), 0.5, 100.0, 0.5, -90, 0));
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
        p.sendMessage("§cNelze psát do chatu na přihlašovacím lobby.");
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

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamageByOthers(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            e.setCancelled(true);
        }
        if (Objects.requireNonNull(e.getTo()).getWorld() == Bukkit.getWorld("world_nether")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        Main.getInstance().getCraftBalancerManager().connectPlayer(event.getPlayer(), "main-lobby");
    }
}
