package me.albedim.inventorylogs.executor;

import me.albedim.inventorylogs.Main;
import me.albedim.inventorylogs.classes.InventoriesGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.text.SimpleDateFormat;
import java.util.Date;
import static me.albedim.inventorylogs.Main.database;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/08/22
 * Created at: 12:48
 * Version: 1.0.0
 * Description: This is the class for the commands
 */

public class Commands implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {

        if (sender instanceof Player) {
            if (cmd.getName().equals("invceck")) {
                if(sender.hasPermission("inventorylogs.admin")){
                    if (args.length > 0) {
                        if (args[0].equals("see")) {
                            if (args.length == 3) {
                                if (database.userExists(args[1])) {
                                    Player target = Bukkit.getPlayerExact(args[1]);
                                    InventoriesGui invsgui = new InventoriesGui(target);
                                    if (!getPageValidity(args[2])) {
                                        sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                                        return false;
                                    }
                                    invsgui.open(target, (Player) sender, Integer.parseInt(args[2]));
                                } else {
                                    sender.sendMessage(Main.getInstance().getConfig().getString("messages.no-logs-found"));
                                }
                            } else {
                                sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                            }

                        } else if (args[0].equals("create")) {
                            if (args.length == 2) {
                                Player target = Bukkit.getPlayerExact(args[1]);
                                String elements = "", amounts = "";

                                for (ItemStack is : target.getInventory()) {
                                    if (is != null) {
                                        elements += is.getType() + ",";
                                        amounts += is.getAmount() + ",";
                                    }
                                }

                                Date date = new Date();
                                Date time = new Date();
                                SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");

                                database.addLog(target.getName(), elements, amounts, date_format.format(date), time_format.format(time));

                                sender.sendMessage(Main.getInstance().getConfig().getString("messages.log-created"));

                            } else {
                                sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                            }

                        } else if (args[0].equals("clear")) {
                            if (args.length == 2) {
                                if (args[1].equals("all")) {
                                    if (database.getTotalInventoriesNumber() > 0) {
                                        String logs_number = String.valueOf(database.getTotalInventoriesNumber());
                                        database.deleteAllLogs();
                                        sender.sendMessage(Main.getInstance().getConfig().getString("messages.logs-cleared").replace("%logs_number%", logs_number));
                                    } else {
                                        sender.sendMessage(Main.getInstance().getConfig().getString("messages.no-logs-found"));
                                    }
                                } else {
                                    if (database.getInventoriesNumber(args[1]) > 0) {
                                        String logs_number = String.valueOf(database.getInventoriesNumber(args[1]));
                                        database.deleteLogs(args[1]);
                                        sender.sendMessage(Main.getInstance().getConfig().getString("messages.logs-cleared").replace("%logs_number%", logs_number));
                                    } else {
                                        sender.sendMessage(Main.getInstance().getConfig().getString("messages.no-logs-found"));
                                    }
                                }
                            } else {
                                sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                            }
                        }
                    } else {
                        sender.sendMessage("§a - - - - - - §7ALBEInventoryLogs §8| §a§lGuida §a- - - - - -");
                        sender.sendMessage("");
                        sender.sendMessage(" §a• §7/invceck create <§aGiocatore§7>");
                        sender.sendMessage(" §a• §7/invceck see <§aGiocatore§7> <§apagina§7>");
                        sender.sendMessage(" §a• §7/invceck clear <§aGiocatore§7/§aAll§7>");
                    }
                }else{
                    sender.sendMessage(Main.getInstance().getConfig().getString("messages.no-permissions"));
                }
            }
        }

        return false;
    }

    private boolean getPageValidity(String page) {
        try {
            Integer.parseInt(page);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
