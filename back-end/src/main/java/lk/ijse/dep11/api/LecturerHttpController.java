package lk.ijse.dep11.api;

import lk.ijse.dep11.to.requestTO.LecturerReqTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;

@RestController
@CrossOrigin
@RequestMapping("api/v1/lecturers")
public class LecturerHttpController {


    @GetMapping
    public void getAllLecturers(){
        System.out.println("getAllLecturers");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{lecturer-id}")
    public void getLecturerById(@PathVariable("lecturer-id") Integer id){
        System.out.println("getLecturerById "+ id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "type=full-time")
    public void getFullTimeLecturers(){
        System.out.println("getFullTimeLecturers");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "type=part-time")
    public void getPartTimeLecturers(){
        System.out.println("getPartTimeLecturers");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewLecturer(@ModelAttribute LecturerReqTO lecturerReqTO){
        System.out.println(lecturerReqTO.getName());
        System.out.println(lecturerReqTO);
        System.out.println("createNewLecturer()");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerByMultipart(@PathVariable("lecturer-id") Integer id) {
        System.out.println("updateLecturerByMultipart " + id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "application/json")
    public void updateLecturerByJson(@PathVariable("lecturer-id") Integer id) {
        System.out.println("updateLecturerByJson " + id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{lecturer-id}")
    public void deleteLecturer(@PathVariable("lecturer-id") Integer id){
        System.out.println("deleteLecturerById() " + id);
    }
}
