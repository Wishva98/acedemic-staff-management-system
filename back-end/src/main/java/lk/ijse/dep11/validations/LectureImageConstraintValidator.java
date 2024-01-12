package lk.ijse.dep11.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LectureImageConstraintValidator implements ConstraintValidator<LecturerImage, MultipartFile> {

    private long maximumFileSize;
    @Override
    public void initialize(LecturerImage constraintAnnotation) {
        maximumFileSize = constraintAnnotation.maximumFileSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        //because picture can be null or empty
        if (multipartFile ==null || multipartFile.isEmpty()) return true;
        if (multipartFile.getContentType() == null || !multipartFile.getContentType().startsWith("image/")) return false;
        return multipartFile.getSize() <= maximumFileSize;
    }
}
