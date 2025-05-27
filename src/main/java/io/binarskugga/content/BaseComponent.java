package io.binarskugga.content;

import lombok.Getter;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseComponent implements IComponent {
	private static AtomicInteger bitsetIndexer = new AtomicInteger(0);
	public static HashMap<Integer, Integer> hashcodeMap = new HashMap<>();

	@Getter public boolean dirty = false;

	public int bitsetIndex() {
		int code = this.getClass().hashCode();
		return hashcodeMap.computeIfAbsent(code, k -> bitsetIndexer.incrementAndGet());
	}
}
