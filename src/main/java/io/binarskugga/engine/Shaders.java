package io.binarskugga.engine;

import io.binarskugga.engine.rendering.ShaderProgram;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.binarskugga.Constants.MAX_LIGHTS;

@AllArgsConstructor
public class Shaders extends Registry<ShaderProgram> {
	@Getter private String path;

	@Override
	public void initialize() {
		Map<String, Object> compileContext = new HashMap<>();
		compileContext.put("__MAX_LIGHTS__", MAX_LIGHTS);

		Stream.of(new File(this.path).listFiles())
				.forEach(file -> {
					String shaderName = file.getName().substring(0, file.getName().lastIndexOf('.'));
					if(this.containsKey(shaderName)) return;

					ShaderProgram program = new ShaderProgram(this.path, shaderName);
					program.compile(compileContext);
					this.put(shaderName, program);
				});
	}

	@Override
	public void destroy() {
		this.values().forEach(ShaderProgram::destroy);
	}
}
