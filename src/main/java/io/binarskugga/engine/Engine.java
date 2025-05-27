package io.binarskugga.engine;

import io.binarskugga.content.World;
import io.binarskugga.content.components.*;
import io.binarskugga.content.systems.AnimationSystem;
import io.binarskugga.content.systems.ControlSystem;
import io.binarskugga.content.systems.LightSystem;
import io.binarskugga.content.systems.RenderSystem;
import io.binarskugga.engine.input.KeyboardButton;
import io.binarskugga.engine.rendering.Camera2D;
import io.binarskugga.engine.rendering.MeshBatch;
import io.binarskugga.engine.rendering.TextureAtlas;
import io.binarskugga.event.EventBus;
import io.binarskugga.event.ISubscriber;
import io.binarskugga.event.impl.KeyEvent;
import io.binarskugga.math.StaticMath;
import io.binarskugga.math.Vec2f;
import io.binarskugga.math.Vec2i;
import io.binarskugga.math.Vec3f;
import lombok.Getter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;


@Listener
public class Engine implements ISubscriber {
    @Getter private final Window window;
    @Getter private final World world;
    private Scene testScene;

    private int sceneIdx = 0;

    public Engine() {
        StaticMath.computeSinTable();
        StaticMath.computeCosTable();

        EventBus.global.subscribe(this);
        this.window = new Window(1200, 720, "Test");

        this.world = new World();
        this.world.addSystems(
                new ControlSystem(),
                new LightSystem(),
                new RenderSystem(),
                new AnimationSystem()
        );

        int tileSize = 64;
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                this.world.createEntity()
                    .add(new RenderComponent(
                            "main", "quad", "environment",
                            new Vec2i(0, 0))
                    ).add(new TransformComponent(
                        new Vec3f(i*tileSize, j*tileSize, 0.0f),
                        new Vec2f(tileSize, tileSize)
                    ));
            }
        }

        this.world.createEntity().add(new AmbientLightComponent(
                new Vec3f(1.0f, 1.0f, 1.0f),
                0.5f
        ));

        this.world.createEntity().add(new LightComponent(
                new Vec3f(0.0f),
                new Vec3f(1.0f, 1.0f, 1.0f),
                10.0f, true
        )).add(new TransformComponent(
                new Vec3f(300, 300, 6),
                new Vec2f(1.0f, 1.0f)
        ));

        this.world.createEntity()
                .add(new RenderComponent("main", "quad", "character", new Vec2i(0,0)))
                .add(new TransformComponent(
                        new Vec3f(100f, 100f, 5.0f),
                        new Vec2f(100f, 100f),
                        0.0f
                )).add(new PlayerComponent(4.0f, false))
                .add(new AnimationComponent(4));

        this.testScene = new Scene();
        this.testScene.cameras().put("main", new Camera2D(window));
        this.testScene.meshes().put("quad", new MeshBatch(
                new int[]{0, 1, 2, 3},
                new float[]{
                        0f, 0f, 0f,
                        0f, 1f, 0f,
                        1f, 0f, 0f,
                        1f, 1f, 0f
                },
                1_000_000
        ));
        this.testScene.atlases().put("image", new TextureAtlas(
                "image", 150, 150, 0
        ));
        this.testScene.atlases().put("image2", new TextureAtlas(
                "image2", 250, 300, 0
        ));
        this.testScene.atlases().put("character", new TextureAtlas(
                "character", 256 / 4, 256 / 4, 0
        ));
        this.testScene.atlases().put("environment", new TextureAtlas(
                "environment", 16, 16, 0
        ));
    }

    public void start() {
        this.window.initialize();
        this.inititalizeGL();

        this.testScene.initialize();

        while(!this.window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            this.window.pollEvents();

            Scene scene = this.sceneIdx == 0 ? this.testScene : null;
            this.world.update(Clock.global, scene);

            this.window.swapBuffers();
            Clock.global.tick();
        }

        this.testScene.destroy();
        this.window.destroy();
    }

    @Handler
    private void onTab(KeyEvent event) {
        if(event.key != KeyboardButton.TAB || !event.isReleased()) return;

        if(this.sceneIdx == 0) this.sceneIdx = 1;
        else if(this.sceneIdx == 1) this.sceneIdx = 0;
    }

    private void inititalizeGL() {
        GL.createCapabilities();
        glClearColor(0f, 0f, 0f, 1.0f);

        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glEnable(GL_MULTISAMPLE);

        glDepthFunc(GL_LESS);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDepthMask(true);
    }
}
