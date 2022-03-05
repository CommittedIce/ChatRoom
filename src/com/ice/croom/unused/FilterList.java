package com.ice.croom.unused;

import java.util.ArrayList;
import java.util.List;

public class FilterList<T> {
	private List<T> arrays;

	private Iterable<T> obj;
	private Lambda<T, Boolean> func;
	public FilterList(Lambda<T, Boolean> func, Iterable<T> obj) {
		this.obj = obj; this.func = func;
	}
	public List<T> getValue() {
		this.arrays = new ArrayList<T>();
		for (T o: obj) {
			if (func.run(o) == true) {
				this.arrays.add(o);
			}
		}
		return this.arrays;
	}
}
