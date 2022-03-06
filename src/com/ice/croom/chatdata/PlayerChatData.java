package com.ice.croom.chatdata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.ice.croom.plugin.Var;
import com.ice.croom.util.Func;

@SuppressWarnings("serial")
public class PlayerChatData implements Serializable {
	private String startPrefix = "&7";
	private String endPrefix = "";
	private String chatCnt = " :: ";
	private boolean hasModifyPermission = false;
	private String playerUUID;
	public PlayerChatData(OfflinePlayer player) {
		this.playerUUID = player.getUniqueId().toString();
	}
	public PlayerChatData(UUID player) {
		this.playerUUID = player.toString();
	}
	public String getStartPrefix() { return startPrefix; }
	public String getEndPrefix() { return endPrefix; }
	public String getChatCnt() { return chatCnt; }
	public String getPrefix() { return startPrefix + Bukkit.getOfflinePlayer(UUID.fromString(this.playerUUID)).getName() + endPrefix; }
	public String getMessageForm(String message) { return Func.putColor(startPrefix + Bukkit.getOfflinePlayer(UUID.fromString(this.playerUUID)).getName() + endPrefix + chatCnt + message); }
	public boolean getModifyPermission() { return hasModifyPermission; }

	public void setStartPrefix(String prefix) { this.startPrefix = prefix; }
	public void setEndPrefix(String prefix) { this.endPrefix = prefix; }
	public void setChatCnt(String cnt) { this.chatCnt = cnt; }
	public void setModifyPermission(boolean modifyPermission) { this.hasModifyPermission = modifyPermission; }



	public static void setDefaultInfo() {
		HashMap<String, PlayerChatData> pcdHash = Var.chatData.getData();
		Set<String> pSet = pcdHash.keySet();
		for (Player p: Bukkit.getOnlinePlayers()) {
			if (!pSet.contains(p.getUniqueId().toString())) {
				pcdHash.put(p.getUniqueId().toString(), new PlayerChatData(p));
			}
		}
	}
	public static PlayerChatData getPlayerChatData(Player player) {
		return Var.chatData.getData().get(player.getUniqueId().toString());
	}
	@Deprecated
	public static String getPrefix(Player player) {
		return Var.chatData.getData().get(player.getUniqueId().toString()).getPrefix();
	}
	public static String getMessageForm(Player player, String message) {
		return Var.chatData.getData().get(player.getUniqueId().toString()).getMessageForm(message);
	}
	public static boolean hasModifyPermission(Player player) {
		return player.isOp() || Var.chatData.getData().get(player.getUniqueId().toString()).getModifyPermission();
	}
}
