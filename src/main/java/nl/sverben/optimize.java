package nl.sverben;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public class optimize extends JavaPlugin implements Listener {
    ArrayList<ArrayList<String>> groups = new ArrayList<>();

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("Multiverse smps /worlds menu fixed");
        getServer().getPluginManager().registerEvents(this, this);
        createGroups();
    }

    private void createGroups() {
        ArrayList<String> list = new ArrayList<>();
        list.add("world");
        list.add("world_nether");
        list.add("world_the_end");
        groups.add(list);

        list = new ArrayList<>();
        list.add("world2");
        list.add("world2_nether");
        list.add("world2_the_end");
        groups.add(list);

        list = new ArrayList<>();
        list.add("world3");
        list.add("world3_nether");
        list.add("world3_the_end");
        groups.add(list);
    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("joinSMP")) {
            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Wut??");
                return true;
            }

            String worldname = args[0];
            for (ArrayList<String> group : groups) {
                for (String world : group) {
                    if (world.equalsIgnoreCase(worldname)) {
                        String index = Integer.toString(groups.indexOf(group));
                        if (player.hasMetadata("WorldGroupLocv3-" + index)) {
                            player.teleport(getServer().getWorld(player.getMetadata("WorldGroupLocv3-" + index).get(0).asString()).getSpawnLocation());
                        } else {
                            player.teleport(getServer().getWorld(worldname).getSpawnLocation());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void PlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String worldname = player.getLocation().getWorld().getName();
        for (ArrayList<String> group : groups) {
            for (String world : group) {
                if (world.equalsIgnoreCase(worldname)) {
                    String index = Integer.toString(groups.indexOf(group));
                    player.setMetadata("WorldGroupLocv3-" + index, new FixedMetadataValue(this, worldname));
                }
            }
        }
    }
}
