package io.binarskugga.engine.rendering;

import io.binarskugga.engine.ILowLevelResource;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.lwjgl.opengl.GL46.*;

public class Texture implements ILowLevelResource {
	private int id;
	@Getter private int width;
	@Getter private int height;

	public  Texture(String path) {
		try {
			// Load PNG using ImageIO
			BufferedImage image = ImageIO.read(new File(path));
			this.width = image.getWidth();
			this.height = image.getHeight();

			int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

			ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.LITTLE_ENDIAN);
			for(int pixel : pixels) {
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // R
				buffer.put((byte) ((pixel >> 8) & 0xFF));  // G
				buffer.put((byte) (pixel & 0xFF));         // B
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // A
			}
			buffer.flip();

			// Create and configure OpenGL texture
			this.id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, this.id);

			// Set texture parameters
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

			// Upload texture data
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glGenerateMipmap(GL_TEXTURE_2D); // Generate mipmaps for better scaling
			this.unbind();
		} catch (IOException e) {
			throw new RuntimeException("Failed to load texture: " + path, e);
		}
	}

	@Override
	public void initialize() {

	}

	@Override
	public void destroy() {
		glDeleteTextures(id);
	}

	public void bind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, this.id);
	}

	public void bind() {
		this.bind(0);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
