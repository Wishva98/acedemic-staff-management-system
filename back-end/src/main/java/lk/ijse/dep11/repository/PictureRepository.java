package lk.ijse.dep11.repository;

import lk.ijse.dep11.entity.LinkedIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<LinkedIn,Integer> {
}
