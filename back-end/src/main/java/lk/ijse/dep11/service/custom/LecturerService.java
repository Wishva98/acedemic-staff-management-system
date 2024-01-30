package lk.ijse.dep11.service.custom;

import lk.ijse.dep11.service.SuperService;
import lk.ijse.dep11.to.LecturerTO;
import lk.ijse.dep11.to.requestTO.LecturerReqTO;
import lk.ijse.dep11.util.LecturerType;

import java.util.List;

public interface LecturerService extends SuperService {
    LecturerTO  saveLecturer(LecturerReqTO lecturerReqTO);
    void updateLecturerViaMultipart(LecturerReqTO lecturerReqTO);
    void updateLectureDetails(LecturerTO lecturerTO);
    void deleteLecturer(Integer id);
    LecturerTO getLecturerDetails(Integer id);
    List<LecturerTO> getAllLecturers(LecturerType lecturerType);

}
