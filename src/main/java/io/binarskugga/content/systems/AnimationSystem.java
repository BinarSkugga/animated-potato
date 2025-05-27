package io.binarskugga.content.systems;

import io.binarskugga.content.BaseSystem;
import io.binarskugga.content.Entity;
import io.binarskugga.content.components.AnimationComponent;
import io.binarskugga.content.components.RenderComponent;
import io.binarskugga.engine.Clock;
import io.binarskugga.engine.Scene;
import net.engio.mbassy.listener.Listener;

import java.util.List;

@Listener
public class AnimationSystem extends BaseSystem {
	@Override
	public void update(Clock clock, Scene scene) {
		float deltaSec = clock.delta() / 1_000_000f / 1000f;  // In seconds

		List<Entity> entities = this.components().entities(
				AnimationComponent.class, RenderComponent.class
		);

		for(Entity entity : entities) {
			AnimationComponent animation = entity.component(AnimationComponent.class);
			RenderComponent render = entity.component(RenderComponent.class);

			if(animation.frame == animation.maxFrames) animation.frame = 0;

			if(!animation.isYAxis) {
				if(animation.initialPosition == Integer.MIN_VALUE)
					animation.initialPosition = render.atlasPosition.x();
				if(animation.initialLine == Integer.MIN_VALUE)
					animation.initialLine = render.atlasPosition.y();
			} else {
				if(animation.initialPosition == Integer.MIN_VALUE)
					animation.initialPosition = render.atlasPosition.y();
				if(animation.initialLine == Integer.MIN_VALUE)
					animation.initialLine = render.atlasPosition.x();
			}

			if(!animation.active) {
				animation.frame = 0;
				if(!animation.isYAxis) {
					render.atlasPosition.x = animation.initialPosition;
					render.atlasPosition.y = animation.initialLine + animation.line;
				} else {
					render.atlasPosition.x = animation.initialLine + animation.line;
					render.atlasPosition.y = animation.initialPosition;
				}
				continue;
			}

			animation.accDelta += deltaSec;
			if(animation.accDelta < animation.frameDuration) continue;

			if(!animation.isYAxis) {
				render.atlasPosition.x = animation.initialPosition + animation.frame;
				render.atlasPosition.y = animation.initialLine + animation.line;
			} else {
				render.atlasPosition.x = animation.initialLine + animation.line;
				render.atlasPosition.y = animation.initialPosition + animation.frame;
			}

			animation.frame += 1;
			animation.accDelta = 0.0f;
		}
	}
}
