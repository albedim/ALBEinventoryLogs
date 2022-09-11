package me.albedim.inventorylogs.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

import static me.albedim.inventorylogs.Main.database;

/*
 *  Created by @albedim (Github: github.com/albedim) on 12/08/22
 *  Last Update -
 */

public class InventoriesGui 
{
    private Player player;
    public InventoriesGui(Player player) 
    {
        this.player = player;
    }

    public ItemStack createButton(Material id, short data, int amount, List<String> lore, String display) 
    {

        @SuppressWarnings("deprecation")
        ItemStack item = new ItemStack(id, amount, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

    public void open(Player player, Player staffer, int page) 
    {

        Inventory inv = Bukkit.createInventory(null, 45, "§8Inventario");
        ArrayList<String> inventories = database.getInventories(player.getName());
        int y = this.getIndexofLore(page), z = 9;

        inv.setItem(7, createButton(Material.PAPER, (short) 1, 1, null, " §7Pagina §a» §7" + page));

        for (int x = this.getIndexofPage(page); z < 45 && x < database.getInventoriesNumber(player.getName()); x++) {
            inv.setItem(z, createButton(Material.CHEST, (short) 1, 1, this.getLore(inventories, y), " §a• §7Dettagli"));
            y += 3;
            z++;
        }
        staffer.openInventory(inv);
    }

    private int getIndexofPage(int page) 
    {
        return 36 * (page - 1);
    }

    private int getIndexofLore(int page) 
    {
        return 3 * this.getIndexofPage(page);
    }

    private ArrayList<String> getLore(ArrayList<String> inventories, int y) 
    {
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(" §7ID §a» §7" + inventories.get(y));
        lore.add(" §7Data §a» §7" + inventories.get(y + 1));
        lore.add(" §7Ora §a» §7" + inventories.get(y + 2));

        return lore;
    }

}
