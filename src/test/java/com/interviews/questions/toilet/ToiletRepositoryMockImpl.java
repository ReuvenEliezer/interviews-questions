package com.interviews.questions.toilet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ToiletRepositoryMockImpl implements ToiletRepository {

    private final List<Toilet> toiletList;

    public ToiletRepositoryMockImpl(List<Toilet> toiletList) {
        this.toiletList = toiletList;
    }

    public ToiletRepositoryMockImpl(Toilet... toilets) {
        this.toiletList = Arrays.stream(toilets).toList();
    }

    @Override
    public List<Toilet> findAll() {
        return toiletList;
    }


}
