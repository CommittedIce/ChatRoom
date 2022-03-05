package com.ice.croom.plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ice.croom.chatdata.ChatRoom;
import com.ice.croom.chatdata.PlayerChatData;



public class Event implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ChatRoom.setDefaultInfo();
		PlayerChatData.setDefaultInfo();
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
