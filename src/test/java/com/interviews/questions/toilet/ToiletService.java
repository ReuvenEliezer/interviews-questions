package com.interviews.questions.toilet;

import java.util.*;

public class ToiletService {
    private final List<Toilet> toilets;
    private final ToiletRepository toiletRepository;

    ToiletService(ToiletRepository toiletRepository) {
        this.toiletRepository = toiletRepository;
        this.toilets = toiletRepository.findAll();
    }

    public Toilet calcNearestLocation(Location location) {
        if (toilets.isEmpty())
            return null;
        Map<Toilet, Double> toiletToDistanceMap = new HashMap<>();
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
