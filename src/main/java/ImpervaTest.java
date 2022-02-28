import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImpervaTest {

    @Test
    public void imprevaTest() {
        mapCom mapCom = new mapComImpl();
//        mapCom mapCom = new mapComImplLocalDate();
        mapCom.set(6, 20);
        mapCom.set(14, 25);
        mapCom.set(6, 25);
        mapCom.setAll(1);
        mapCom.setAll(17);
        mapCom.set(14, 13);

        int i1 = mapCom.get(14);
        int i2 = mapCom.get(6);
        int i3 = mapCom.get(3);
    }


    interface mapCom {
        int get(int index);

        void set(int index, int value);

        void setAll(int value);
    }

//    set(6,20)
//    set(14,25)
//    set(6,25)
//    setAll(1)
//    setAll(17)
//    set(14,13)
//
//    Will give the following output:
//    get(14) → 13
//    get(6) → 17
//    get(3) → 17


    class mapComImpl implements mapCom {

        Map<Integer, Integer> map = new HashMap<>();
        Integer value = null;

        @Override
        public int get(int index) {
            Integer value = map.get(index);
            if (value == null)
                return this.value;
            return value;
        }

        @Override
        public void set(int index, int value) {
            map.put(index, value);
        }

        @Override
        public void setAll(int value) {
            map.clear();
            this.value = value;
        }
    }


    class ValueLastUpdatedDateTime {
        LocalDateTime localDateTime;
        Integer value;

        public ValueLastUpdatedDateTime(LocalDateTime localDateTime, Integer value) {
            this.localDateTime = localDateTime;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ValueLastUpdatedDateTime that = (ValueLastUpdatedDateTime) o;
            return value == that.value &&
                    Objects.equals(localDateTime, that.localDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(localDateTime, value);
        }
    }

    class mapComImplLocalDate implements mapCom {

        Map<Integer, ValueLastUpdatedDateTime> map = new HashMap<>();
        ValueLastUpdatedDateTime globalValueDateTime = new ValueLastUpdatedDateTime(LocalDateTime.now(), null);

        @Override
        public int get(int index) {
            ValueLastUpdatedDateTime lastUpdatedDateTime = map.get(index);

            if (lastUpdatedDateTime != null) {
                if (lastUpdatedDateTime.localDateTime.isAfter(this.globalValueDateTime.localDateTime)) {
                    return lastUpdatedDateTime.value;
                }
            }
            return this.globalValueDateTime.value;
        }

        @Override
        public void set(int index, int value) {
            map.put(index, new ValueLastUpdatedDateTime(LocalDateTime.now(), value));
        }

        @Override
        public void setAll(int value) {
            this.globalValueDateTime.localDateTime = LocalDateTime.now();
            this.globalValueDateTime.value = value;
        }
    }
}
