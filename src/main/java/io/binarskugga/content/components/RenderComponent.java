package io.binarskugga.content.components;

import io.binarskugga.content.BaseComponent;
import io.binarskugga.math.Vec2i;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RenderComponent extends BaseComponent {
	public String shader;
	public String mesh;
	public String atlas;
	public Vec2i atlasPosition;
}
