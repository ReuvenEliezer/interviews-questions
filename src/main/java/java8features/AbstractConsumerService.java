package java8features;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractConsumerService<T extends Request> implements ConsumerService<T> {

    private Map<RequestType, Function<T, Boolean>> requestTypeMap = new HashMap<>();

    @Override
    public Boolean process(T request) {
        Function<T, Boolean> requestBooleanFunction = requestTypeMap.get(request.getRequestType());
        if (requestBooleanFunction == null){
            throw new UnsupportedOperationException(String.format("RequestType %s not supported", request.getRequestType()));
        }
        return requestBooleanFunction.apply(request);
    }

    protected void add(RequestType requestType, Function<T, Boolean> requestBooleanFunction){
        requestTypeMap.put(requestType, requestBooleanFunction);
    }
}
