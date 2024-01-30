package lk.ijse.dep11.to.requestTO;

import lk.ijse.dep11.util.LecturerType;
import lk.ijse.dep11.validations.LecturerImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerReqTO implements Serializable {
    private Integer id;

    @NotBlank(message = "Name cannot be Blank")
    @Pattern(regexp = "^[A-Za-z ]{2,}$",message = "Invalid name")
    private String name;

    @NotBlank(message = "designation cannot be empty")
    @Length(min = 3,message = "invalid designation")
    private String designation;

    @NotBlank(message = "qualifications cannot be empty")
    @Length(min = 3,message = "Invalid qualifications")
    private String qualifications;

    @NotNull(message = "type cannot be empty either full-time or visiting")
    private LecturerType type;

    @Null(groups = Create.class,message = "Display order should be empty when create")
    @NotNull(groups = Update.class,message = "Display order cannot be empty")
    @PositiveOrZero(groups = Update.class,message = "Display order should be zero or positive")
    private Integer displayOrder;

    @LecturerImage(maximumFileSize = 10*1024*1024,message = "file size is too large not file type not valid")
    private MultipartFile picture;

    @Pattern(regexp = "^http(s)://.+$",message = "no a valid linked in URL")
    private String linkedIn;

    public interface Create extends Default{}
    public interface Update extends Default{}

    public LecturerReqTO(String name, String designation, String qualifications, LecturerType type, Integer displayOrder, MultipartFile picture, String linkedIn) {
        this.name = name;
        this.designation = designation;
        this.qualifications = qualifications;
        this.type = type;
        this.displayOrder = displayOrder;
        this.picture = picture;
        this.linkedIn = linkedIn;
    }
}
