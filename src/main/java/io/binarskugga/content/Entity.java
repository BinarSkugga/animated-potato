package io.binarskugga.content;

import io.binarskugga.event.ISubscriber;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Entity extends ConcurrentHashMap<Class<? extends IComponent>, List<IComponent>> implements ISubscriber {
	private static final AtomicLong idProvider = new AtomicLong(0);
	@Getter private final long id;
	@Getter private final World world;

	public Entity(World world) {
		this.id = idProvider.getAndIncrement();
		this.world = world;
	}

	@SuppressWarnings("unchecked")
	public <T extends IComponent> List<T> components(Class<T> componentClass) {
		return (List<T>) this.get(componentClass);
	}

	@SuppressWarnings("unchecked")
	public <T extends IComponent> T component(Class<T> componentClass) {
		return (T) this.get(componentClass).getFirst();
	}

	public boolean hasComponent(@NonNull Class<? extends IComponent> componentClass) {
		List<IComponent> components = this.get(componentClass);
		return components != null && !components.isEmpty();
	}

	@SafeVarargs
	public final boolean hasComponents(Class<? extends IComponent>... componentClasses) {
		for (Class<? extends IComponent> clazz : componentClasses) {
			if (!hasComponent(clazz)) return false;
		}
		return true;
	}
}
