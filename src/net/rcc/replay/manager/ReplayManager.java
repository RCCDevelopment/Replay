package net.rcc.replay.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owlike.genson.convert.DefaultConverters.UUIDConverter;

import net.minecraft.server.v1_8_R3.MobSpawnerAbstract.a;

public class ReplayManager {
	
	private Map<UUID, PlayerManager> playerMap;
	
	private Map<Player, PlayerManager> replayPlayersMap;
	
	private Integer playTime = 0;
	private File file;
	
	public ReplayManager() {
		this.setPlayerMap(new ConcurrentHashMap<>());
		this.setReplayPlayersMap(new ConcurrentHashMap<>());
		this.setFile(new File("replays.json"));
		if(!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.startTimer();
	}
	
	public PlayerManager getPlayer(Player player){
		if(!containsPlayerMap(player))
			return null;
		
		return playerMap.get(player);
	}
	
	private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	public PlayerManager readPlayerManager(String json) {
		return gson.fromJson(json, PlayerManager.class);
	}
	
	public void saveAllPlayers() {
        
		/*
		 * 
		 * SHIT CODE.....
		 * 
		 */
		
//		Bukkit.getOnlinePlayers().forEach(o -> {
//			
//			PlayerManager manager = playerMap.get(o.getUniqueId());
//			String json = gson.toJson(manager);
//			
//			System.out.println(readPlayerManager(json).getLocationMap());;
//			
//		});
		
		/**
		 * 
		 *  old code
		 * 
		 */
		
		JSONObject jsonObject = new JSONObject();
		
		JSONObject recordObject = new JSONObject();
		
		playerMap.forEach((player, manager) -> {
			
			recordObject.put(player, toJSONArray(manager));
			jsonObject.put("records", recordObject);
			
			//String obj = toJSON(manager);
			
			FileWriter writer;
	        try {
	            writer = new FileWriter(this.file);
	            writer.write(jsonObject.toString());
	            writer.flush();
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		});
		
		for (int i = 0; i < 25; i++) {
			System.err.println("/n");
		}
		
		Map<UUID, PlayerManager> playMa = new ConcurrentHashMap<>();
		
		Map<String, Object> out = new HashMap<>(recordObject);
		
		out.forEach((uuid, stringhurevomdienstlol) -> {
			playMa.put(UUID.fromString(uuid), (PlayerManager) stringhurevomdienstlol);
		});
		
		String uuid = "918490b6-8ea2-45bc-bbdd-ffeb346718ba";
		
//		/out.get(uuid);
		System.err.println(playMa.toString());
	}
	
	public void startTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				playTime += 1;
			}
			
		}, 0, 50);
	}
	
	public static JSONArray toJSONArray(Object object) {
        String str = "";
        Class<? extends Object> c = object.getClass();
        JSONArray jsonObject = new JSONArray();
        for (Field field : c.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            String value = null;
			try {
				value = String.valueOf(field.get(object));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			JSONObject object2 = new JSONObject();
			object2.put(name, value);
            jsonObject.add(object2);
        }
        System.out.println(jsonObject.toString());
        return jsonObject;
    }
	
	public boolean containsPlayerMap(Player player) {
		return playerMap.containsKey(player);
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public Map<UUID, PlayerManager> getPlayerMap() {
		return playerMap;
	}

	public void setPlayerMap(Map<UUID, PlayerManager> playerMap) {
		this.playerMap = playerMap;
	}

	public Integer getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}
	
	public Map<Player, PlayerManager> getReplayPlayersMap() {
		return replayPlayersMap;
	}
	
	public void setReplayPlayersMap(Map<Player, PlayerManager> replayPlayersMap) {
		this.replayPlayersMap = replayPlayersMap;
	}
	
}
