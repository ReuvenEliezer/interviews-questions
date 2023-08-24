package java8features;

import java.util.UUID;

public abstract class Request {

    private UUID id;
    private RequestType requestType;

    public Request(RequestType requestType) {
        this.id = UUID.randomUUID();
        this.requestType = requestType;
    }

    public UUID getId() {
        return id;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
