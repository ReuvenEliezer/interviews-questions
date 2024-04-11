package com.interviews.questions.toilet;

import java.util.UUID;

public class Toilet {

    UUID id; //Generated
    com.interviews.questions.toilet.Location location;

    public Toilet(Location location) {
        this.location = location;
        this.id = UUID.randomUUID();
    }
}
