package angelsense;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
class Node {
    int value;
    Node next;
    List<String> stringList;

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, List<String> stringList) {
        this.value = value;
        this.stringList = stringList;
    }

}
