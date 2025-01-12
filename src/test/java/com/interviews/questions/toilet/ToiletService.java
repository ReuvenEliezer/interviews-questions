package com.interviews.questions.toilet;


public class ToiletService {

    private final ToiletRepository toiletRepository;

    public ToiletService(ToiletRepository toiletRepository) {
        this.toiletRepository = toiletRepository;
    }

    public Toilet calcNearestLocation(Location sourceLocation) {
        Toilet toiletResult = null;
        double distanceResult = 0;
        for (Toilet toilet : toiletRepository.findAll()) {
            double distance = calcDistance(sourceLocation, toilet.location);
            if (toiletResult == null || distance < distanceResult) {
                toiletResult = toilet;
                distanceResult = distance;
            }
        }
        return toiletResult;
//        Map<Toilet, Double> toiletToDistanceMap = new HashMap<>();
//        for (Toilet toilet : toilets) {
//            double distance = calcDistance(location, toilet.location);
//            toiletToDistanceMap.put(toilet, distance);
//        }
//        return Collections.min(toiletToDistanceMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private double calcDistance(Location sourceLocation, Location distanationLocation) {
        return Math.sqrt(Math.pow(sourceLocation.lat - distanationLocation.lat, 2) + Math.pow(sourceLocation.along - distanationLocation.along, 2));
    }

}
