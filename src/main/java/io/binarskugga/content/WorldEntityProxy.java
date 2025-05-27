package io.binarskugga.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WorldEntityProxy {
	@Getter World world;
	@Getter Entity entity;

	public WorldEntityProxy add(IComponent component) {
		this.world.components().add(this.entity, component);
		return this;
	}
}
