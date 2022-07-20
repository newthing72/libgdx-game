package core.entity.controllers;

import core.app.game.GameController;
import core.common.GameSettings;
import core.common.GameStore;
import core.common.events.EventService;
import core.entity.Entity;
import core.entity.attributes.inventory.FullBagException;
import core.entity.attributes.inventory.item.OrbInventoryItem;
import core.entity.attributes.msc.Coordinates;
import core.entity.collision.orb.OrbContact;
import core.entity.controllers.actions.EntityActionFactory;
import core.entity.misc.Orb;
import core.networking.events.EventTypeFactory;
import java.util.UUID;

public class OrbController extends EntityController {
  GameStore gameStore;
  OrbContact orbContact;

  public OrbController(
      GameController gameController,
      EntityActionFactory entityActionFactory,
      EventService eventService,
      EventTypeFactory eventTypeFactory,
      GameStore gameStore,
      OrbContact orbContact,
      Entity entity) {
    super(gameController, entityActionFactory, eventService, eventTypeFactory, entity);
    this.orbContact = orbContact;
    this.gameStore = gameStore;
  }

  @Override
  public void afterWorldUpdate() throws Exception {

    if (orbContact.isCollision(this.entity.getUuid())) {
      for (UUID contact : orbContact.getCollisions(this.entity.getUuid())) {
        Entity entity = gameStore.getEntity(contact);
        if (entity.getBag().freeSpace() > 0) {
          try {
            entity.getBag().appendItem(new OrbInventoryItem(0));
          } catch (FullBagException e) {
            continue;
          }
          gameController.removeEntity(this.entity.getUuid());
          return;
        }
      }
      return;
    }

    if (!gameStore.doesChunkExist(entity.getChunk().chunkRange.getDown())) {
      /* If the chunk below doesn't exist. Don't move down. It could cause a problem */
      entity.setBodyPosition(entity.coordinates.toPhysicsVector2());
      return;
    }

    Coordinates moveTo =
        new Coordinates(
            this.entity.getBodyPosition().x / GameSettings.PHYSICS_SCALE,
            this.entity.getBodyPosition().y / GameSettings.PHYSICS_SCALE);
    if (!this.entity.coordinates.equals(moveTo)) {
      gameController.moveEntity(this.entity.getUuid(), moveTo);
      ((Orb) this.entity).needsUpdate();
    }
  }
}