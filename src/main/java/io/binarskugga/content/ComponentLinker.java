package io.binarskugga.content;

import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ComponentLinker extends ConcurrentHashMap<Class<? extends IComponent>, Map<Long, Entity>> {
	private final Map<Long, BitSet> entitiesComponentsMask = new ConcurrentHashMap<>();

	public ComponentLinker() {}

	public ComponentLinker add(@NonNull Entity e, @NonNull IComponent component) {
		Class<? extends IComponent> clazz = component.getClass();
		this.computeIfAbsent(clazz, k ->  new ConcurrentHashMap<>())
				.put(e.id(), e);

		e.computeIfAbsent(clazz, k -> new ArrayList<>())
				.add(component);

		entitiesComponentsMask.computeIfAbsent(e.id(), k -> new BitSet())
				.set(component.bitsetIndex());

		return this;
	}

	public ComponentLinker remove(@NonNull Entity e, @NonNull Class<? extends IComponent> clazz) {
		Map<Long, Entity> entities = this.get(clazz);
		if (entities == null) return this;

		e.remove(clazz);
		entities.remove(e.id());

		BitSet mask = entitiesComponentsMask.get(e.id());
		mask.clear(BaseComponent.hashcodeMap.get(clazz.hashCode()));
		if(mask.isEmpty())
			entitiesComponentsMask.remove(e.id());

		return this;
	}

	@SafeVarargs
	public final List<Entity> entities(@NonNull Class<? extends IComponent>... classes) {
		if (classes == null || classes.length == 0) return Collections.emptyList();

		Map<Long, Entity> entities = this.get(classes[0]);
		if(entities == null) return Collections.emptyList();

		BitSet queryMask = new BitSet();
		for (Class<? extends IComponent> clazz : classes) {
			queryMask.set(BaseComponent.hashcodeMap.get(clazz.hashCode()));
		}

		Map<Long, Entity> initialEntities = this.get(classes[0]);
		return initialEntities.values().stream().filter(entity -> {
			BitSet mask = entitiesComponentsMask.get(entity.id());
			if (mask == null) return false;

			BitSet temp = (BitSet) mask.clone();
			temp.and(queryMask);
			return temp.equals(queryMask);
		}).collect(Collectors.toList());
	}
}
