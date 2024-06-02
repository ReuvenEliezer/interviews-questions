package com.interviews.questions.toilet;

import java.util.List;

public class ToiletRepositoryImpl implements ToiletRepository {
    @Override
    public List<Toilet> findAll() {
        return List.of(new Toilet(new Location(33d, 34d)),
                new Toilet(new Location(34d, 34d)),
                new Toilet(new Location(34d, 35d))
        );
    }

}
