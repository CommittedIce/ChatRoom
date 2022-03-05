package com.ice.croom.message;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.ice.croom.util.Func;


public class Messages {
	public static UtilMessages utilMessage = new UtilMessages();
	public static InfoMessages infoMessage = new InfoMessages();
	public static ErrorMessages errorMessage = new ErrorMessages();
	//public static String varName = Func.putColor("");
	public static void sendMessage(OfflinePlayer sender, String text) {sendMessage((CommandSender) sender, text);}
	public static void sendMessage(CommandSender target, String text) {
		//sender.sendMessage(putcolor("&cunknown Command!"));
		target.sendMessage(Func.putColor(text));
	}
}
