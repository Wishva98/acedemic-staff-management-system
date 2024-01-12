package lk.ijse.dep11.entity;

import lk.ijse.dep11.to.LecturerTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linkedin")
public class LinkedIn implements SuperEntity {

    @Id
    @OneToOne
    @JoinColumn(name = "lecturer_id",referencedColumnName = "id")
    private Lecturer lecturer;

    @Column(name = "linkeIn",nullable = false,length = 2000)
    private String linkedIn;
}
