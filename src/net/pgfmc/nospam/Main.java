package net.pgfmc.nospam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public static Map<UUID, Boolean> LAST_CHAT = new HashMap<UUID, Boolean>();
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void noSpam(AsyncPlayerChatEvent e)
	{
		UUID p = e.getPlayer().getUniqueId();
		
		if (LAST_CHAT.get(p) != null)
		{
			if (!LAST_CHAT.get(p))
			{
				e.getPlayer().sendMessage("Slow down!");
				e.setCancelled(true);
				return;
			}
		}
		
		spam(p);
	}
	
	public void spam(UUID p)
	{
		LAST_CHAT.put(p, Boolean.FALSE);
		// Makes it to where players can only chat every .75 seconds
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run()
			{
				LAST_CHAT.put(p, Boolean.TRUE);
			}
			
		}, 15);
		
		
	}
}
