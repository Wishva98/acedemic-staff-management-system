package lk.ijse.dep11.service.util;

import lk.ijse.dep11.entity.Lecturer;
import lk.ijse.dep11.entity.LinkedIn;
import lk.ijse.dep11.to.LecturerTO;
import lk.ijse.dep11.to.requestTO.LecturerReqTO;
import lk.ijse.dep11.util.LecturerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TransformerTest {

    @Autowired
    private Transformer transformer;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void toLecturer() {
        LecturerReqTO lecturerReqTO = new LecturerReqTO("Kasun sampath", "Lecturer", "Msc in IT", LecturerType.FULL_TIME, 5, null, "http://www.linkedin.com/wishvanath");
        Lecturer lecturer = transformer.toLecturer(lecturerReqTO);

        assertEquals(lecturerReqTO.getName(),lecturer.getName());
        assertEquals(lecturerReqTO.getDesignation(),lecturer.getDesignation());
        assertEquals(lecturerReqTO.getQualifications(),lecturer.getQualifications());
        assertEquals(lecturerReqTO.getLinkedIn(),lecturer.getLinkedIn().getUrl());
        assertEquals(lecturerReqTO.getType(),lecturer.getType());
        assertNotNull(lecturer.getLinkedIn());
    }

    @Test
    void fromLecturer() {
        Lecturer lecturer = new Lecturer(10, "Dasun kanishka", "Professor", "Phd in AI",
                LecturerType.VISITING, 2,null,new LinkedIn(null,"http://www.linkendin.com/dasun"));
        LecturerTO lecturerTO = transformer.fromLecturer(lecturer);
        System.out.println(lecturerTO);
    }
}