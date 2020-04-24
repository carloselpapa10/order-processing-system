package org.ordersample.orderservice.control;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.ordersample.domaininfo.AppEvent;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.logging.Logger;

public class EventDeserializer implements Deserializer<AppEvent> {

    private static final Logger logger = Logger.getLogger(EventDeserializer.class.getName());

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to configure
    }

    @Override
    public AppEvent deserialize(String s, byte[] bytes) {
        return null;
    }

    @Override
    public AppEvent deserialize(String topic, Headers headers, byte[] data) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
            final JsonObject jsonObject = Json.createReader(input).readObject();
            final Class<? extends AppEvent> eventClass = (Class<? extends AppEvent>) Class.forName(jsonObject.getString("class"));
            return eventClass.getConstructor(JsonObject.class).newInstance(jsonObject.getJsonObject("data"));
        } catch (Exception e) {
            logger.severe("Could not deserialize event: " + e.getMessage());
            throw new SerializationException("Could not deserialize event", e);
        }
    }

    @Override
    public void close() {

    }
}
