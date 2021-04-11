public interface IRandomNumberGenerator {
    boolean insert(int value);

    boolean remove(int value);

    int getRandom();
}
