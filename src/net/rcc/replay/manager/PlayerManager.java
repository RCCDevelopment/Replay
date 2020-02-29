package net.rcc.replay.manager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PlayerManager {
	
	private Map<Integer, Location> locationMap;
	private Map<Integer, Block> breakedBlocks;
	private Map<Integer, Block> placedBlocks;
	private Map<Integer, Double> damageSelf;
	private Map<Integer, Entity> damageOther;
	private Map<Integer, ItemStack> dropedItems;
	private Map<Integer, ItemStack> pickupItems;
	private Map<Integer, PotionEffectType> potionsEffect;
	private Map<Integer, Effect> effects;
	private Map<Integer, Action> interact;
	private Map<Integer, ThrownPotion> thrownPotions;
	private UUID uuid;
	
	public PlayerManager(UUID uuid) {
		this.setLocationMap(new ConcurrentHashMap<>());
		this.setBreakedBlocks(new ConcurrentHashMap<>());
		this.setPlacedBlocks(new ConcurrentHashMap<>());
		this.setDamageSelf(new ConcurrentHashMap<>());
		this.setDamageOther(new ConcurrentHashMap<>());
		this.setDropedItems(new ConcurrentHashMap<>());
		this.setPickupItems(new ConcurrentHashMap<>());
		this.setPotionsEffect(new ConcurrentHashMap<>());
		this.setEffects(new ConcurrentHashMap<>());
		this.setInteract(new ConcurrentHashMap<>());
		this.setThrownPotions(new ConcurrentHashMap<>());
		
		this.setUuid(uuid);
	}

	public JsonObject save() {
		JsonObject object = new JsonObject();
		
		JsonArray jsonArray = new JsonArray();
		
		locationMap.forEach((playtime, location) -> {
			jsonArray.add(null);
		});
		
		object.addProperty(uuid.toString(), jsonArray.toString());
		
		return object;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Map<Integer, Location> getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(ConcurrentHashMap concurrentHashMap) {
		this.locationMap = concurrentHashMap;
	}

	public Map<Integer, Block> getBreakedBlocks() {
		return breakedBlocks;
	}

	public void setBreakedBlocks(ConcurrentHashMap concurrentHashMap) {
		this.breakedBlocks = concurrentHashMap;
	}

	public Map<Integer, Block> getPlacedBlocks() {
		return placedBlocks;
	}

	public void setPlacedBlocks(ConcurrentHashMap concurrentHashMap) {
		this.placedBlocks = concurrentHashMap;
	}

	public Map<Integer, Double> getDamageSelf() {
		return damageSelf;
	}

	public void setDamageSelf(Map<Integer, Double> damageSelf) {
		this.damageSelf = damageSelf;
	}

	public Map<Integer, Entity> getDamageOther() {
		return damageOther;
	}

	public void setDamageOther(Map<Integer, Entity> damageOther) {
		this.damageOther = damageOther;
	}

	public Map<Integer, ItemStack> getDropedItems() {
		return dropedItems;
	}

	public void setDropedItems(Map<Integer, ItemStack> dropedItems) {
		this.dropedItems = dropedItems;
	}

	public Map<Integer, ItemStack> getPickupItems() {
		return pickupItems;
	}

	public void setPickupItems(Map<Integer, ItemStack> pickupItems) {
		this.pickupItems = pickupItems;
	}

	public Map<Integer, PotionEffectType> getPotionsEffect() {
		return potionsEffect;
	}

	public void setPotionsEffect(Map<Integer, PotionEffectType> potionsEffect) {
		this.potionsEffect = potionsEffect;
	}

	public Map<Integer, Effect> getEffects() {
		return effects;
	}

	public void setEffects(Map<Integer, Effect> effects) {
		this.effects = effects;
	}

	public Map<Integer, Action> getInteract() {
		return interact;
	}

	public void setInteract(Map<Integer, Action> interact) {
		this.interact = interact;
	}

	public Map<Integer, ThrownPotion> getThrownPotions() {
		return thrownPotions;
	}

	public void setThrownPotions(Map<Integer, ThrownPotion> thrownPotions) {
		this.thrownPotions = thrownPotions;
	}
	
}
