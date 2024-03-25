package entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    @SerializedName("courseID")
    private String courseID;

    private String name;
    private int hours;


}
