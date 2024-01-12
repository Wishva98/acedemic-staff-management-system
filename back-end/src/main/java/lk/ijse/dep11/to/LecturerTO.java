package lk.ijse.dep11.to;

import lk.ijse.dep11.util.LecturerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerTO {
    private Integer id;
    private String name;
    private String designation;
    private String qualifications;
    private LecturerType type;
    private Integer displayOrder;
    private String picture;
    private String linkedIn;
}
