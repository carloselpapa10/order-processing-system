package org.ordersample.orderservice.model;

import io.karengryg.Movie;
import io.karengryg.User;
import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {

    private Movie movie;
    private User user;

    public OrderEvent(Object source) {
        super(source);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
