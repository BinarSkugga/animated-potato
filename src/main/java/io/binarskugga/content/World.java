package io.binarskugga.content;

import io.binarskugga.engine.Clock;
import io.binarskugga.engine.Scene;
import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;

public class World {
	@Getter private final List<ISystem> systems;
	@Getter private final ComponentLinker components;

	public World() {
		this.systems = new LinkedList<>();
		this.components = new ComponentLinker();
	}

	public void update(@NonNull Clock clock, Scene scene) {
		this.systems.forEach(system -> system.update(clock, scene));
	}

	@SafeVarargs
	public final <T extends ISystem> void addSystems(T @NonNull ... systems) {
		for(ISystem system : systems) {
			this.systems.add(system);
			system.components(this.components);
		}
	}

	public WorldEntityProxy createEntity() {
		Entity entity = new Entity(this);
		return new WorldEntityProxy(this, entity);
	}
}
