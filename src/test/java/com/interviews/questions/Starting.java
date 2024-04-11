package com.interviews.questions;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class Starting implements IStarting {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        int a = 0;
    }
}
