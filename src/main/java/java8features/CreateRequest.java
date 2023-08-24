package java8features;

public class CreateRequest extends Request {

    private String metaData;


    public CreateRequest(String metaData) {
        super(RequestType.CREATE);
        this.metaData = metaData;
    }

    public String getMetaData() {
        return metaData;
    }
}
