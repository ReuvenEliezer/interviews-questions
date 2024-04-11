package com.interviews.questions.java8features;

public class CreateProcessor extends AbstractConsumerService<com.interviews.questions.java8features.CreateRequest> {

    public CreateProcessor() {
        add(RequestType.CREATE, request -> processReq(request));
    }

    private Boolean processReq(CreateRequest request) {
        String metaData = request.getMetaData();
        return Boolean.TRUE;
    }

}