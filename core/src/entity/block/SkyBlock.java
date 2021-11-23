package entity.block;

import app.render.BaseAssetManager;
import common.Clock;
import entity.EntityBodyBuilder;

public class SkyBlock extends EmptyBlock {
  public SkyBlock(
      Clock clock, BaseAssetManager baseAssetManager, EntityBodyBuilder entityBodyBuilder) {
    super(clock, baseAssetManager, entityBodyBuilder);
    this.textureName = "sky.png";
  }

  @Override
  public synchronized void renderSync() {
    if (this.coordinates.getY() < 0) {
      this.textureName = "dirty.png";
    }
    super.renderSync();
  }
}