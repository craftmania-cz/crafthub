package cz.wake.hub;

import cz.wake.hub.commands.LobbyCommand;
import cz.wake.hub.commands.SenderPlayer_command;
import cz.wake.hub.listener.PlayerListener;
import cz.wake.hub.utils.CraftBalancerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Main extends JavaPlugin implements PluginMessageListener {

    private static Main instance;
    private CraftBalancerManager craftBalancerManager;

    @Override
    public void onEnable(){
        instance = this;

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        craftBalancerManager = new CraftBalancerManager(this);

        getCommand("sendplayer").setExecutor(new SenderPlayer_command());
        getCommand("skip").setExecutor(new LobbyCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        for (World w : Bukkit.getWorlds()) {
            w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            w.setGameRule(GameRule.DO_FIRE_TICK, false);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            for (Entity e : w.getEntities()){
                e.remove();
            }
        }
    }

    @Override
    public void onDisable(){
        instance = null;
    }

    public static Main getInstance(){
        return instance;
    }

    public CraftBalancerManager getCraftBalancerManager() {
        return craftBalancerManager;
    }


    public void sendToServer(Player player, String target) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

    }

}
