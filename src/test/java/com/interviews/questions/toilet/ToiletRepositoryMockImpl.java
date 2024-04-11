package com.interviews.questions.toilet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ToiletRepositoryMockImpl implements ToiletRepository {

    private List<com.interviews.questions.toilet.Toilet> toiletList;

    public ToiletRepositoryMockImpl(List<com.interviews.questions.toilet.Toilet> toiletList) {
        this.toiletList = toiletList;
    }

    public ToiletRepositoryMockImpl(com.interviews.questions.toilet.Toilet... toilets) {
        this.toiletList = Arrays.stream(toilets).collect(Collectors.toList());
    }

    @Override
    public List<Toilet> findAll() {
        return toiletList;
    }


}
