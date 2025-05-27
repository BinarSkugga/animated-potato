package io.binarskugga.engine.rendering;

import io.binarskugga.engine.ILowLevelResource;
import io.binarskugga.math.*;
import lombok.Getter;
import lombok.NonNull;
import org.lwjgl.opengl.GL46;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL46.*;

public class ShaderProgram implements ILowLevelResource {
	@Getter private final int id;
	@Getter private final String name;
	private final Map<String, Integer> uniforms = new HashMap<>();

	private String vertexCode;
	private String fragmentCode;

	public ShaderProgram(String path, String name) {
		this.name = name;
		this.id = glCreateProgram();
		this.vertexCode = readFile(path + name + ".vert");
		this.fragmentCode = readFile(path + name + ".frag");
	}

	public ShaderProgram(String name) {
		this("src/main/resources/shaders/", name);
	}

	public void compile(Map<String, Object> compileContext) {
		for(Map.Entry<String, Object> entry : compileContext.entrySet()) {
			this.vertexCode = this.vertexCode.replaceAll(entry.getKey(), String.valueOf(entry.getValue()));
			this.fragmentCode = this.fragmentCode.replaceAll(entry.getKey(), String.valueOf(entry.getValue()));
		}

		int vertexShader = compileShader(GL46.GL_VERTEX_SHADER, this.vertexCode);
		int fragmentShader = compileShader(GL46.GL_FRAGMENT_SHADER, this.fragmentCode);

		glAttachShader(this.id, vertexShader);
		glAttachShader(this.id, fragmentShader);
		glLinkProgram(this.id);

		if (glGetProgrami(this.id, GL46.GL_LINK_STATUS) == 0)
			throw new RuntimeException("Error linking shader: " + GL46.glGetProgramInfoLog(this.id));

		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}

	public void compile() {
		this.compile(new HashMap<>());
	}

	@NonNull
	private static String readFile(String path) {
		try {
			return Files.readString(Paths.get(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read shader file: " + path, e);
		}
	}

	private static int compileShader(int type, String shaderCode) {
		int id = glCreateShader(type);
		glShaderSource(id, shaderCode);
		glCompileShader(id);

		if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
			throw new RuntimeException("Error compiling shader: " + GL46.glGetShaderInfoLog(id));
		}

		return id;
	}

	public void uniform(@NonNull Mat4f matrix, String name) {
		int location = this.getUniformLocation(name);
		glUniformMatrix4fv(location, false, matrix.toArray());
	}

	public void uniform(boolean value, String name) {
		int location = this.getUniformLocation(name);
		glUniform1i(location, value ? 1 : 0);
	}

	public void uniform(int value, String name) {
		int location = this.getUniformLocation(name);
		glUniform1i(location, value);
	}

	public void uniform(float value, String name) {
		int location = this.getUniformLocation(name);
		glUniform1f(location, value);
	}

	public void uniform(double value, String name) {
		int location = this.getUniformLocation(name);
		glUniform1d(location, value);
	}

	public void uniform(@NonNull Vec2f value, String name) {
		int location = this.getUniformLocation(name);
		glUniform2fv(location, value.toArray());
	}

	public void uniform(@NonNull Vec2d value, String name) {
		int location = this.getUniformLocation(name);
		glUniform2dv(location, value.toArray());
	}

	public void uniform(@NonNull Vec2i value, String name) {
		int location = this.getUniformLocation(name);
		glUniform2iv(location, value.toArray());
	}

	public void uniform(@NonNull Vec3f value, String name) {
		int location = this.getUniformLocation(name);
		glUniform3fv(location, value.toArray());
	}

	public void uniform(@NonNull Vec3d value, String name) {
		int location = this.getUniformLocation(name);
		glUniform3dv(location, value.toArray());
	}

	public void uniform(@NonNull Vec3i value, String name) {
		int location = this.getUniformLocation(name);
		glUniform3iv(location, value.toArray());
	}

	public void uniform(@NonNull Vec4f value, String name) {
		int location = this.getUniformLocation(name);
		glUniform4fv(location, value.toArray());
	}

	public void uniform(@NonNull Vec4d value, String name) {
		int location = this.getUniformLocation(name);
		glUniform4dv(location, value.toArray());
	}

	public void uniform(@NonNull Vec4i value, String name) {
		int location = this.getUniformLocation(name);
		glUniform4iv(location, value.toArray());
	}

	public int getUniformLocation(String name) {
		if(this.uniforms.containsKey(name)) return this.uniforms.get(name);
		return glGetUniformLocation(this.id, name);
	}

	@Override
	public void initialize() {
		// Nothing to do for now
	}

	@Override
	public void destroy() {
		glDeleteProgram(this.id);
	}

	public void bind() {
		glUseProgram(this.id);
	}

	public void unbind() {
		glUseProgram(0);
	}
}
