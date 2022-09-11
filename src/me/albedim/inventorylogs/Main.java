package me.albedim.inventorylogs;

import me.albedim.inventorylogs.classes.Database;
import me.albedim.inventorylogs.executor.Commands;
import me.albedim.inventorylogs.listener.InventoryClick;
import me.albedim.inventorylogs.listener.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

/*
 *  Created by @albedim (Github: github.com/albedim) on 11/08/22
 *  Last Update -
 */

public class Main extends JavaPlugin 
{
    public static Main plugin;
    public static Database database;

    public void onEnable() 
    {
        plugin = this;
        getCommand("invceck").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        this.database = new Database();
        saveDefaultConfig();
        try {
            database.connect();
            System.out.println("[ALBEInventoryLogs] Plugin succefully activated and connected to the database '" + getConfig().getString("database.db-name") + "'.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() 
    {
        database.disconnect();
    }

    public static Main getInstance() 
    {
        return plugin;
    }

}
