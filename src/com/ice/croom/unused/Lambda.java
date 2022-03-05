package com.ice.croom.unused;

@FunctionalInterface
public interface Lambda<T1, T2> {
	public abstract T2 run(T1 value);
}
