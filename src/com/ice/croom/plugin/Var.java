package com.ice.croom.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.ice.croom.chatdata.ChatRoom;
import com.ice.croom.chatdata.PlayerChatData;
import com.ice.croom.util.DataSet;
import com.ice.croom.util.Func;

public class Var {
	public static ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();

	public static final String prefix = Func.putColor("&7[&b&oChatRoom&r&7]&r ");

	public static Plugin plugin = JavaPlugin.getPlugin(Plugin.class);

	public static File PluginFolder = JavaPlugin.getPlugin(Plugin.class).getDataFolder();
	public static DataSet<HashMap<Integer, ChatRoom>> rooms = new DataSet<HashMap<Integer, ChatRoom>>(new File(PluginFolder, "ChatRooms.data"));
	public static DataSet<HashMap<String, ChatRoom>> chattingroom = new DataSet<HashMap<String, ChatRoom>>(new File(PluginFolder, "SayRoom.data"));
	public static DataSet<HashMap<String, PlayerChatData>> chatData = new DataSet<HashMap<String, PlayerChatData>>(new File(PluginFolder, "ChatData.data"));

	public static Random random = new Random();

	public static ChatRoom mainRoom;
}
