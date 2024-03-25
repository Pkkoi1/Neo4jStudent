package entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class enrollment {
    private Course  course;
    private Student student;
}
