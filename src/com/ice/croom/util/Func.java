package com.ice.croom.util;

import java.util.UUID;

import org.bukkit.ChatColor;

import com.ice.croom.plugin.Var;

public class Func {
	public static String putColor(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	public static int getRandint(int min, int max) {
		return Var.random.nextInt(max - min + 1) + min;
	}
	public static boolean isSameUUID(UUID uuid1, UUID uuid2) {
		return uuid1.compareTo(uuid2) == 0;
	}
}
