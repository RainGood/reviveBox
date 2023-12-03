package me.rain.revivebox;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ReviveBox extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void OnPlayerDead(PlayerDeathEvent e){
        Player player = e.getEntity();
        World world = player.getWorld();

        int howmanyitems = 0;
        for (int i = 0; i <= 41; i++){
            if (player.getInventory().getItem(i) != null) {
                howmanyitems++;
            }
        }
        Block block_left = player.getLocation().getBlock();
        Block block_right = world.getBlockAt(block_left.getX()-1, block_left.getY() ,block_left.getZ());
        Block sign_block = world.getBlockAt(block_left.getX(),block_left.getY(),block_left.getZ()+1);
        sign_block.setType(Material.SPRUCE_SIGN);
        Sign sign = (Sign) sign_block.getState();
        sign.getSide(Side.FRONT).setLine(0, "There is a dead body of " + player.getName());
        sign.update();

        block_left.setBlockData(Material.CHEST.createBlockData("[facing=south, type=left]"));
        block_right.setBlockData(Material.CHEST.createBlockData("[facing=south, type=right]"));

        Chest chest = (Chest) block_left.getState();
        Chest chest2 = (Chest) block_right.getState();
        Inventory inventory_chest = chest.getBlockInventory();
        Inventory inventory_chest2 = chest2.getBlockInventory();
        int inv_num = 0;
        for (int i = 0; i <= howmanyitems; i++){
            if (player.getInventory().getItem(i) != null){
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
