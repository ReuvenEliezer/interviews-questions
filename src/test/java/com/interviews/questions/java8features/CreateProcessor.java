package com.interviews.questions.java8features;

public class CreateProcessor extends AbstractConsumerService<CreateRequest> {

    public CreateProcessor() {
        add(RequestType.CREATE, this::processReq);
    }

    private Boolean processReq(CreateRequest request) {
        String metaData = request.getMetaData();
        return Boolean.TRUE;
    }

}
