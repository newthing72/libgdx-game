package core.entity.controllers.events.types;

import core.entity.Entity;
import core.common.Coordinates;

public class EntityEventTypeFactory {
  public static FallDamageEventType createFallDamageEventType(
      Coordinates last_position, Coordinates new_position, Entity entity) {
    return new FallDamageEventType(last_position, new_position, entity);
  }

  public static ChangeHealthEventType createChangeHealthEventType(Entity entity) {
    return new ChangeHealthEventType(entity);
  }
}
