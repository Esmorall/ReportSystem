package net.minemora.reportsystem.bungee;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.minemora.reportsystem.ReportSystem;

public final class BungeeHandler {
	
	private static Plugin plugin;
	
	private BungeeHandler() {}
	
	public static void setup(Plugin pl) {
		plugin = pl;
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "legacy:reportsystem");
	}
	
	public static void sendGoTo(Player sender, String playerName, boolean online) {
		if(online) {
			sendMessage(sender, "GoTo", playerName + ":online");
		}
		else {
			sendMessage(sender, "GoTo", playerName);
		}
	}
	
	public static void sendGoTo(String playerName, boolean online) {
		if(online) {
			sendMessage("GoTo", playerName + ":online");
		}
		else {
			sendMessage("GoTo", playerName);
		}
	}
	
	public static void sendMessage(String subChannel, String message) {
		Player player = getPlayer();
		if(player == null) {
			System.out.println("trying to send pmc message but random player is null");
			return;
		}
		sendMessage(player, subChannel, message);
    }
	
	public static void sendMessage(Player player, String subChannel, String message) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(subChannel);
		out.writeUTF(message);
		player.sendPluginMessage(ReportSystem.getPlugin(), "legacy:reportsystem", out.toByteArray());
    }
	
	private static Player getPlayer() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			return player;
		}
		return null;
	}
}