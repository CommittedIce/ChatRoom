package com.ice.croom.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ice.croom.chatdata.ChatRoom;
import com.ice.croom.chatdata.PlayerChatData;
import com.ice.croom.message.Messages;
import com.ice.croom.util.Func;

public class Plugin extends JavaPlugin{
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new Event(), this);
		File dataFolder = this.getDataFolder();
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}


		Var.random.setSeed(System.currentTimeMillis());

		Var.chattingroom.LoadFile();
		if (Var.chattingroom.getData() == null) {
			Var.chattingroom.setData(new HashMap<String, ChatRoom>());
		}
		Var.rooms.LoadFile();
		if (Var.rooms.getData() == null) {
			Var.rooms.setData(new HashMap<Integer, ChatRoom>());
			Var.mainRoom = new ChatRoom("#main", 10^10);
		} else { Var.mainRoom = Var.rooms.getData().get(10^10); }
		Var.chatData.LoadFile();
		if (Var.chatData.getData() == null) {
			Var.chatData.setData(new HashMap<String, PlayerChatData>());
		}
		Var.consoleSender.sendMessage(Var.rooms.getData().values().stream().map((ChatRoom cr)->cr.getListenPlayers()).collect(Collectors.toList()).toString());
		Var.consoleSender.sendMessage(Func.putColor("&bChatRoom Plugin loaded!"));

		/*
		for (int i = 1; i < 5; i++) {
			new ChatRoom("Main_" + i + "");
		}
		*/
		ChatRoom.setDefaultInfo();
		PlayerChatData.setDefaultInfo();
	}
	public void onDisable() {
		Var.chattingroom.SaveFile();
		Var.rooms.SaveFile();
		Var.chatData.SaveFile();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//boolean isPlayer = sender instanceof Player;
		String cmdName = cmd.getName();
		if (cmdName.equalsIgnoreCase("croom")) {
			if (args.length < 1) { Messages.sendMessage(sender, Messages.errorMessage.wrongCommand); return false; }
			if (args[0].equalsIgnoreCase("info")) {
				Messages.sendMessage(sender, Messages.infoMessage.PluginInfo);
				return true;
			} else if (args[0].equalsIgnoreCase("help")) {
				Messages.sendMessage(sender, Messages.infoMessage.PluginHelp);
				return true;
			}

			// Belows commands are only for players
			else if (sender instanceof ConsoleCommandSender) { Messages.sendMessage(sender, Messages.errorMessage.functionNotForConsole); return false; }


			else if (args[0].equalsIgnoreCase("listen")) {
				if (args.length < 3 ) { Messages.sendMessage(sender, Messages.errorMessage.wrongCommand); return false; }
				if (!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) { Messages.sendMessage(sender, Messages.errorMessage.wrongCommand); return false; }
				ChatRoom room = ChatRoom.getChatRoom(args[2], true);
				if (room == null) { sender.sendMessage(Func.putColor("&c"+args[2]+" is unknown room!")); return false; }
				if (args[1].equalsIgnoreCase("on")) {
					boolean alreadyJoin = room.addListenPlayer((((OfflinePlayer) sender).getUniqueId().toString()));
					if (alreadyJoin) { sender.sendMessage(Func.putColor("&cYou are already listening this room!")); return false; }
					sender.sendMessage(Func.putColor("&bNow you listen the room \""+args[2]+"\""));
					return true;
				} else if (args[1].equalsIgnoreCase("off")) {
					if (room == Var.chattingroom.getData().get(((OfflinePlayer) sender).getUniqueId().toString())) { sender.sendMessage(Func.putColor("&cAs you chatting on the room \""+args[2]+"\", you can't leave this room!")); return false; }
					boolean alreadyLeft = room.removeListenPlayer(((OfflinePlayer) sender).getUniqueId().toString());
					if (alreadyLeft) {sender.sendMessage(Func.putColor("&cYou are no longer listening this room")); return false; }
					sender.sendMessage(Func.putColor("&bNow you don't listen the room \""+args[2]+"\""));
					return true;
				}
				return false;
			} else if (args[0].equalsIgnoreCase("say")) {
				if (args.length < 2) { Messages.sendMessage(sender, Messages.errorMessage.wrongCommand); return false; }
				ChatRoom room = ChatRoom.getChatRoom(args[1], true);
				if (room == null) { Messages.sendMessage(sender, Messages.errorMessage.unknownChatRoom); return false; }
				Var.chattingroom.getData().put(((OfflinePlayer) sender).getUniqueId().toString(), room);
				boolean alreadyJoin = room.addListenPlayer(((OfflinePlayer) sender).getUniqueId().toString());
				if (!alreadyJoin) {sender.sendMessage(Func.putColor("&bSince you wasn't listening the room, you are made to listen that"));}
				sender.sendMessage(Func.putColor("&bNow you say on the room \""+args[1]+"\""));
				return true;
			} else if (args[0].equalsIgnoreCase("manage")) {
				if (args.length < 2) { Messages.sendMessage(sender, Messages.errorMessage.wrongCommand); return false; }
				if (args[1].equalsIgnoreCase("add")) {
					if (!PlayerChatData.hasModifyPermission((Player) sender)) { Messages.sendMessage(sender, Messages.errorMessage.hasNotPermission); return false;}
					if (args.length < 2 ) { Messages.sendMessage(sender, Messages.errorMessage.wrongCommand); return false; }
					if (Var.rooms.getData().values().stream().filter((ChatRoom v) -> v.getName().equalsIgnoreCase(args[2])).count() > 0) { Messages.sendMessage(sender, Messages.errorMessage.existingChatRoom); return false; };
					ChatRoom cr = new ChatRoom(args[2]);
					if (sender instanceof OfflinePlayer) {
						cr.addListenPlayer(((OfflinePlayer) sender).getUniqueId().toString());
						cr.setOwner(((OfflinePlayer) sender).getUniqueId().toString());
						Var.chattingroom.getData().put(((OfflinePlayer) sender).getUniqueId().toString(), cr);
						sender.sendMessage(Func.putColor("&bYou made chatroom " + args[2] + ", and you made listen and say on the room"));
					}
					sender.sendMessage(Func.putColor("&bSuccessfully made room " + args[2]));
					return true;
				} else if (args[1].equalsIgnoreCase("remove")) {
					if (args.length < 2 ) { Messages.sendMessage(sender, Messages.errorMessage.wrongCommand); return false; }
					ChatRoom cr = ChatRoom.getChatRoom(args[2], true);
					if (cr == null) { Messages.sendMessage(sender, Messages.errorMessage.unknownChatRoom); return false; }
					if (cr == Var.mainRoom) { Messages.sendMessage(sender, "&cYou can't remove main room!"); return false; }
					if (!cr.hasModifyPermission((OfflinePlayer) sender)) { Messages.sendMessage(sender, Messages.errorMessage.hasNotPermission); return false; }
					cr.closeRoom();
					return true;
				}
			}
		}
		return false;
	}
	/*
	*
	* Requests a list of possible completions for a command argument.
	*
	* @param sender Source of the command.  For players tab-completing a
	*	 command inside of a command block, this will be the player, not
	*	 the command block.
	* @param command Command which was executed
	* @param alias The alias used
	* @param args The arguments passed to the command, including final
	*	 partial argument to be completed and command label
	* @return A List of possible completions for the final argument, or null
	*	 to default to the command executor
	*/
		public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
		boolean isPlayer = sender instanceof Player;
		List<String> emptyList = new ArrayList<String>();
		args = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		switch (cmd.getName()) {
		case "croom":

			//About console
			if (!isPlayer) {
				if (args.length == 1) {
					return Arrays.asList("info", "help");
				}
				return emptyList;
			}


			if (args.length == 1) {
				return Arrays.asList("listen", "say", "manage", "info", "help");//, "modify");
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("listen")) { return Arrays.asList("on", "off");
				} else if (args[0].equalsIgnoreCase("say")) {
					ChatRoom cr = Var.chattingroom.getData().get(((OfflinePlayer) sender).getUniqueId().toString());
					return Var.rooms.getData().values().stream()
							.filter((ChatRoom v) -> v.getIdentity() != cr.getIdentity())
							.map((ChatRoom v) -> v.getName())
							.collect(Collectors.toList());
				} else if (args[0].equalsIgnoreCase("manage")) {
					if (PlayerChatData.hasModifyPermission((Player) sender)) { return Arrays.asList("add", "modify", "remove"); }
					return Arrays.asList("modify", "remove");
				}
				return emptyList;
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("listen")) {
					if (!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) {return emptyList;}
					OfflinePlayer ofp = (OfflinePlayer) sender;
					boolean ltype = !args[1].equalsIgnoreCase("on");

					return Var.rooms.getData().values().stream()
							.filter((ChatRoom v) -> v.getListenPlayers().contains(ofp.getUniqueId().toString()) == ltype)
							.map((ChatRoom v) -> v.getName())
							.collect(Collectors.toList());
				} else if (args[0].equalsIgnoreCase("manage")) {
					if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("modify")) {
						return Var.rooms.getData().values().stream()
									.filter((ChatRoom v) -> v.hasModifyPermission((OfflinePlayer) sender))
									.map((ChatRoom v) -> v.getName())
									.collect(Collectors.toList());
					}
				}
				return emptyList;
			}
			return emptyList;
		}
		return emptyList;
	}
}
