package toilet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ToiletRepositoryMockImpl implements ToiletRepository {

    private List<Toilet> toiletList;

    public ToiletRepositoryMockImpl(List<Toilet> toiletList) {
        this.toiletList = toiletList;
    }

    public ToiletRepositoryMockImpl(Toilet... toilets) {
        this.toiletList = Arrays.stream(toilets).collect(Collectors.toList());
    }

    @Override
    public List<Toilet> findAll() {
        return toiletList;
    }


}
