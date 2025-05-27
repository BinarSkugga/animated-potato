package io.binarskugga.engine;

import io.binarskugga.engine.rendering.MeshBatch;

public class MeshBatches extends Registry<MeshBatch> {
	@Override
	public void initialize() {
		this.values().forEach(MeshBatch::initialize);
	}

	@Override
	public void destroy() {
		this.values().forEach(MeshBatch::destroy);
	}
}
