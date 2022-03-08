package networking.translation;

import common.Coordinates;
import common.events.types.CreateAIEntityEventType;
import common.exceptions.SerializationDataMissing;
import networking.events.EventTypeFactory;
import org.junit.Test;

import java.util.UUID;

public class TranslateCreateAIEvent {

  @Test
  public void testTranslateCreateEntityEvent() throws SerializationDataMissing {
    Coordinates coordinates = new Coordinates(0, 1);
    UUID uuid = UUID.randomUUID();

    CreateAIEntityEventType outgoing = EventTypeFactory.createAIEntityEventType(coordinates, uuid);
    CreateAIEntityEventType incoming =
        NetworkDataDeserializer.createCreateAIEntityEventType(
            NetworkDataSerializer.createCreateAIEntityEventType(outgoing));

    assert incoming.getTarget().equals(outgoing.getTarget());
    assert incoming.getCoordinates().equals(outgoing.getCoordinates());
  }
}
