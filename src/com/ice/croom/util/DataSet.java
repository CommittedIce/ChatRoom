package com.ice.croom.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import com.ice.croom.plugin.Var;

public class DataSet<T>{
	private File path;
	private T data;
	public DataSet(File path){this.path = path;}
	public T getData() {
		return this.data;
	}
	public void setData(T value) {
		this.data = value;
	}
	public void SaveFile() {
		Var.consoleSender.sendRawMessage(this.path.exists()+"");
		OutputStream out = null;
		BufferedOutputStream bout = null;
		BukkitObjectOutputStream oout = null;
		try {
			out = new FileOutputStream(this.path);
			bout = new BufferedOutputStream(out);
			oout = new BukkitObjectOutputStream(bout);
			oout.writeObject(this.data);
		}
		catch (Exception var13) { var13.printStackTrace();} finally {
			try {oout.close();}
			 catch (IOException var12) {var12.printStackTrace();}
		}
	}
	@SuppressWarnings("unchecked")
	public void LoadFile() {
		InputStream in = null;
		BufferedInputStream bin = null;
		BukkitObjectInputStream oin = null;

		if (!(this.path.exists() && this.path.isFile()))
		{
			try {this.path.createNewFile();}
			catch (IOException ioe) {ioe.getStackTrace();}
			this.data = null;
			return;
		}
		try {
			in = new FileInputStream(this.path);
			bin = new BufferedInputStream(in);
			oin = new BukkitObjectInputStream(bin);
			this.data = (T) oin.readObject();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {oin.close();}
			catch (IOException ioe) {ioe.printStackTrace();}
		}
		return;
	}
}