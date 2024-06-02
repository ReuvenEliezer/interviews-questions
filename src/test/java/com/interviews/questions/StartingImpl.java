package com.interviews.questions;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Service;

@Service
public class StartingImpl implements Starting {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        int a = 0;
    }
}
