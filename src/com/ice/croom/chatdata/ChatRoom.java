package com.ice.croom.chatdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.ice.croom.message.Messages;
import com.ice.croom.plugin.Var;
import com.ice.croom.util.Func;

@SuppressWarnings("serial")
public class ChatRoom implements Serializable {
	private int identity;
	private String name;
	private String ownerUUID;
	private ArrayList<String> listenPlayers = new ArrayList<String>();
	//private HashMap<OfflinePlayer, ArrayList<ItemStack>> items;
	public ChatRoom(String name, int identity){
		setOf(name, identity);
	}
	public ChatRoom(String name) {
		int nid = Func.getRandint(10^10, 10^11-1);
		while (Var.rooms.getData().containsKey(nid)) { nid = Func.getRandint(10*10, 10*11-1); }
		setOf(name, nid);
	}
	private void setOf(String name, int identity){
		this.identity = identity;
		this.name = name;
		Var.rooms.getData().put(identity, this);
	}
	public int getIdentity() { return identity; }
	public ArrayList<String> getListenPlayers() { return listenPlayers; }
	public void setOwner(OfflinePlayer ofp) { this.ownerUUID = ofp.getUniqueId().toString(); }
	public void setOwner(String ofp) { this.ownerUUID = ofp; }
	public String getOwner() { return this.ownerUUID; }
	public String getName() {return this.name;}
	/*
	 * @return Whether the player joined already
	 */
	public boolean addListenPlayer(String p) {
		if (this.listenPlayers.contains(p)) {return true;}
		this.listenPlayers.add(p);
		return false;
	}
	/*
	 * @return Whether the player joined already
	 */
	public boolean addListenPlayer(OfflinePlayer p) { return this.addListenPlayer(p.getUniqueId().toString()); }
	/*
	 * @return Whether the player left already
	 */
	public boolean removeListenPlayer(String p) {
		if (!this.listenPlayers.contains(p)) {return true;}
		this.listenPlayers.remove(p);
		return false;
	}
	/*
	 * @return Whether the player left already
	 */
	public boolean removeListenPlayer(OfflinePlayer p) { return this.removeListenPlayer(p.getUniqueId().toString()); }
	public boolean hasModifyPermission(OfflinePlayer p) {
		return p.isOp() || p.getUniqueId().toString().equalsIgnoreCase(ownerUUID) || Var.chatData.getData().get(p.getUniqueId().toString()).getModifyPermission();
	}
	public void closeRoom() {
		List<String> players = new ArrayList<String>();
		Var.chattingroom.getData().forEach((String id, ChatRoom c)->{
			if (c == this) {
				players.add(id);

				Messages.sendMessage(Bukkit.getOfflinePlayer(UUID.fromString(id)), "&bSince the room " + this.name + " which you are chatting on is closed, you moved to main room!");
				Var.chattingroom.getData().put(id, Var.mainRoom);
				Var.mainRoom.addListenPlayer(id);
			}
		});
		for (String p : this.listenPlayers) {
			if (players.contains(p)) { continue; }
			Messages.sendMessage(Bukkit.getOfflinePlayer(UUID.fromString(p)), "&bThe room " + this.name + " which you are listening is closed!");
		}
		this.listenPlayers.clear();
		Var.rooms.getData().remove(this.identity);
		this.identity = -1;
	}
	public void sendMessage(String message) {
		this.listenPlayers.forEach((String id) -> {
			OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(id));
			if (p.isOnline()) {((Player) p).sendMessage(message);}
		});
	}





	public static void setDefaultInfo() {
		HashMap<String, ChatRoom> chats = Var.chattingroom.getData();
		for (Player p: Bukkit.getOnlinePlayers()) {
			String id = p.getUniqueId().toString();
			if (!chats.containsKey(id)) {
				chats.put(id, Var.mainRoom);
				Var.mainRoom.addListenPlayer(id);
				p.sendMessage("Since you didn't set chatroom, you moved on default chatroom!");

			}
		}

	}
	public static ChatRoom getChatRoom(String name, boolean getNull) {
		for (ChatRoom cr: Var.rooms.getData().values()) {
			if (cr.getName().equalsIgnoreCase(name)) {
				return cr;
			}
		}
		if (getNull) {
			return null;
		} else {
			return new ChatRoom(name);
		}
	}
	public static ChatRoom getChatRoom(OfflinePlayer p) {
		return Var.chattingroom.getData().get(p.getUniqueId().toString());
	}

}
