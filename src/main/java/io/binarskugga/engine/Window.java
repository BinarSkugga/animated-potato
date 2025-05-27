package io.binarskugga.engine;

import io.binarskugga.engine.input.KeyboardInputHandler;
import io.binarskugga.event.EventBus;
import io.binarskugga.event.ISubscriber;
import io.binarskugga.event.impl.*;
import lombok.Getter;
import lombok.Setter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import org.lwjgl.glfw.GLFWVidMode;

import static io.binarskugga.Constants.MSAA_SAMPLES;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.system.MemoryUtil.NULL;

@Listener
public class Window implements ISubscriber, ILowLevelResource {
    @Getter @Setter private int width, height;
    @Getter @Setter private boolean vsync = false;
    @Getter @Setter private String title;
    private KeyboardInputHandler keyboardHandler;

    @Getter private long id = 0;

    public Window(int width, int height, String title) {
        EventBus.global.subscribe(this);
        this.width = width;
        this.height = height;
        this.title = title;
        this.keyboardHandler = new KeyboardInputHandler();
    }

    @Override
    public void initialize() {
        createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW.");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, MSAA_SAMPLES);

        this.id = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (this.id == NULL)
            throw new IllegalStateException("Failed to create the GLFW window.");

        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (mode == null)
            throw new IllegalStateException("Failed to get monitor video mode.");

//        glfwSetWindowPos(
//            this.id,
//            (mode.width() - this.width) / 2,
//            (mode.height() - this.height) / 2
//        );

        glfwMakeContextCurrent(this.id);
        glfwSwapInterval(this.vsync ? 1 : 0);
        glfwShowWindow(this.id);

        glfwSetFramebufferSizeCallback(this.id, (window, width, height) -> {
            this.width = width;
            this.height = height;
            EventBus.global.publish(new WindowResizeEvent(window, width, height));
        });
        glfwSetCursorPosCallback(this.id, (window, x, y) -> {
            EventBus.global.publish(new MouseMoveEvent(window, x, y));
        });
        glfwSetScrollCallback(this.id, (window, x, y) -> {
            double[] xpos = new double[1];
            double[] ypos = new double[1];
            glfwGetCursorPos(window, xpos, ypos);
            EventBus.global.publish(new MouseScrollEvent(window, x, y, xpos[0], ypos[0]));
        });
    }

    @Override
    public void destroy() {
        glfwFreeCallbacks(this.id);
        glfwDestroyWindow(this.id);
        glfwTerminate();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.id);
    }

    public void swapBuffers() {
        glfwSwapBuffers(this.id);
    }

    public void pollEvents() {
        glfwPollEvents();
        this.keyboardHandler.process(this.id);
    }

    @Handler
    public void onClockSecondTick(ClockSecondResetEvent event) {
        glfwSetWindowTitle(this.id, this.title + " - " + event.fps + "FPS");
    }
}
