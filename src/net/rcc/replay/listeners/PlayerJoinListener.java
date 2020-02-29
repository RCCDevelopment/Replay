package net.rcc.replay.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.rcc.replay.SystemMain;
import net.rcc.replay.manager.PlayerManager;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void on(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SystemMain.getInstance().getReplayManager().getPlayerMap().put(player.getUniqueId(), new PlayerManager(player.getUniqueId()));
	}

}
