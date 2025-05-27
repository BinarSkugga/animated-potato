package io.binarskugga.content.components;

import io.binarskugga.content.BaseComponent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerComponent extends BaseComponent {
	public float speed;
	public boolean takingDamage;
}
