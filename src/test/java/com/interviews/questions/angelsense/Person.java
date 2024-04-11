package com.interviews.questions.angelsense;

import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    private int age;
    private com.interviews.questions.angelsense.Name name;
    private String[] address;
    private List<Name> friends;

}
