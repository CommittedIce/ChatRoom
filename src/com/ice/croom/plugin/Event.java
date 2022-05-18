package com.ice.croom.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ice.croom.chatdata.ChatRoom;
import com.ice.croom.chatdata.PlayerChatData;
import com.ice.croom.util.Func;

import net.md_5.bungee.api.chat.TranslatableComponent;



public class Event implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ChatRoom.setDefaultInfo();
		PlayerChatData.setDefaultInfo();

		PlayerChatData pcd = PlayerChatData.getPlayerChatData(e.getPlayer());
		TranslatableComponent joinMessage = new TranslatableComponent("multiplayer.player.joined");
		joinMessage.addWith(Func.putColor(pcd.getStartPrefix() + e.getPlayer().getDisplayName() + pcd.getEndPrefix()));
		Bukkit.getOnlinePlayers().forEach((Player p) -> p.spigot().sendMessage(joinMessage));
		Var.consoleSender.spigot().sendMessage(joinMessage);
		e.setJoinMessage("");
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		PlayerChatData pcd = PlayerChatData.getPlayerChatData(e.getPlayer());
		TranslatableComponent leftMessage = new TranslatableComponent("multiplayer.player.left");
		leftMessage.addWith(Func.putColor(pcd.getStartPrefix() + e.getPlayer().getDisplayName() + pcd.getEndPrefix()));
		Bukkit.getOnlinePlayers().forEach((Player p) -> p.spigot().sendMessage(leftMessage));
		Var.consoleSender.spigot().sendMessage(leftMessage);
		e.setQuitMessage("");
	}
	@EventHandler
	public void onPlayerWrite(AsyncPlayerChatEvent e){
		Player player = e.getPlayer();
		String message = e.getMessage();

		e.setCancelled(true);

		ChatRoom cr = ChatRoom.getChatRoom(player);
		cr.sendMessage("[" + cr.getName() + "] " + PlayerChatData.getMessageForm(player, message));
	}
}
