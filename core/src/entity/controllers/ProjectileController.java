package entity.controllers;

import app.GameController;
import common.GameSettings;
import common.events.EventService;
import entity.Entity;
import entity.attributes.Coordinates;
import entity.collision.projectile.ProjectileContact;
import entity.controllers.actions.EntityActionFactory;
import networking.events.EventTypeFactory;

public class ProjectileController extends EntityController {

  Coordinates startPosition;
  ProjectileContact projectileContact;
  float distanceRange;

  public ProjectileController(
      GameController gameController,
      EntityActionFactory entityActionFactory,
      EventService eventService,
      EventTypeFactory eventTypeFactory,
      Entity entity,
      ProjectileContact projectileContact,
      Coordinates startPosition,
      float distanceRange) {
    super(gameController, entityActionFactory, eventService, eventTypeFactory, entity);
    this.startPosition = startPosition;
    this.distanceRange = distanceRange;
    this.projectileContact = projectileContact;
  }

  @Override
  public void beforeWorldUpdate() throws Exception {
    super.beforeWorldUpdate();
  }

  @Override
  public void afterWorldUpdate() throws Exception {
    Coordinates moveTo =
        new Coordinates(
            this.entity.getBodyPosition().x / GameSettings.PHYSICS_SCALE,
            this.entity.getBodyPosition().y / GameSettings.PHYSICS_SCALE);
    // if distance traveled goes over max. destroy it
    if (moveTo.calcDistance(this.startPosition) > distanceRange) {
      gameController.removeEntity(this.entity.getUuid());
      return;
    }
    if (projectileContact.isCollision(this.entity.getUuid())) {

      gameController.removeEntity(this.entity.getUuid());
    }
    if (!this.entity.coordinates.equals(moveTo))
      gameController.moveEntity(this.entity.getUuid(), moveTo);
  }
}
