package com.interviews.questions.toilet;

import java.util.*;

public class ToiletService {
    private List<com.interviews.questions.toilet.Toilet> toilets;
    private com.interviews.questions.toilet.ToiletRepository toiletRepository;

    ToiletService(ToiletRepository toiletRepository) {
        this.toiletRepository = toiletRepository;
        this.toilets = toiletRepository.findAll();
    }

    public com.interviews.questions.toilet.Toilet calcNearestLocation(Location location) {
        if (toilets.isEmpty())
            return null;
        Map<com.interviews.questions.toilet.Toilet, Double> toiletToDistanceMap = new HashMap<>();
        for (Toilet toilet : toilets) {
            double distance = calcDistance(location.lat, toilet.location.lat, location.along, toilet.location.along);
            toiletToDistanceMap.put(toilet, distance);
        }
        return Collections.min(toiletToDistanceMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private double calcDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}
