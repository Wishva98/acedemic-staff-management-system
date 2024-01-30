package lk.ijse.dep11.repository;

import lk.ijse.dep11.entity.Lecturer;
import lk.ijse.dep11.util.LecturerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer,Integer> {

    List<Lecturer> findLecturersByType(LecturerType lecturerType);

    @Query("SELECT l FROM Lecturer l WHERE l.type = lk.ijse.dep11.util.LecturerType.FULL_TIME")
    List<Lecturer> findFullTimeLecturers();

    @Query("SELECT l FROM Lecturer l WHERE l.type = lk.ijse.dep11.util.LecturerType.VISITING")
    List<Lecturer> findVisitingLecturers();
}
