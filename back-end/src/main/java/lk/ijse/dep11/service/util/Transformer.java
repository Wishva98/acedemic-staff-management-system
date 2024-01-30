package lk.ijse.dep11.service.util;

import lk.ijse.dep11.entity.Lecturer;
import lk.ijse.dep11.entity.LinkedIn;
import lk.ijse.dep11.entity.Picture;
import lk.ijse.dep11.to.LecturerTO;
import lk.ijse.dep11.to.requestTO.LecturerReqTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Transformer {
    private final ModelMapper mapper;

    public Transformer(ModelMapper mapper) {
        this.mapper = mapper;

        mapper.typeMap(String.class,LinkedIn.class).setConverter(ctx-> ctx.getSource() !=null ? new LinkedIn(null,ctx.getSource()):null);
        mapper.typeMap(MultipartFile.class, Picture.class).setConverter(ctx->null);
        mapper.typeMap(LinkedIn.class, String.class).setConverter(ctx->ctx.getSource() != null? ctx.getSource().getUrl() :null);
    }

    public Lecturer toLecturer(LecturerReqTO lecturerReqTO){
        Lecturer lecturer = mapper.map(lecturerReqTO, Lecturer.class);
        if (lecturerReqTO.getLinkedIn() != null) lecturer.getLinkedIn().setLecturer(lecturer);
        return lecturer;
    }

    public LecturerTO fromLecturer(Lecturer lecturer){
        LecturerTO lecturerTO = mapper.map(lecturer, LecturerTO.class);
        return lecturerTO;
    }

    public Lecturer fromLecturerTO(LecturerTO lecturerTO){
        return mapper.map(lecturerTO, Lecturer.class);
    }
}
