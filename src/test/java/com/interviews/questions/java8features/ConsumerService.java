package com.interviews.questions.java8features;

public interface ConsumerService<T extends Request> {
    Boolean process(T request);

}
