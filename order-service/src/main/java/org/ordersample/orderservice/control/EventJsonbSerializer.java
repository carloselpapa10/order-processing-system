package org.ordersample.orderservice.control;

import org.ordersample.domaininfo.AppEvent;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class EventJsonbSerializer implements JsonbSerializer<AppEvent> {
    @Override
    public void serialize(AppEvent event, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartObject();
        generator.write("class", event.getClass().getCanonicalName());
        ctx.serialize("data", event, generator);
        generator.writeEnd();
        generator.close();
    }
}
