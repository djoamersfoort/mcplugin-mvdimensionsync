package nl.sverben;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class joinCommand implements CommandExecutor {
    Server srv;
    ArrayList<ArrayList<String>> groups;

    public joinCommand(Server srv, ArrayList<ArrayList<String>> groups) {
        this.srv = srv;
        this.groups = groups;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("joinSMP")) {
            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Please provide a smp name!");
                return true;
            }

            String worldname = args[0];
            for (ArrayList<String> group : groups) {
                for (String world : group) {
                    if (world.equalsIgnoreCase(worldname)) {
                        String index = Integer.toString(groups.indexOf(group));
                        if (player.hasMetadata("WorldGroupLocv3-" + index)) {
                            player.teleport(Objects.requireNonNull(srv.getWorld(player.getMetadata("WorldGroupLocv3-" + index).get(0).asString())).getSpawnLocation());
                        } else {
                            player.teleport(Objects.requireNonNull(srv.getWorld(worldname)).getSpawnLocation());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
