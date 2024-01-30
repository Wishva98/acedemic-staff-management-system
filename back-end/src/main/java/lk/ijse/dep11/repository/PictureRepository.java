package lk.ijse.dep11.repository;

import lk.ijse.dep11.entity.Lecturer;
import lk.ijse.dep11.entity.LinkedIn;
import lk.ijse.dep11.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
