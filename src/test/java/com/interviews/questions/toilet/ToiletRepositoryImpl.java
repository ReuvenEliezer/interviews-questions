package com.interviews.questions.toilet;

import java.util.List;

public class ToiletRepositoryImpl implements ToiletRepository {
    @Override
    public List<com.interviews.questions.toilet.Toilet> findAll() {
        return List.of(new com.interviews.questions.toilet.Toilet(new com.interviews.questions.toilet.Location(33d, 34d)),
                new com.interviews.questions.toilet.Toilet(new com.interviews.questions.toilet.Location(34d, 34d)),
                new Toilet(new Location(34d, 35d))
        );
    }

}
