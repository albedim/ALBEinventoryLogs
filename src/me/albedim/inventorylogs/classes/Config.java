package me.albedim.inventorylogs.classes;

import me.albedim.inventorylogs.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 *  Created by @albedim (Github: github.com/albedim) on 01/09/22
 *  Last Update -
 */

public class Config {

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

}
