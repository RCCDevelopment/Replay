package net.rcc.replay.events;

import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ReplayTriggeredEvent extends PlayerEvent {
	
	public static HandlerList handlers;
	private Typ typ;
	private Block breakedBlock;
	private Block placedBlock;
	private Effect effect;
	private Entity targetDamageEntity;
	private Double damage;
	private PotionEffectType potionEffect;
	private ItemStack dropedItem;
	private ItemStack pickupedItem;
	private Action action;
	private ThrownPotion thrownPotion;
	
	public enum Typ {
		MOVE,
		BREAK_BLOCK,
		PLACED_BLOCK,
		DAMAGE_SELF,
		DAMAGE_OTHER,
		DROP_ITEM,
		PICKUP_ITEM,
		THROW,
		INTERACT;
	}

	public enum InteractBlockType {
		PLACE,BREAK;
	}
	
	public enum ItemType {
		DROPED,PICKUPED;
	}
	
	public ReplayTriggeredEvent(Player player, Typ typ, Block block, InteractBlockType blockType) {
		super(player);
		this.setTyp(typ);
		if(blockType.equals(InteractBlockType.BREAK)) {
			this.setBreakedBlock(block);
		} else if(blockType.equals(InteractBlockType.PLACE)) {
			this.setPlacedBlock(block);
		}
	}

	public ReplayTriggeredEvent(Player player, Typ typ, Effect effect) {
		super(player);
		this.setTyp(typ);
		this.setEffect(effect);
	}
	
	public ReplayTriggeredEvent(Player player, Typ typ, Entity targetEntity) {
		super(player);
		this.setTyp(typ);
		this.setTargetDamageEntity(targetEntity);
	}
	
	public ReplayTriggeredEvent(Player player, Typ typ, Double damage) {
		super(player);
		this.setTyp(typ);
		this.setDamage(damage);
	}
	
	public ReplayTriggeredEvent(Player player, Typ typ, PotionEffectType potionEffectType) {
		super(player);
		this.setTyp(typ);
		this.setPotionEffect(potionEffectType);
	}
	
	public ReplayTriggeredEvent(Player player, Typ typ, ItemStack itemStack, ItemType itemType) {
		super(player);
		this.setTyp(typ);
		if(itemType.equals(ItemType.DROPED)) {
			this.setDropedItem(itemStack);	
		} else if(itemType.equals(ItemType.PICKUPED)) {
			this.setPickupedItem(itemStack);
		}
	}
	
	public ReplayTriggeredEvent(Player player, Typ typ, Action action) {
		super(player);
		this.setTyp(typ);
		this.setAction(action);
	}
	
	public ReplayTriggeredEvent(Player player, Typ typ, ThrownPotion thrownPotion) {
		super(player);
		this.setTyp(typ);
		this.setThrownPotion(thrownPotion);
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Typ getTyp() {
		return typ;
	}

	public void setTyp(Typ typ) {
		this.typ = typ;
	}

	public Block getBreakedBlock() {
		return breakedBlock;
	}

	public void setBreakedBlock(Block breakedBlock) {
		this.breakedBlock = breakedBlock;
	}

	public Block getPlacedBlock() {
		return placedBlock;
	}

	public void setPlacedBlock(Block placedBlock) {
		this.placedBlock = placedBlock;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}

	public Entity getTargetEntity() {
		return targetDamageEntity;
	}

	public void setTargetDamageEntity(Entity targetDamageEntity) {
		this.targetDamageEntity = targetDamageEntity;
	}

	public Double getDamage() {
		return damage;
	}

	public void setDamage(Double damage) {
		this.damage = damage;
	}

	public PotionEffectType getPotionEffect() {
		return potionEffect;
	}

	public void setPotionEffect(PotionEffectType potionEffect) {
		this.potionEffect = potionEffect;
	}

	public ItemStack getDropedItem() {
		return dropedItem;
	}

	public void setDropedItem(ItemStack dropedItem) {
		this.dropedItem = dropedItem;
	}

	public ItemStack getPickupedItem() {
		return pickupedItem;
	}

	public void setPickupedItem(ItemStack pickupedItem) {
		this.pickupedItem = pickupedItem;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public ThrownPotion getThrownPotion() {
		return thrownPotion;
	}

	public void setThrownPotion(ThrownPotion thrownPotion) {
		this.thrownPotion = thrownPotion;
	}

	static {
		handlers = new HandlerList();
	}
}

