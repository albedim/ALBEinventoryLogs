package me.albedim.inventorylogs.listener;

import me.albedim.inventorylogs.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import java.text.SimpleDateFormat;
import java.util.Date;

import static me.albedim.inventorylogs.Main.database;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 13/08/22
 * Created at: 08:14
 * Version: 1.0.0
 * Description: This is the class for the join event
 */

public class JoinEvent implements Listener
{

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                String elements = "", amounts = "";

                for (ItemStack is : e.getPlayer().getInventory()) {
                    if (is != null) {
                        elements += is.getType() + ",";
                        amounts += is.getAmount() + ",";
                    }
                }

                Date date = new Date();
                Date time = new Date();
                SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");

                database.addLog(e.getPlayer().getName(), elements, amounts, date_format.format(date), time_format.format(time));
            }
        }, Integer.parseInt(Main.getInstance().getConfig().getString("configuration.create-logs-every")), Integer.parseInt(Main.getInstance().getConfig().getString("configuration.create-logs-every")));
    }

}
