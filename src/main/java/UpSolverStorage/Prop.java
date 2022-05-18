package UpSolverStorage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
class Prop {
    private String name;
    @Setter(AccessLevel.NONE)
    private LocalDateTime createDateTime;
    private LocalDateTime updatedDateTime;

    public Prop(String name) {
        this.name = name;
        this.createDateTime = LocalDateTime.now();
        this.updatedDateTime = this.createDateTime;
    }
}