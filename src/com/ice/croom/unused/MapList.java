package com.ice.croom.unused;

import java.util.ArrayList;
import java.util.List;

public class MapList<T1, T2> {
	private List<T2> arrays;

	private Iterable<T1> obj;
	private Lambda<T1, T2> func;
	public MapList(Lambda<T1, T2> func, Iterable<T1> obj) {
		this.obj = obj; this.func = func;
	}
	public List<T2> getValue() {
		this.arrays = new ArrayList<T2>();
		for (T1 o: obj) {
			this.arrays.add(func.run(o));
		}
		return this.arrays;
	}
}
