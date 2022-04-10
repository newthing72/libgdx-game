package entity.collision;

import com.badlogic.gdx.physics.box2d.Body;
import common.exceptions.BodyNotFound;

public abstract class CollisionPoint {

  private Body body;

  public CollisionPoint(Body body) {
    this.body = body;
  }

  public Body getBody() throws BodyNotFound {
    if (body == null) throw new BodyNotFound("In CollisionPoint");
    return body;
  }
}