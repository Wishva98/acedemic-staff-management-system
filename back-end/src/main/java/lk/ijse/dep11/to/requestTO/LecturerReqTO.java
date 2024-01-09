package lk.ijse.dep11.to.requestTO;

import lk.ijse.dep11.util.LecturerType;
import lk.ijse.dep11.validations.LecturerImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerReqTO implements Serializable {
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
    private Integer displayOrder;

    @LecturerImage
    private MultipartFile picture;

    @Pattern(regexp = "^http://[a-z.0-9A-Z]{2,}$")
    private String linkedIn;

    public interface Create extends Default{}
    public interface Update extends Default{}
}
