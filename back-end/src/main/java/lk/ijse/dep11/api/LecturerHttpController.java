package lk.ijse.dep11.api;

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

    @GetMapping(value = "/{lecturer-id}")
    public void getLecturerById(@PathVariable("lecturer-id") Integer id){
        System.out.println("getLecturerById "+ id);
    }

    @GetMapping(params = "type=full-time")
    public void getFullTimeLecturers(){
        System.out.println("getFullTimeLecturers");
    }

    @GetMapping(params = "type=part-time")
    public void getPartTimeLecturers(){
        System.out.println("getPartTimeLecturers");
    }

    @PostMapping
    public void createNewLecturer(){
        System.out.println("createNewLecturer()");
    }

    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerByMultipart(@PathVariable("lecturer-id") Integer id) {
        System.out.println("updateLecturerByMultipart " + id);
    }

    @PatchMapping(value = "/{lecturer-id}", consumes = "application/json")
    public void updateLecturerByJson(@PathVariable("lecturer-id") Integer id) {
        System.out.println("updateLecturerByJson " + id);
    }

    @DeleteMapping(value = "/{lecturer-id}")
    public void deleteLecturer(@PathVariable("lecturer-id") Integer id){
        System.out.println("deleteLecturerById() " + id);
    }
}
