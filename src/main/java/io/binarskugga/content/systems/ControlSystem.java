package io.binarskugga.content.systems;

import io.binarskugga.content.BaseSystem;
import io.binarskugga.content.Entity;
import io.binarskugga.content.components.AnimationComponent;
import io.binarskugga.content.components.LightComponent;
import io.binarskugga.content.components.PlayerComponent;
import io.binarskugga.content.components.TransformComponent;
import io.binarskugga.engine.Clock;
import io.binarskugga.engine.Scene;
import io.binarskugga.engine.input.KeyboardButton;
import io.binarskugga.event.ISubscriber;
import io.binarskugga.event.impl.KeyEvent;
import io.binarskugga.math.Vec2f;
import io.binarskugga.math.Vec3f;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;

import java.util.List;
import java.util.NoSuchElementException;

@Listener
public class ControlSystem extends BaseSystem implements ISubscriber {
	private Vec3f movement = new Vec3f(0, 0, 0);
	private int animationLine = 0;
	private boolean dash = false;
	private boolean takingDamage = false;

	private Entity mainPlayer = null;
	private boolean cameraCentered = false;

	@Override
	public void update(Clock clock, Scene scene) {
		if(this.mainPlayer == null) {
			this.mainPlayer = this.components().entities(
					TransformComponent.class, PlayerComponent.class
			).getFirst();
		}

		float deltaFactor = (float) clock.delta() / 1_000_000;
		if(deltaFactor == 0) deltaFactor = 1;

		TransformComponent transform = this.mainPlayer.component(TransformComponent.class);
		Vec2f halfSize = transform.size.div(2f);

		if(!this.cameraCentered) {
			// Instantly centers the camera so there is no visual glitch on opening
			scene.cameras().get("main").follow(transform.position.add(halfSize.x, halfSize.y, 0.0f), 1.0f);
			this.cameraCentered = true;
		}

		PlayerComponent player = this.mainPlayer.component(PlayerComponent.class);
		float adjustedSpeed = player.speed / deltaFactor;
		player.takingDamage = takingDamage;

		if(this.dash) {
			// Very crude dash
			transform.position = transform.position.add(this.movement.normalize().mul(200));
			this.dash = false;
		} else {
			transform.position = transform.position.add(this.movement.normalize().mul(adjustedSpeed));
		}

		try {
			AnimationComponent animation = this.mainPlayer.component(AnimationComponent.class);
			animation.line = animationLine;
			animation.active = movement.x != 0 || movement.y != 0;
		} catch (NoSuchElementException e) {}

		scene.cameras().get("main").follow(transform.position.add(halfSize.x, halfSize.y, 0.0f), 0.02f);
		movement.set(0.0f);
	}

	@Handler
	public void onWASD(KeyEvent event) {
		if(event.key != KeyboardButton.W && event.key != KeyboardButton.A
				&& event.key != KeyboardButton.S && event.key != KeyboardButton.D) {
			return;
		}

		if(event.key == KeyboardButton.W && event.isDown()) {
			this.movement.y(-1);
			this.animationLine = 3;
		}
		else if(event.key == KeyboardButton.S && event.isDown()) {
			this.movement.y(1);
			this.animationLine = 0;
		}

		if(event.key == KeyboardButton.A && event.isDown()) {
			this.movement.x(-1);
			this.animationLine = 1;
		}
		else if(event.key == KeyboardButton.D && event.isDown()) {
			this.movement.x(1);
			this.animationLine = 2;
		}
	}

	@Handler
	public void onSpace(KeyEvent event) {
		if(event.key != KeyboardButton.SPACE) return;

		if(event.isPressed()) {
			this.dash = true;
		}
	}

	@Handler
	public void onC(KeyEvent event) {
		if(event.key != KeyboardButton.C) return;
		this.takingDamage = event.isDown();
	}
}
