package io.binarskugga.engine;

import io.binarskugga.engine.light.AmbientLight;
import io.binarskugga.engine.light.Light;
import io.binarskugga.engine.light.LightBuffer;
import io.binarskugga.math.Vec2f;
import io.binarskugga.math.Vec3f;
import lombok.Getter;
import lombok.Setter;
import net.engio.mbassy.listener.Listener;

import java.util.ArrayList;
import java.util.List;

@Listener
public class Scene implements ILowLevelResource {
	@Getter private boolean initialized;

	@Getter private final Shaders shaders;
	@Getter private final Textures textures;
	@Getter private final TextureAtlases atlases;
	@Getter private final Cameras cameras;
	@Getter private final MeshBatches meshes;

	@Getter private final LightBuffer lightBuffer;
	@Getter @Setter private AmbientLight ambientLight;
	@Getter @Setter private List<Light> lights;

	public Scene() {
		this.initialized = false;
		this.shaders = new Shaders("src/main/resources/shaders/");
		this.textures = new Textures("src/main/resources/textures/");
		this.atlases = new TextureAtlases(this.textures);
		this.cameras = new Cameras();
		this.meshes = new MeshBatches();

		this.lightBuffer = new LightBuffer(0);
	}

	@Override
	public void initialize() {
		if(this.initialized) return;

		this.lightBuffer.initialize();
		this.shaders.initialize();
		this.cameras.initialize();
		this.textures.initialize();
		this.atlases.initialize();
		this.meshes.initialize();

		this.initialized = true;
		System.out.println("Initialized scene");
	}

	@Override
	public void destroy() {
		if(!initialized) return;

		this.meshes.destroy();
		this.textures.destroy();
		this.cameras.destroy();
		this.shaders.destroy();
		this.lightBuffer.destroy();

		this.initialized = false;
		System.out.println("Cleaned scene");
	}
}
