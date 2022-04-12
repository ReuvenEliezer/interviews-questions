package angelsense;

import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    private int age;
    private Name name;
    private String[] address;
    private List<Name> friends;

}
