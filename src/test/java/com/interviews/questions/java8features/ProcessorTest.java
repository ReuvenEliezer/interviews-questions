package com.interviews.questions.java8features;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ProcessorTest {

    @Test
    public void test(){
        CreateRequest createRequest = new CreateRequest("createReq");
        CreateProcessor consumerService = new CreateProcessor();
        Boolean result = consumerService.process(createRequest);
        Assertions.assertThat(result).isTrue();
    }
}
