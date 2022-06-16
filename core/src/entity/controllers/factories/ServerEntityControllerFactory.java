package entity.controllers.factories;

import com.google.inject.Inject;
import entity.Entity;
import entity.controllers.EntityController;
import entity.controllers.events.consumers.ChangedHealthConsumer;
import entity.controllers.events.consumers.FallDamageConsumer;
import entity.controllers.events.types.ChangeHealthEventType;
import entity.controllers.events.types.FallDamageEventType;

public class ServerEntityControllerFactory extends EntityControllerFactory {
  @Inject FallDamageConsumer fallDamageConsumer;
  @Inject ChangedHealthConsumer changeHealthConsumer;

  @Override
  public EntityController createEntityUserController(Entity entity) {
    return super.createEntityUserController(entity)
        .registerEntityEventConsumer(FallDamageEventType.type, fallDamageConsumer)
        .registerEntityEventConsumer(ChangeHealthEventType.type, changeHealthConsumer);
  }

  @Override
  public EntityController createEntityPathController(Entity source, Entity target) {
    return super.createEntityPathController(source, target)
        .registerEntityEventConsumer(FallDamageEventType.type, fallDamageConsumer)
        .registerEntityEventConsumer(ChangeHealthEventType.type, changeHealthConsumer);
  }

  @Override
  public EntityController createRemoteBodyController(Entity entity) {
    return super.createRemoteBodyController(entity)
        .registerEntityEventConsumer(FallDamageEventType.type, fallDamageConsumer)
        .registerEntityEventConsumer(ChangeHealthEventType.type, changeHealthConsumer);
  }
}
