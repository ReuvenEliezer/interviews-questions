import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListRemove {

    public static void listRemoveAll(List<String> list, String removeMe) {
        //throw ConcurrentModificationException (because we remove while iterating of the list) and On^2 (for each + remove element from list)
        for (String s : list) {
            if (s.equals(removeMe)) {
                list.remove(s);
            }
        }
    }

    public static void listRemoveAll2(List<String> list, String removeMe) {
       // throw ArrayIndexOutOfBoundsException
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).equals(removeMe)) {
                list.remove(i);
            }
        }
    }

    public static void listRemoveAll3(List<String> list, String removeMe) {
        //if the 2 elements tobe removed is adjacent to each other -> the list.remove(index) is remove first instance and set the next value in the current index, now the counter ++ > go to the next index and missed the next value
        //for example the list is of A, A, B  -> we want to remove A element -> the list.remove(index) -> remove the first A and short the list from size of 3 to 2, now the first element is the second instance of A, and counter doing ++ and the next foreach check the last index the missed the current.
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(removeMe)) {
                list.remove(i);
            }
        }
    }

    public static void listRemoveAll4(List<String> list, String removeMe) {
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String s = iterator.next();
            if (s.equals(removeMe)) {
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        List<String> letters = new ArrayList<>();
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("A");
        letters.add("B");
        letters.add("E");
        letters.add("E");


        ListRemove.listRemoveAll(letters, "A");
        System.out.println(letters);

        ListRemove.listRemoveAll2(letters, "A");
        System.out.println(letters);

        ListRemove.listRemoveAll3(letters, "E");
        System.out.println(letters);

        ListRemove.listRemoveAll4(letters, "A");
        System.out.println(letters);

    }
}


