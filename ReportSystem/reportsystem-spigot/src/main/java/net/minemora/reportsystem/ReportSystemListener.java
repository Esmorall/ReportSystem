package net.minemora.reportsystem;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minemora.reportsystem.bungee.BungeeListener;

public class ReportSystemListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		for(String pn : ReportSystem.getSpectators()) {
			if(Bukkit.getPlayer(pn) != null) {
				event.getPlayer().hidePlayer(Bukkit.getPlayer(pn));
			}
		}
		if(BungeeListener.getInstance().getQueue().containsKey(event.getPlayer().getName())) {
			event.setJoinMessage("");
		}
		if(ReportSystemAPI.isProcessQueueOnJoin()) {
			ReportSystemAPI.processQueue(event.getPlayer());
		}
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		ReportSystem.getSpectators().remove(event.getPlayer().getName());
	}
}