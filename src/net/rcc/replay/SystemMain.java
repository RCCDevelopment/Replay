package net.rcc.replay;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.ParseException;

import net.rcc.replay.manager.PlayerManager;
import net.rcc.replay.manager.ReplayManager;

public class SystemMain extends JavaPlugin implements Listener{

	private static SystemMain instance;
	private ReplayManager replayManager;
	
	@Override
	public void onEnable() {
		setInstance(this);
		this.setReplayManager(new ReplayManager());
		Bukkit.getPluginManager().registerEvents(new Listener() {
			
			@EventHandler
			public void on(PlayerJoinEvent event) {
				Player player = event.getPlayer();
				PlayerManager playerManager = new PlayerManager(player.getUniqueId());
				replayManager.getPlayerMap().put(player.getUniqueId(), playerManager);
				Bukkit.broadcastMessage("playerManager creating new instance....");
			}
			
			@EventHandler (priority = EventPriority.LOWEST)
			 public void onMove(PlayerMoveEvent event) {
				 Player player = event.getPlayer();
				 Bukkit.broadcastMessage("1");
				 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
					 Bukkit.broadcastMessage("2");
					 if(!event.isCancelled()) {
						 Bukkit.broadcastMessage("3");
						 Bukkit.broadcastMessage(player.getName() + replayManager.getPlayTime() + event.getTo());
						 replayManager.getPlayerMap().get(player.getUniqueId()).getLocationMap().put(replayManager.getPlayTime(), event.getTo());
					 }
				 }
			 }
			 
			 @EventHandler (priority = EventPriority.LOWEST)
			 public void onBreakBlock(BlockBreakEvent event) {
				 Player player = event.getPlayer();
				 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
					 if(!event.isCancelled()) {
						 replayManager.getPlayerMap().get(player.getUniqueId()).getBreakedBlocks().put(replayManager.getPlayTime(), event.getBlock());
					 }
				 }
			 }
			 
			 @EventHandler (priority = EventPriority.LOWEST)
			 public void onPlaceBlock(BlockPlaceEvent event) {
				 Player player = event.getPlayer();
				 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
					 if(!event.isCancelled()) {
						 replayManager.getPlayerMap().get(player.getUniqueId()).getPlacedBlocks().put(replayManager.getPlayTime(), event.getBlockPlaced());
					 }
				 }
			 }

			 
			 @EventHandler (priority = EventPriority.LOWEST)
			 public void onDamageSelf(EntityDamageEvent event) {
				 Entity entity = event.getEntity();
				 
				 if(entity instanceof Player) {
					 Player player = (Player) event.getEntity();
					 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
						 if(!event.isCancelled()) {
							 replayManager.getPlayerMap().get(player.getUniqueId()).getDamageSelf().put(replayManager.getPlayTime(), player.getHealth());
						 }
					 }
				 }
				 
			 }
			 
			 @EventHandler (priority = EventPriority.LOWEST)
			 public void onDamageOther(EntityDamageEvent event) {
				 Entity entity = event.getEntity();
				 
				 if(entity instanceof Player) {
					 Player player = (Player) event.getEntity();
					 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
						 if(!event.isCancelled()) {
							 replayManager.getPlayerMap().get(player.getUniqueId()).getDamageOther().put(replayManager.getPlayTime(), event.getEntity());
						 }
					 }
				 }
			 }
			 
			 @EventHandler
			 public void onDamage(PlayerInteractAtEntityEvent event) {
				 Entity entity = event.getRightClicked();
				 
				 if(entity instanceof Player) {
					 Player player = (Player) event.getPlayer();
					 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
						 if(!event.isCancelled()) {
							 replayManager.getPlayerMap().get(player.getUniqueId()).getInteract().put(replayManager.getPlayTime(), Action.RIGHT_CLICK_BLOCK);
						 }
					 }
				 }
			 }
			 
			 @EventHandler
			 public void onInteractByBlock(PlayerInteractEvent event) {
				 Player player = event.getPlayer();
				 
				 if(event.getClickedBlock() != null) {
					 
					 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
					 	 if(!event.isCancelled()) {
					 		 replayManager.getPlayerMap().get(player.getUniqueId()).getInteract().put(replayManager.getPlayTime(), event.getAction());
					 	 }
					 }
					 
				 }
				 
			 }
			 
			 @EventHandler
			 public void onDropItem(PlayerDropItemEvent event) {
				 Player player = event.getPlayer();
				 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
				 	 if(!event.isCancelled()) {
				 		 replayManager.getPlayerMap().get(player.getUniqueId()).getDropedItems().put(replayManager.getPlayTime(), event.getItemDrop().getItemStack());
				 	 }
				 }
			 }
			 
			 @EventHandler
			 public void onPickupItem(PlayerPickupItemEvent event) {
				 Player player = event.getPlayer();
				 
				 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
				 	 if(!event.isCancelled()) {
				 		 replayManager.getPlayerMap().get(player.getUniqueId()).getPickupItems().put(replayManager.getPlayTime(), event.getItem().getItemStack());
				 	 }
				 }
				 
			 }
			 
			 @EventHandler (priority = EventPriority.HIGHEST)
			 public void saveAllPlayers(AsyncPlayerChatEvent event) throws ParseException, IOException {
			 	 Player player = event.getPlayer();
			 	 if(replayManager.getPlayerMap().containsKey(player.getUniqueId())) {
//			 		 if(event.getMessage().contains("loch")) {
//			 			 ///////player.sendMessage("loading from .json");
//			 			 
//			 			JSONObject jsonObject = (JSONObject) new JSONParser().parse(new String(Files.readAllBytes(replayManager.getFile().toPath())));
//
//			            JSONObject object = (JSONObject) jsonObject.get("records");
//			            
//			            //player.sendMessage(object.toJSONString());
//			            
//			            //System.err.println(new HashMap<>(net.sf.json.JSONObject.fromObject(object)));
//			            
//			 			//HashMap<?, ?> map = ReplayReader.toHashMap(object.toString());
//			 			 
//			            ReplayReader.convertObjectToMap(jsonObject);
//			 			
////			            /(///Map<?, ?> map = ReplayReader.toMap(object);
////						///player.sendMessage(map.toString());
//			 			 
//							//json = new JsonParser().parse(new String(Files.readAllBytes(replayManager.getFile().toPath()))).getAsJsonObject();
////							JsonObject records = json.get("records").getAsJsonObject();
//							
//							//ReplayReader.toMap(object);
//														
//						
//			 			 
//			 			 
//			 			 /*JsonArray array = records.get("uuid").getAsJsonArray();
//			 			 
//			 			String locationmap=  array.get(0).getAsJsonObject().get("locationmap").getAsString();*/
//			 			 
//			 			 return;
//			 		 }
			 		 replayManager.saveAllPlayers();
			 	 	 player.sendMessage("§7Die Rohdaten wurden gespeichert.");
			 	 }
		     }
			 
			 
		}, this);
	}
	
	public static SystemMain getInstance() {
		return instance;
	}

	public static void setInstance(SystemMain instance) {
		SystemMain.instance = instance;
	}

	public ReplayManager getReplayManager() {
		return replayManager;
	}

	public void setReplayManager(ReplayManager replayManager) {
		this.replayManager = replayManager;
	}
	
}
