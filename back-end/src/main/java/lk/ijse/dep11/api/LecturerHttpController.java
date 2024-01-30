package lk.ijse.dep11.api;

import lk.ijse.dep11.service.custom.LecturerService;
import lk.ijse.dep11.to.LecturerTO;
import lk.ijse.dep11.to.requestTO.LecturerReqTO;
import lk.ijse.dep11.util.LecturerType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/lecturers")
public class LecturerHttpController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LecturerService lecturerService;

    @GetMapping
    public List<LecturerTO> getAllLecturers(){
        return lecturerService.getAllLecturers(null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{lecturer-id}")
    public LecturerTO getLecturerById(@PathVariable("lecturer-id") Integer id){
        return lecturerService.getLecturerDetails(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "type=full-time")
    public List<LecturerTO> getFullTimeLecturers(){
        return lecturerService.getAllLecturers(LecturerType.FULL_TIME);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "type=part-time")
    public List<LecturerTO> getPartTimeLecturers(){
        return lecturerService.getAllLecturers(LecturerType.VISITING);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data",produces = "application/json")
    public LecturerTO createNewLecturer(@ModelAttribute  @Validated(LecturerReqTO.Create.class)LecturerReqTO lecturerReqTO){
        return lecturerService.saveLecturer(lecturerReqTO);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerByMultipart(@PathVariable("lecturer-id") Integer id,
                                          @ModelAttribute @Validated(LecturerReqTO.Update.class) LecturerReqTO lecturerReqTO) {
        System.out.println(id);
        lecturerReqTO.setId(id);
        lecturerService.updateLecturerViaMultipart(lecturerReqTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "application/json")
    public void updateLecturerByJson(@PathVariable("lecturer-id") Integer id, @RequestBody @Validated(LecturerReqTO.Update.class) LecturerTO lecturerTO) {
        lecturerTO.setId(id);
        lecturerService.updateLectureDetails(lecturerTO);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{lecturer-id}")
    public void deleteLecturer(@PathVariable("lecturer-id") Integer id){
        lecturerService.deleteLecturer(id);
    }
}
