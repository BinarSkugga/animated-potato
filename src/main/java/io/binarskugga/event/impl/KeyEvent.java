package io.binarskugga.event.impl;

import io.binarskugga.engine.input.ButtonState;
import io.binarskugga.engine.input.KeyboardButton;
import io.binarskugga.event.IEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class KeyEvent implements IEvent {
	public long window;
	public KeyboardButton key;
	public ButtonState state;
	public int consecutivePress;

	public boolean isPressed() {
		return this.state == ButtonState.PRESSED;
	}

	public boolean isDoublePressed() {
		return this.isPressed() && this.consecutivePress >= 2;
	}

	public boolean isHeld() {
		return this.state == ButtonState.HELD;
	}

	public boolean isDown() {
		return this.isPressed() || this.isHeld();
	}

	public boolean isReleased() {
		return this.state == ButtonState.RELEASED;
	}

	public boolean isIdle() {
		return this.state == ButtonState.IDLE;
	}

	public boolean isUp() {
		return this.isReleased() || this.isIdle();
	}
}
