package entity.controllers;

import app.GameController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import common.Direction;
import common.events.EventService;
import common.exceptions.EntityNotFound;
import entity.Entity;
import entity.block.DirtBlock;
import entity.block.SkyBlock;
import entity.controllers.actions.EntityActionFactory;
import networking.events.EventTypeFactory;

import java.util.logging.Logger;

public class EntityUserController extends EntityController {

    private final static Logger LOGGER = Logger.getLogger(EntityUserController.class.getName());

    public EntityUserController(
            GameController gameController,
            EntityActionFactory entityActionFactory,
            EventService eventService,
            EventTypeFactory eventTypeFactory,
            Entity entity) {
        super(gameController, entityActionFactory, eventService, eventTypeFactory, entity);
    }

    @Override
    public void beforeWorldUpdate() {
        Body body = this.entity.getBody();
        float impulse = body.getMass() * 10;
        try {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    this.gameController.placeBlock(this.entity, Direction.LEFT, SkyBlock.class);
                } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    this.gameController.placeBlock(this.entity, Direction.RIGHT, SkyBlock.class);
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    this.gameController.placeBlock(this.entity, Direction.DOWN, SkyBlock.class);
                } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    this.gameController.placeBlock(this.entity, Direction.UP, SkyBlock.class);
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    this.gameController.placeBlock(this.entity, Direction.LEFT, DirtBlock.class);
                } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    this.gameController.placeBlock(this.entity, Direction.RIGHT, DirtBlock.class);
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    this.gameController.placeBlock(this.entity, Direction.DOWN, DirtBlock.class);
                } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    this.gameController.placeBlock(this.entity, Direction.UP, DirtBlock.class);
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    this.gameController.createLadder(this.entity.coordinates.getMiddle().getLeft().getBase());
                } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    this.gameController.createLadder(this.entity.coordinates.getMiddle().getRight().getBase());
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    this.gameController.createLadder(this.entity.coordinates.getMiddle().getDown().getBase());
                } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    this.gameController.createLadder(this.entity.coordinates.getMiddle().getUp().getBase());
                }
            }
        } catch (EntityNotFound e) {
            e.printStackTrace();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            gameController.createAI(this.entity.uuid);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (this.getAction("climbUp").isValid(body)) {
                this.applyAction("climbUp", body);
            } else if (this.getAction("jump").isValid(body)) {
                this.applyAction("jump", body);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (this.getAction("climbDown").isValid(body)) {
                this.applyAction("climbDown", body);
            } else {
                this.applyAction("stop", body);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.applyAction("left", body);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.applyAction("right", body);
        }
    }
}
