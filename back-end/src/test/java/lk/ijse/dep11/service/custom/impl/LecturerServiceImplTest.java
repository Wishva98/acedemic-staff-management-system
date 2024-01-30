package lk.ijse.dep11.service.custom.impl;

import lk.ijse.dep11.entity.Lecturer;
import lk.ijse.dep11.entity.LinkedIn;
import lk.ijse.dep11.exception.AppException;
import lk.ijse.dep11.service.custom.LecturerService;
import lk.ijse.dep11.to.LecturerTO;
import lk.ijse.dep11.to.requestTO.LecturerReqTO;
import lk.ijse.dep11.util.LecturerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@SpringBootTest
@Transactional
class LecturerServiceImplTest {

    @Autowired
    private LecturerService lecturerService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveLecturer() {
        LecturerReqTO lecturerReqTO = new LecturerReqTO("Kasun sampath", "Lecturer", "Msc in IT",
                LecturerType.FULL_TIME,5, null, "http://www.linkedin.com/wishvanath");
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTO);
        System.out.println(lecturerTO.getLinkedIn());
        assertNotNull(lecturerTO.getId());
        assertTrue(lecturerTO.getId() > 0);
        assertEquals(lecturerReqTO.getName(), lecturerTO.getName());
        assertEquals(lecturerReqTO.getDesignation(), lecturerTO.getDesignation());
        assertEquals(lecturerReqTO.getQualifications(), lecturerTO.getQualifications());
        assertEquals(lecturerReqTO.getType(), lecturerTO.getType());
        assertEquals(lecturerReqTO.getDisplayOrder(), lecturerTO.getDisplayOrder());
        assumingThat(lecturerReqTO.getLinkedIn() != null, () -> assertEquals(lecturerReqTO.getLinkedIn(), lecturerTO.getLinkedIn()));
        assumingThat(lecturerReqTO.getLinkedIn() == null, () -> assertNull(lecturerTO.getLinkedIn()));
    }

    @Test
    void updateLecturerViaMultipart() {

    }

    @Test
    void updateLectureDetails() {
        LecturerReqTO lecturerReqTo = new LecturerReqTO("Amith",
                "Associate Lecturer", "BSc, MSc",
                LecturerType.VISITING, 5,
                null,
                "https://linkedin.com");
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTo);
        lecturerTO.setName("Nuwan");
        lecturerTO.setLinkedIn(null);
        lecturerService.updateLectureDetails(lecturerTO);
        LecturerTO lecturer = lecturerService.getLecturerDetails(lecturerTO.getId());
        assertEquals(lecturerTO, lecturer);
    }

    @Test
    void deleteLecturer() {
        LecturerReqTO lecturerReqTo = new LecturerReqTO("Amith",
                "Associate Lecturer", "BSc, MSc",
                LecturerType.VISITING, 5,
                null,
                "https://linkedin.com");
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTo);
        assertNotNull(lecturerTO);
        lecturerService.deleteLecturer(lecturerTO.getId());
        assertThrows(AppException.class, ()-> lecturerService.getLecturerDetails(lecturerTO.getId()));
    }

    @Test
    void getLecturerDetails() {
        LecturerReqTO lecturerReqTo = new LecturerReqTO("Amith",
                "Associate Lecturer", "BSc, MSc",
                LecturerType.VISITING, 5,
                null,
                "https://linkedin.com");
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTo);
        LecturerTO lecturerDetails = lecturerService.getLecturerDetails(lecturerTO.getId());
        assertNotNull(lecturerDetails);
        assertEquals(lecturerReqTo.getType(),lecturerTO.getType());
        assertNull(lecturerTO.getPicture());

    }

    @Test
    void getAllLecturers() {
        for (int i = 0; i < 10; i++) {
            LecturerReqTO lecturerReqTo = new LecturerReqTO("Amith",
                    "Associate Lecturer", "BSc, MSc",
                    i < 5 ? LecturerType.VISITING : LecturerType.FULL_TIME, 5,
                    null,
                    "https://linkedin.com");
            lecturerService.saveLecturer(lecturerReqTo);
        }
        assertTrue(lecturerService.getAllLecturers(null).size() >= 10);
        assertTrue(lecturerService.getAllLecturers(LecturerType.FULL_TIME).size() >= 5);
        assertTrue(lecturerService.getAllLecturers(LecturerType.VISITING).size() >= 5);
    }
}