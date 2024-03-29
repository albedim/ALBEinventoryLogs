package me.albedim.inventorylogs.listener;

import me.albedim.inventorylogs.classes.InventoryGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static me.albedim.inventorylogs.Main.database;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 13/08/22
 * Created at: 18:38
 * Version: 1.0.0
 * Description: This is the class for the inventoryClick event
 */

public class InventoryClick implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals("§8Inventario")) {

            if(e.getCurrentItem() == null) return;
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.CHEST)){
                String id = e.getCurrentItem().getItemMeta().getLore().get(0).substring(12),
                        date = e.getCurrentItem().getItemMeta().getLore().get(1).substring(14),
                        time = e.getCurrentItem().getItemMeta().getLore().get(2).substring(13),
                        page = e.getClickedInventory().getItem(7).getItemMeta().getDisplayName();

                InventoryGui invgui = new InventoryGui(player);
                invgui.open(
                        Bukkit.getPlayerExact(database.getAuthor(e.getCurrentItem().getItemMeta().getLore().get(0).substring(12))),
                        id,
                        date,
                        time,
                        page
                );
            }

        }
    }

}
