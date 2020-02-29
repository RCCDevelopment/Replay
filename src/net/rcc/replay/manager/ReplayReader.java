package net.rcc.replay.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.owlike.genson.Genson;

import net.rcc.replay.SystemMain;

public class ReplayReader {

	public static JSONObject getRecordsObject() throws ParseException, IOException {
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(new String(Files.readAllBytes(SystemMain.getInstance().getReplayManager().getFile().toPath())));
        JSONObject object = (JSONObject) jsonObject.get("records");
        return object;
	}
	
	public static JSONObject getRecord(String path) throws ParseException, IOException {
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(new String(Files.readAllBytes(SystemMain.getInstance().getReplayManager().getFile().toPath())));
        JSONObject object = (JSONObject) jsonObject.get("records");
        JSONObject object2 = (JSONObject) object.get(path);
        return object2;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Player, PlayerManager> convertObjectToMap(JSONObject jsonObject) throws IOException, ParseException{
		
		JSONObject jObject = getRecordsObject();
		
		Map<Player, PlayerManager> map = new ConcurrentHashMap<>();
		
		Map<?, ?> hash = new HashMap<>(getRecordsObject());
		
		jObject.forEach((key, value) -> {
			try {
				
				
				
				//JSONObject uuid = getRecord(key.toString());
				
				//JSONObject locations = (JSONObject) uuid.get("locationMap");
				Bukkit.broadcastMessage(key + " - " + value);	
			} catch (NullPointerException ex) {
				
			}
		});
		
		return null;
	}
	
	public static Map<String, Object> toMap(JSONObject jsonobj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = (Iterator<String>) jsonobj.values();
        while(keys.hasNext()) {
            String key = keys.next();
            Object value = jsonobj.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }   
            map.put(key, value);
        }   return map;
    }

    public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }   return list;
    }

}
