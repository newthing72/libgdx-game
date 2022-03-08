package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.google.inject.Inject;
import common.Coordinates;
import entity.block.Block;
import entity.collision.EntityPoint;
import entity.collision.ground.GroundPoint;
import entity.collision.ground.GroundSensorPoint;
import entity.collision.ladder.LadderPoint;
import entity.collision.left.LeftSensorPoint;
import entity.collision.right.RightSensorPoint;

public class EntityBodyBuilder {

  @Inject
  public EntityBodyBuilder() {}

  public static Body createEntityBody(World world, Coordinates coordinates) {
    float center_x = -(Entity.coordinatesScale - Entity.staticWidth) / 2f;
    float center_y = -(Entity.coordinatesScale - Entity.staticHeight) / 2f;

    Body theBody;
    BodyDef bodyDef = new BodyDef();

    FixtureDef mainFixtureDef = new FixtureDef();
    PolygonShape mainShape = new PolygonShape();
    Fixture mainFixture;

    FixtureDef smoothFixtureDef = new FixtureDef();
    PolygonShape smoothShape = new PolygonShape();
    Fixture smoothFixture;

    FixtureDef jumpFixtureDef = new FixtureDef();
    PolygonShape jumpShape = new PolygonShape();
    Fixture jumpFixture;

    FixtureDef leftFixtureDef = new FixtureDef();
    PolygonShape leftShape = new PolygonShape();
    Fixture leftFixture;

    FixtureDef rightFixtureDef = new FixtureDef();
    PolygonShape rightShape = new PolygonShape();
    Fixture rightFixture;

    Filter filter = new Filter();
    filter.categoryBits = 1;
    filter.maskBits = 2;

    // create the body
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(
        coordinates.getXReal() * Entity.coordinatesScale,
        coordinates.getYReal() * Entity.coordinatesScale);
    theBody = world.createBody(bodyDef);
    theBody.setFixedRotation(true);

    // create the main
    mainShape.setAsBox(
        Entity.staticWidth / 2f, Entity.staticHeight / 2f, new Vector2(center_x, center_y + 2), 0);
    mainFixtureDef.shape = mainShape;
    mainFixtureDef.density = 1f;
    mainFixtureDef.restitution = 0;
    mainFixture = theBody.createFixture(mainFixtureDef);
    mainFixture.setFilterData(filter);
    mainFixture.setUserData(new EntityPoint(theBody));

    // create the smooth
    Vector2[] smoothVertices = new Vector2[3];
    float left = center_x - (Entity.staticWidth / 2f) - 1;
    float right = center_x + (Entity.staticWidth / 2f) + 1;
    float bottom = center_y - (Entity.staticHeight / 2f) - 1;
    smoothVertices[0] = new Vector2(left, center_y);
    smoothVertices[1] = new Vector2(right, center_y);
    smoothVertices[2] = new Vector2(center_x, bottom);
    smoothShape.set(smoothVertices);
    smoothFixtureDef.shape = smoothShape;
    smoothFixtureDef.density = 1f;
    smoothFixtureDef.restitution = 0;
    smoothFixture = theBody.createFixture(smoothFixtureDef);
    smoothFixture.setFilterData(filter);

    // create the jump
    jumpShape.setAsBox(
        Entity.staticWidth / 2f,
        5f,
        new Vector2(-Entity.staticWidth / 8f, -Entity.staticHeight / 2f - 3f),
        0);
    jumpFixtureDef.shape = jumpShape;
    jumpFixtureDef.isSensor = true;
    jumpFixture = theBody.createFixture(jumpFixtureDef);
    jumpFixture.setUserData(new GroundSensorPoint(theBody));

    // create the left
    leftShape.setAsBox(5f, Entity.staticHeight / 2f, new Vector2(-Entity.staticWidth / 2f, 0), 0);
    leftFixtureDef.shape = leftShape;
    leftFixtureDef.isSensor = true;
    leftFixture = theBody.createFixture(leftFixtureDef);
    leftFixture.setUserData(new LeftSensorPoint(theBody));

    // create the right
    rightShape.setAsBox(
        5f, Entity.staticHeight / 2f, new Vector2((Entity.staticWidth / 2f) - 5, 0), 0);
    rightFixtureDef.shape = rightShape;
    rightFixtureDef.isSensor = true;
    rightFixture = theBody.createFixture(rightFixtureDef);
    rightFixture.setUserData(new RightSensorPoint(theBody));

    return theBody;
  }

  public static Body createSolidBlockBody(World world, Coordinates coordinates) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(
        coordinates.getXReal() * Entity.coordinatesScale,
        coordinates.getYReal() * Entity.coordinatesScale);

    Body theBody = world.createBody(bodyDef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(Block.staticWidth / 2f, Block.staticHeight / 2f);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 0f;
    fixtureDef.restitution = 0;
    Fixture blockFixture = theBody.createFixture(fixtureDef);

    Filter filter = new Filter();
    filter.categoryBits = 2;
    filter.maskBits = 1;
    blockFixture.setFilterData(filter);

    blockFixture.setUserData(new GroundPoint());
    return theBody;
  }

  public static Body createEmptyBlockBody() {
    return null;
  }

  public static Body createEmptyLadderBody(World world, Coordinates coordinates) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(
        coordinates.getXReal() * Entity.coordinatesScale,
        coordinates.getYReal() * Entity.coordinatesScale);

    Body theBody = world.createBody(bodyDef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(Block.staticWidth / 2.0f, Block.staticHeight / 2f);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 0f;
    fixtureDef.restitution = 0;
    fixtureDef.isSensor = true;
    Fixture blockFixture = theBody.createFixture(fixtureDef);

    Filter filter = new Filter();
    filter.categoryBits = 2;
    filter.maskBits = 1;

    blockFixture.setFilterData(filter);
    blockFixture.setUserData(new LadderPoint());

    return theBody;
  }
}
