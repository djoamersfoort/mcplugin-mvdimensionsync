package nl.sverben;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class optimize extends JavaPlugin implements Listener {
    ArrayList<ArrayList<String>> groups = new ArrayList<>();

    @Override
    public void onEnable() {
        Server srv = getServer();

        Logger logger = getLogger();
        logger.info("Multiverse smps /worlds menu fixed");
        getServer().getPluginManager().registerEvents(this, this);
        createGroups();
        Objects.requireNonNull(getCommand("joinsmp")).setExecutor(new joinCommand(srv, groups));
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
    public void PlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String worldname = Objects.requireNonNull(player.getLocation().getWorld()).getName();
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
