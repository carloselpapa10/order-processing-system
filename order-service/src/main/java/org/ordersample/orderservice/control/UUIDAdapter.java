package org.ordersample.orderservice.control;

import javax.json.bind.adapter.JsonbAdapter;
import java.util.UUID;

public class UUIDAdapter implements JsonbAdapter<UUID, String> {

    @Override
    public String adaptToJson(UUID uuid) {
        return uuid.toString();
    }

    @Override
    public UUID adaptFromJson(String string) {
        return UUID.fromString(string);
    }

}
