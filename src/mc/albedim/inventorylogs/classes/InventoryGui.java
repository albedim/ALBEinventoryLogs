package mc.albedim.inventorylogs.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

import static mc.albedim.inventorylogs.Main.database;

/*
 *  Created by @albedim (Github: github.com/albedim) on 12/08/22
 *  Last Update -
 */

public class InventoryGui {
    private Player player;

    public InventoryGui(Player player) {
        this.player = player;
    }

    public ItemStack createButton(Material id, short data, int amount, List<String> lore, String display) {

        @SuppressWarnings("deprecation")
        ItemStack item = new ItemStack(id, amount, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }


    public void open(Player player, String id, String date, String time, String page) {
        Inventory inv = Bukkit.createInventory(null, 45, "§8Inventario");
        String[] elements = database.getElements(id);
        String[] amounts = database.getAmounts(id);

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(" §7Giocatore §a» §7" + player.getName());
        lore.add(" §7Data §a» §7" + date);
        lore.add(" §7Ora §a» §7" + time);

        inv.setItem(8, createButton(Material.PLAYER_HEAD, (short) 1, 1, lore, "§a• §7Inventario §a#" + id));
        inv.setItem(7, createButton(Material.PAPER, (short) 1, 1, null, page));

        int x = 9, y = 0;
        for (String element : elements) {
            inv.setItem(x, createButton(Material.getMaterial(element), (short) 1, Integer.parseInt(amounts[y]), null, null));
            x++;
            y++;
        }

        this.player.openInventory(inv);

    }

}
