package me.albedim.inventorylogs.classes;

import java.sql.*;
import java.util.ArrayList;

import static me.albedim.inventorylogs.Main.config;

/*
 *  Created by @albedim (Github: github.com/albedim) on 11/08/22
 *  Last Update 01/09/22
 */

public class Database {

    public void addLog(String player, String elements, String amounts, String date, String time) {
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

    public ArrayList<String> getInventories(String player) {
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

    public boolean userExists(String player) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteLogs(String player) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("DELETE FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteAllLogs() {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("DELETE FROM inventories");
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInventoriesNumber(String player) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            ResultSet rs = stmt.executeQuery();
            int counter = 0;

            while (rs.next()) counter++;

            return counter;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public String[] getElements(String id) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getString("elements").split(",");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getTotalInventoriesNumber() {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories ORDER BY id DESC");
            ResultSet rs = stmt.executeQuery();
            int counter = 0;

            while (rs.next()) counter++;

            return counter;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public String getAuthor(String id) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getString("author");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[] getAmounts(String id) {
        try {
            PreparedStatement stmt = config.getConnection().prepareStatement("SELECT * FROM inventories WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getString("amounts").split(",");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
