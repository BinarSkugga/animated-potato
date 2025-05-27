package io.binarskugga.engine;

import io.binarskugga.engine.rendering.TextureAtlas;

public class TextureAtlases extends Registry<TextureAtlas> {
	private final Textures textures;

	public TextureAtlases(Textures textures) {
		this.textures = textures;
	}

	@Override
	public void initialize() {
		this.values().forEach(atlas -> {
			atlas.link(this.textures);
		});
	}
}
