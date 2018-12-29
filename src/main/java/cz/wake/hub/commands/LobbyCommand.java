package cz.wake.hub.commands;

import cz.wake.hub.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if ((command.getName().equalsIgnoreCase("skip"))) {
                Main.getInstance().getCraftBalancerManager().connectPlayer(p, "main-lobby");
            }
        }
        return false;
    }
}
