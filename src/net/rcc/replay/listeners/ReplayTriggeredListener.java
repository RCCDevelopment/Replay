package net.rcc.replay.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.rcc.replay.SystemMain;
import net.rcc.replay.events.ReplayTriggeredEvent;
import net.rcc.replay.manager.PlayerManager;

public class ReplayTriggeredListener implements Listener {

	@EventHandler
	public void on(ReplayTriggeredEvent event) {
		Player player = event.getPlayer();
		
		if(SystemMain.getInstance().getReplayManager().containsPlayerMap(player)) {
			
			Integer playTime = SystemMain.getInstance().getReplayManager().getPlayTime();
			
			PlayerManager playerManager = SystemMain.getInstance().getReplayManager().getPlayerMap().get(player);
			
			switch (event.getTyp()) {
			case MOVE:
				playerManager.getLocationMap().put(playTime, player.getLocation());
				break;
			case BREAK_BLOCK:
				playerManager.getBreakedBlocks().put(playTime, event.getBreakedBlock());
				break;
			case PLACED_BLOCK:
				playerManager.getPlacedBlocks().put(playTime, event.getPlacedBlock());
				break;
			case DAMAGE_OTHER:
				playerManager.getDamageOther().put(playTime, event.getTargetEntity());
				break;
			case DAMAGE_SELF:
				playerManager.getDamageSelf().put(playTime, event.getDamage());
				break;
			case DROP_ITEM:
				playerManager.getDropedItems().put(playTime, event.getDropedItem());
				break;
			case PICKUP_ITEM:
				playerManager.getPickupItems().put(playTime, event.getPickupedItem());
				break;
			case THROW:
				playerManager.getThrownPotions().put(playTime, event.getThrownPotion());
				break;
			case INTERACT:
				playerManager.getInteract().put(playTime, event.getAction());
				break;
			default:
				break;
			}
			
		}
	}

}
