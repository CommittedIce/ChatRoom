package com.ice.croom.message;

import java.util.Arrays;

import com.ice.croom.plugin.Var;
import com.ice.croom.util.Func;

public class InfoMessages {
	public String version = Func.putColor("&7[&bChatRoom Plugin (ver. " + Var.plugin.getDescription().getVersion()+")&7]");
	public String PluginInfo = String.join("\n", Arrays.asList(
			"",
			version,
			"&fmade by ICE#6871",
			"&7Type '/croom help' to "
	).stream().map((String t) -> Func.putColor(t+"&r")).toList());;
	public String PluginHelp = String.join("\n", Arrays.asList(
			"",
			version,
			"&fcommands: ",
			Messages.utilMessage.tab + "&7/croom ...",
			Messages.utilMessage.tab.repeat(2) + "&7info: Prints plugin info message.",
			Messages.utilMessage.tab.repeat(2) + "&7help: Prints this message.",
			Messages.utilMessage.tab.repeat(2) + "&7listen (on|off) <Room>: Listening settings in the ChatRoom.",
			Messages.utilMessage.tab.repeat(2) + "&7say <Room>: Starts to say on cretain ChatRoom.",
			Messages.utilMessage.tab.repeat(2) + "&7manage ...",
			Messages.utilMessage.tab.repeat(3) + "&7add <string>: Add new ChatRoom",
			Messages.utilMessage.tab.repeat(3) + "&7remove <Room>: Remove certain ChatRoom."
	).stream().map((String t) -> Func.putColor(t+"&r")).toList());
}
