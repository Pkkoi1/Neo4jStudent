package entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Department {
    @SerializedName("deptID")
    private String id;
    private String name;
    private String dean;
    private String building;
    private int room;

}
