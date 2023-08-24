package java8features;

public interface ConsumerService<T extends Request> {
    Boolean process(T request);

}
