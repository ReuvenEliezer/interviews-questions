package com.interviews.questions.angelsense;

import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Name {
    private String firstName;
    private String lastName;
    private List<String> stringList;

}
