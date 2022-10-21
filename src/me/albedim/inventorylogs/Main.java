package me.albedim.inventorylogs;

import me.albedim.inventorylogs.classes.Config;
import me.albedim.inventorylogs.classes.Database;
import me.albedim.inventorylogs.executor.Commands;
import me.albedim.inventorylogs.listener.InventoryClick;
import me.albedim.inventorylogs.listener.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/08/22
 * Created at: 08:54
 * Version: 1.0.0
 * Description: This is the class for the main
 */

public class Main extends JavaPlugin
{
    public static Main plugin;
    public static Config config;
    public static Database database;

    public void onEnable()
    {
        plugin = this;
        this.config = new Config();
        getCommand("invceck").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        saveDefaultConfig();
        try {
            config.connect();
            this.database = new Database();
            System.out.println("[ALBEInventoryLogs] Plugin succefully activated and connected to the database '" + getConfig().getString("database.db-name") + "'.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() { config.disconnect(); }

    public static Main getInstance() { return plugin; }

}
