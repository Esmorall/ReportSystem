package net.minemora.reportsystem;

import com.google.gson.Gson;
import com.imaginarycode.minecraft.redisbungee.RedisBungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.minemora.reportsystem.command.CommandManager;
import net.minemora.reportsystem.config.ConfigMain;
import net.minemora.reportsystem.database.Database;
import net.minemora.reportsystem.network.PluginMessageHandler;
import net.minemora.reportsystem.network.PubSubMessageHandler;

public class ReportSystem extends Plugin {
	
	private static ReportSystem plugin;
	
	private static final Gson gson;
	
	static {
	    gson = new Gson();
	}
	
	@Override
    public void onEnable() {
		plugin = this;
		CommandManager.registerCommands(this);
		getProxy().getPluginManager().registerListener(this, new PluginMessageHandler());
		getProxy().getPluginManager().registerListener(this, new ReportSystemListener());
		getProxy().registerChannel("legacy:reportsystem");
		RedisBungee.getApi().registerPubSubChannels("ReportSystem");
		getProxy().getPluginManager().registerListener(this, new PubSubMessageHandler());
		ConfigMain.getInstance().setup();
		Database.getDatabase().setup();
		ReportBanManager.setup();
	}
	
	public static ReportSystem getPlugin() {
		return plugin;
	}
	
	public static Gson getGson() {
		return gson;
	}

}