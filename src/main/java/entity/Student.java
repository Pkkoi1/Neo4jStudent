package entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    @SerializedName("studentID")
    private int id;
    private String name;
    private Double gpa;

}
