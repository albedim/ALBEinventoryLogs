package me.albedim.inventorylogs.classes;

import me.albedim.inventorylogs.Main;
import java.sql.*;
import java.util.ArrayList;

/*
 *  Created by @albedim (Github: github.com/albedim) on 11/08/22
 *  Last Update -
 */

public class Database {
    private Connection connection;
    private String host = Main.getInstance().getConfig().getString("database.db-host");
    private String port = Main.getInstance().getConfig().getString("database.db-port");
    private String database = Main.getInstance().getConfig().getString("database.db-name");
    private String username = Main.getInstance().getConfig().getString("database.db-username");
    private String password = Main.getInstance().getConfig().getString("database.db-password");

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
            this.createInventoriesTable();
        }
    }

    public void disconnect() {
        if (!isConnected()) {
            return;
        }
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void createInventoriesTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `inventories` (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,`author` VARCHAR(255) NOT NULL, `date` VARCHAR(255) NOT NULL, `time` VARCHAR(255) NOT NULL,`elements` VARCHAR(1024) NOT NULL, `amounts` VARCHAR(528) NOT NULL);";
        Statement st = this.connection.createStatement();
        st.execute(sql);
    }

    public void addLog(String player, String elements, String amounts, String date, String time) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO inventories VALUES(?,?,?,?,?,?)");
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
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM inventories WHERE author = ?");
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
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM inventories WHERE author = ?");
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
            PreparedStatement stmt = this.connection.prepareStatement("DELETE FROM inventories WHERE author = ?");
            stmt.setString(1, player);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteAllLogs() {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("DELETE FROM inventories");
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInventoriesNumber(String player) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM inventories WHERE author = ?");
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
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM inventories WHERE id = ?");
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
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM inventories ORDER BY id DESC");
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
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM inventories WHERE id = ?");
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
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM inventories WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getString("amounts").split(",");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
