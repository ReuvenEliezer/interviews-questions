package toilet;

import java.util.UUID;

public class Toilet {

    UUID id; //Generated
    Location location;

    public Toilet(Location location) {
        this.location = location;
        this.id = UUID.randomUUID();
    }
}
