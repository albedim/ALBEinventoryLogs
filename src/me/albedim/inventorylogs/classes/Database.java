package me.albedim.inventorylogs.classes;

import java.sql.*;
import java.util.ArrayList;

import static me.albedim.inventorylogs.Main.config;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 01/09/22
 * Created at: 11:14
 * Version: 1.0.0
 * Description: This is the class for the database
 */

public class Database
{

    public void addLog(String player, String elements, String amounts, String date, String time)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("INSERT INTO inventories VALUES(?,?,?,?,?,?)");
            stmt.setInt(1, 0);
            stmt.setString(2, player);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, elements);
            stmt.setString(6, amounts);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getInventories(String player)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> inventories = new ArrayList<String>();

            while (rs.next()) {
                inventories.add(rs.getString("id"));
                inventories.add(rs.getString("date"));
                inventories.add(rs.getString("time"));
            }

            return inventories;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean userExists(String player)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLogs(String player)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("DELETE FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteAllLogs()
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("DELETE FROM inventories");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInventoriesNumber(String player)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT count(*) AS total FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getInt("title");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String[] getElements(String id)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getString("elements").split(",");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalInventoriesNumber()
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT count(*) AS total FROM inventories ORDER BY id DESC");
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getAuthor(String id)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getString("author");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getAmounts(String id)
    {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getString("amounts").split(",");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
