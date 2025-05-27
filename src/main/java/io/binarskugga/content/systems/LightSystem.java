package io.binarskugga.content.systems;

import io.binarskugga.content.BaseSystem;
import io.binarskugga.content.Entity;
import io.binarskugga.content.components.AmbientLightComponent;
import io.binarskugga.content.components.LightComponent;
import io.binarskugga.content.components.TransformComponent;
import io.binarskugga.engine.Clock;
import io.binarskugga.engine.Scene;
import io.binarskugga.engine.light.AmbientLight;
import io.binarskugga.engine.light.Light;
import io.binarskugga.event.ISubscriber;
import io.binarskugga.math.Vec2f;
import io.binarskugga.math.Vec3f;
import net.engio.mbassy.listener.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static io.binarskugga.math.StaticMath.sin;

@Listener
public class LightSystem extends BaseSystem implements ISubscriber {
	private float time = 0.0f;

	@Override
	public void update(Clock clock, Scene scene) {
		float delta = clock.delta() / 1_000_000f / 1000f;
		time += delta;
		Collection<Entity> entities = this.components().entities(
				TransformComponent.class, LightComponent.class
		);

		List<Light> lights = new ArrayList<>();
		for (Entity entity : entities) {
			TransformComponent transform = entity.component(TransformComponent.class);
			List<LightComponent> lightComponents = entity.components(LightComponent.class);

			for(LightComponent l : lightComponents) {
				Vec3f lightPosition = transform.transform(l.offset);

				if(l.isFlickering) {
					float adjustedFlickerSpeed = l.flickerSpeed / delta;

					// Third and second frequencies param are arbitrary
					float freq1 = sin(time * adjustedFlickerSpeed) * l.flickerAmplitude;
					float freq2 = sin(time * adjustedFlickerSpeed * 1.7f) * l.flickerAmplitude * 0.5f;
					float freq3 = sin(time * adjustedFlickerSpeed * 2.4f) * l.flickerAmplitude * 0.2f;

					float flicker = freq1 + freq2 + freq3;
					l.intensity = Math.max(0.0f, l.intensity + flicker);
				}

				lights.add(new Light(
						lightPosition,
						l.color, l.intensity,
						l.constant, l.linear, l.quadratic
				));
			}
		}

		try {
			Entity ambientLight = this.components().entities(AmbientLightComponent.class).getFirst();
			AmbientLightComponent component = ambientLight.component(AmbientLightComponent.class);
			scene.ambientLight(new AmbientLight(
					component.color, component.intensity
			));
		} catch (NoSuchElementException e) {}

		scene.lights(lights);
	}
}
