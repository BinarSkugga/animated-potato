package io.binarskugga.engine;

import io.binarskugga.engine.rendering.Texture;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.stream.Stream;

@AllArgsConstructor
public class Textures extends Registry<Texture> {
	@Getter private String path;

	@Override
	public void initialize() {
		Stream.of(new File(this.path).listFiles())
				.forEach(file -> {
					String textureName = file.getName().substring(0, file.getName().lastIndexOf('.'));
					if(this.containsKey(textureName)) return;

					Texture texture = new Texture(file.getAbsolutePath());
					this.put(textureName, texture);
				});
	}

	@Override
	public void destroy() {
		this.values().forEach(Texture::destroy);
	}
}
