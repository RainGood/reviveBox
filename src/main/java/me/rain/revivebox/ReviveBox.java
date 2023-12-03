package me.rain.revivebox;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReviveBox extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void OnJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        int howmanyitems = 0;
        for (int i = 0; i <= 36; i++){
            if (player.getInventory().getItem(i) != null) {
                howmanyitems++;
            }
        }

        if (e.getPlayer().getInventory().getContents().length <= 27){

            Block block = player.getLocation().getBlock();
            block.setType(Material.CHEST);

            Chest chest = (Chest) block.getState();
            Inventory inventory_chest = chest.getBlockInventory();
            int inv_num = 0;
            for (int i = 0; i <= howmanyitems; i++){
                if (player.getInventory().getItem(i) == null)
                {
                    player.sendMessage("Empty");
                }else{
                    inventory_chest.setItem(inv_num, player.getInventory().getItem(i));
                    inv_num++;
                }

            }
        }else{
            Block block_left = player.getLocation().getBlock();
            Block block_right = world.getBlockAt(block_left.getX()-1, block_left.getY() ,block_left.getZ());

            block_left.setBlockData(Material.CHEST.createBlockData("[facing=south, type=left]"));
            block_right.setBlockData(Material.CHEST.createBlockData("[facing=south, type=right]"));

            Chest chest = (Chest) block_left.getState();
            Chest chest2 = (Chest) block_right.getState();
            Inventory inventory_chest = chest.getBlockInventory();
            Inventory inventory_chest2 = chest2.getBlockInventory();
            int inv_num = 0;
            for (int i = 0; i <= howmanyitems; i++){
                if (player.getInventory().getItem(i) == null)
                {
                    player.sendMessage("Empty");
                }else{
                    try {
                        if (i<=27){
                            inventory_chest2.setItem(inv_num, player.getInventory().getItem(i));
                            inv_num++;

                        }else {
                            inventory_chest.setItem(inv_num, player.getInventory().getItem(i));
                            inv_num++;
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        inv_num = 0;
                    }
                }

            }
        }
    }
}
