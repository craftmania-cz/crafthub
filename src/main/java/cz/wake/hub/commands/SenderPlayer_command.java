package cz.wake.hub.commands;

import cz.wake.hub.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class SenderPlayer_command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender Sender, Command Command, String String, String[] ArrayOfString) {
        if ((Command.getName().equalsIgnoreCase("sendplayer"))) {
            if (Sender.hasPermission("crafthub.sendplayer")) {
                if (ArrayOfString.length == 0) {
                    Sender.sendMessage("§cNapoveda: /sendplayer nick");
                    return false;
                } else {
                    Player p = Bukkit.getPlayer(ArrayOfString[0]); //Hrac musi byt online!
                    Main.getInstance().getCraftBalancerManager().connectPlayer(p, "main-lobby");
                    return true;
                }
            }
        }
        return false;
    }




}
