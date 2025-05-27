package io.binarskugga.engine.light;

import io.binarskugga.engine.ILowLevelResource;
import io.binarskugga.engine.rendering.ShaderProgram;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

import static io.binarskugga.Constants.MAX_LIGHTS;
import static org.lwjgl.opengl.GL46.*;

public class LightBuffer implements ILowLevelResource {
	private final int LIGHT_SIZE = 16;

	private int id;
	private final int binding;
	private FloatBuffer buffer;

	public LightBuffer(int binding) {
		this.binding = binding;
		this.buffer = BufferUtils.createFloatBuffer(MAX_LIGHTS * LIGHT_SIZE);
	}

	@Override
	public void initialize() {
		this.id = glGenBuffers();
		glBindBuffer(GL_SHADER_STORAGE_BUFFER, this.id);
		glBufferData(GL_SHADER_STORAGE_BUFFER, MAX_LIGHTS * LIGHT_SIZE * Float.BYTES, GL_DYNAMIC_DRAW);
		glBindBufferBase(GL_SHADER_STORAGE_BUFFER, this.binding, this.id);
	}

	@Override
	public void destroy() {
		glDeleteBuffers(this.id);
	}

	public void bind(ShaderProgram program) {
		int blockIndex = glGetProgramResourceIndex(program.id(), GL_SHADER_STORAGE_BLOCK, "uLights");
		glShaderStorageBlockBinding(program.id(), blockIndex, this.binding);
	}

	public void update(List<Light> lights) {
		this.buffer.clear();
		for (Light light : lights) {
			float[] lightData = light.toUBOData();
			this.buffer.put(lightData);
		}
		this.buffer.flip();

		glBindBuffer(GL_SHADER_STORAGE_BUFFER, this.id);
		glBufferSubData(GL_SHADER_STORAGE_BUFFER, 0, this.buffer);
	}
}
