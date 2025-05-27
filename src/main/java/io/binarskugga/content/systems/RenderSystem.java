package io.binarskugga.content.systems;

import io.binarskugga.content.BaseSystem;
import io.binarskugga.content.Entity;
import io.binarskugga.content.components.PlayerComponent;
import io.binarskugga.content.components.RenderComponent;
import io.binarskugga.content.components.TransformComponent;
import io.binarskugga.engine.Clock;
import io.binarskugga.engine.Scene;
import io.binarskugga.engine.input.KeyboardButton;
import io.binarskugga.engine.rendering.*;
import io.binarskugga.event.ISubscriber;
import io.binarskugga.event.impl.KeyEvent;
import io.binarskugga.event.impl.WindowResizeEvent;
import io.binarskugga.math.Mat4f;
import io.binarskugga.math.Vec2f;
import lombok.NonNull;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;

import java.util.*;

import static org.lwjgl.opengl.GL46.*;

@Listener
public class RenderSystem extends BaseSystem implements ISubscriber {
	public Map<String, Map<String, Map<String, List<Entity>>>> organizeEntities(Collection<Entity> entities) {
		Map<String, Map<String, Map<String, List<Entity>>>> organizedEntities = new HashMap<>();
		entities.forEach(e -> {
			RenderComponent render = e.component(RenderComponent.class);
			organizedEntities
					.computeIfAbsent(render.shader, k -> new HashMap<>())
					.computeIfAbsent(render.atlas, k -> new HashMap<>())
					.computeIfAbsent(render.mesh, k -> new ArrayList<>())
					.add(e);
		});

		return organizedEntities;
	}

	public void update(Clock clock, @NonNull Scene scene) {
		if(!scene.initialized()) return;

		Camera2D camera = scene.cameras().get("main");
		List<Entity> entities = this.components().entities(
				TransformComponent.class, RenderComponent.class
		);

		Map<String, Map<String, Map<String, List<Entity>>>> organizedEntities = organizeEntities(entities);
		organizedEntities.forEach((shader, atlasOrganized) -> {
			ShaderProgram program = scene.shaders().get(shader);

			program.bind();
			program.uniform(camera.projection(), "uProjection");

			if(scene.ambientLight() != null) {
				program.uniform(scene.ambientLight().color, "uAmbientColor");
				program.uniform(scene.ambientLight().intensity, "uAmbientIntensity");
			}

			if(scene.lights() != null) {
				program.uniform(scene.lights().size(), "uLightCount");
				scene.lightBuffer().bind(program);
				scene.lightBuffer().update(scene.lights());
			}

			atlasOrganized.forEach((a, meshOrganized) -> {
				TextureAtlas atlas = scene.atlases().get(a);
				atlas.texture().bind();

				Vec2f textureSize = new Vec2f(atlas.texture().width(), atlas.texture().height());
				Vec2f spriteSize = new Vec2f(atlas.spriteWidth(), atlas.spriteHeight());
				program.uniform(textureSize, "uTextureSize");
				program.uniform(spriteSize, "uSpriteSize");
				program.uniform(atlas.padding(), "uTexturePadding");
				program.uniform(0, "uTexture0");

				meshOrganized.forEach((m, ents) -> {
					MeshBatch mesh = scene.meshes().get(m);

					List<Vec2f> atlasPositions = new ArrayList<>();
					List<Mat4f> transforms = new ArrayList<>();
					ents.stream().sorted(Comparator.comparing(
						ent -> ent.component(TransformComponent.class).position.z)
					).forEach(ent -> {
						TransformComponent transform = ent.component(TransformComponent.class);
						RenderComponent render = ent.component(RenderComponent.class);

						atlasPositions.add(new Vec2f(render.atlasPosition.x, render.atlasPosition.y));
						transforms.add(transform.transformation());

						try {
							PlayerComponent player = ent.component(PlayerComponent.class);
							program.uniform(player.takingDamage, "uTakingDamage");
						} catch (NoSuchElementException | NullPointerException e) {
							program.uniform(false, "uTakingDamage");
						}
					});

					mesh.update(atlasPositions, transforms);
					mesh.draw(transforms.size());
				});
			});
		});
	}

	@Handler
	public void onWindowResize(@NonNull WindowResizeEvent event) {
		glViewport(0, 0, Math.min(event.width, 1000), Math.min(event.height, 800));
	}

	@Handler
	public void onF1(@NonNull KeyEvent event) {
		if (event.key != KeyboardButton.F1 || !event.isReleased()) return;

		int polyMode = glGetInteger(GL_POLYGON_MODE);
		if (polyMode == GL_LINE)
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		if (polyMode == GL_FILL)
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

//	@Handler
//	public void onMouseMove(MouseMoveEvent event) {
//		Camera2D camera = scene.cameras().get("main");
//		Vec2f position = new Vec2f((float) event.x, (float) event.y).div(this.camera.zoom());
//		this.lights.get(0).position = position;
//	}
}
