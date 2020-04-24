package org.ordersample.domaininfo;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Objects;

public abstract class AppEvent extends ApplicationEvent {

    private final Instant instant;

    public AppEvent() {
        super(null);
        instant = Instant.now();
    }

    public AppEvent(Object source) {
        super(source);
        instant = Instant.now();
    }

    protected AppEvent(Object source, Instant instant) {
        super(source);
        this.instant = instant;
    }

    public Instant getInstant() {
        return instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppEvent appEvent = (AppEvent) o;
        return Objects.equals(instant, appEvent.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant);
    }
}
