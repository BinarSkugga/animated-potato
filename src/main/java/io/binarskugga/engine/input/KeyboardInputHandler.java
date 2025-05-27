package io.binarskugga.engine.input;

import io.binarskugga.engine.Clock;
import io.binarskugga.event.EventBus;
import io.binarskugga.event.impl.KeyEvent;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.binarskugga.Constants.CONSECUTIVE_PRESS_TIMEOUT_MS;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class KeyboardInputHandler {
    private final Map<KeyboardButton, ButtonState> states = new HashMap<>();
    private final Map<KeyboardButton, Long> lastPressed = new HashMap<>();
    private final Map<KeyboardButton, Integer> consecutivePresses = new HashMap<>();

    public long consecutiveTimeout = TimeUnit.MILLISECONDS.toNanos(CONSECUTIVE_PRESS_TIMEOUT_MS);

    public ButtonState state(KeyboardButton key) {
        return this.states.getOrDefault(key, ButtonState.IDLE);
    }

    public int consecutivePress(KeyboardButton key) {
        return this.consecutivePresses.getOrDefault(key, 0);
    }

    public KeyboardInputHandler() {
        for(KeyboardButton key : KeyboardButton.all) {
            this.states.putIfAbsent(key, ButtonState.IDLE);
            this.consecutivePresses.putIfAbsent(key, 0);
            this.lastPressed.putIfAbsent(key, Clock.global.time());
        }
    }

    public void process(long windowID) {
        for(KeyboardButton key : KeyboardButton.all) {
            int code = key.code();
            if(code < 0) continue;

            int action = GLFW.glfwGetKey(windowID, code);

            ButtonState previousState = this.states.get(key);
            if(previousState == ButtonState.IDLE && action == GLFW.GLFW_RELEASE) continue;

            KeyEvent event = new KeyEvent();
            event.key = key;

            // Key is still pressed
            this.stillPressed(action, previousState, event);

            // Key was just pressed
            this.justPressed(action, previousState, event);

            // Key is still released
            this.stillReleased(action, previousState, event);

            // Key was just released
            this.justReleased(action, previousState, event);

            event.consecutivePress = this.consecutivePresses.get(key);
            EventBus.global.publish(event);
        }
    }

    private void justPressed(int action, ButtonState previousState, KeyEvent event) {
        // Key is not currently being pressed
        if(action != GLFW_PRESS) return;

        // We weren't released or idle past iteration, so it's not a new press
        if(previousState != ButtonState.IDLE && previousState != ButtonState.RELEASED) return;

        long currentTime = Clock.global.time();

        // Fill event data
        this.states.put(event.key, ButtonState.PRESSED);
        event.state = ButtonState.PRESSED;

        // Compute consecutive presses
        this.consecutivePresses.computeIfPresent(event.key, (key, consecutive) -> {
            if(consecutive == 0) return 1;

            if(currentTime - this.lastPressed.get(event.key) <= this.consecutiveTimeout) {
                return consecutive + 1;
            } else {
                return 1;
            }
        });
    }

    private void stillPressed(int action, ButtonState previousState, KeyEvent event) {
        // Key is not currently being pressed
        if(action != GLFW_PRESS) return;

        // We weren't pressed or help past iteration, so it's a new press
        if(previousState != ButtonState.PRESSED && previousState != ButtonState.HELD) return;

        this.states.put(event.key, ButtonState.HELD);
        event.state = ButtonState.HELD;
    }

    private void justReleased(int action, ButtonState previousState, KeyEvent event) {
        // Key is not currently released
        if(action != GLFW.GLFW_RELEASE) return;

        // It wasn't pressed or held last iteration, it wasn't just released
        if(previousState != ButtonState.PRESSED && previousState != ButtonState.HELD) return;

        this.states.put(event.key, ButtonState.RELEASED);
        event.state = ButtonState.RELEASED;

        // Update last pressed
        long currentTime = Clock.global.time();
        this.lastPressed.put(event.key, currentTime);
    }

    private void stillReleased(int action, ButtonState previousState, KeyEvent event) {
        // Key is not currently released
        if(action != GLFW.GLFW_RELEASE) return;

        // It wasn't released or idle last iteration, it was just released
        if(previousState != ButtonState.IDLE && previousState != ButtonState.RELEASED) return;

        this.states.put(event.key, ButtonState.IDLE);
        event.state = ButtonState.IDLE;

        // Compute consecutive presses
        long currentTime = Clock.global.time();
        this.consecutivePresses.computeIfPresent(event.key, (key, consecutive) -> {
            if(currentTime - this.lastPressed.get(event.key) > this.consecutiveTimeout) return 0;
            else return consecutive;
        });
    }
}
