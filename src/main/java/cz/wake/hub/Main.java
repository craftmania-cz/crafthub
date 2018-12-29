package cz.wake.hub;

import cz.wake.hub.commands.LobbyCommand;
import cz.wake.hub.commands.SenderPlayer_command;
import cz.wake.hub.listener.PlayerListener;
import cz.wake.hub.utils.CraftBalancerManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Main extends JavaPlugin {

    private static Main instance;
    private CraftBalancerManager craftBalancerManager;

    @Override
    public void onEnable(){
        instance = this;

        craftBalancerManager = new CraftBalancerManager(this);

        getCommand("sendplayer").setExecutor(new SenderPlayer_command());
        getCommand("skip").setExecutor(new LobbyCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        for (World w : Bukkit.getWorlds()) {
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
}
