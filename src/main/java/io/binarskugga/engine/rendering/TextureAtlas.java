package io.binarskugga.engine.rendering;

import io.binarskugga.engine.Textures;
import lombok.Getter;
import lombok.NonNull;

public class TextureAtlas {
	@Getter private String textureName;
	@Getter private Texture texture;
	@Getter private final int spriteWidth, spriteHeight;
	@Getter private final int padding;

	public TextureAtlas(String textureName, int spriteWidth, int spriteHeight, int padding) {
		this.textureName = textureName;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.padding = padding;
	}

	public void link(@NonNull Textures textures) {
		this.texture = textures.get(textureName);
	}

}
