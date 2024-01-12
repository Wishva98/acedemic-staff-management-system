package lk.ijse.dep11.entity;

import lk.ijse.dep11.util.LecturerType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecturer")
public class Lecturer implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 300,nullable = false)
    private String name;

    @Column(length = 600,nullable = false)
    private String designation;

    @Column(length = 600,nullable = false)
    private String qualifications;

    @Column(nullable = false,columnDefinition = "ENUM('FULL_TIME','VISITING')")
    @Enumerated(EnumType.STRING)
    private LecturerType type;

    @Column(nullable = false)
    private Integer displayOrder;

    @ToString.Exclude
    @OneToOne(mappedBy = "lecturer")
    private Picture picture;

    @ToString.Exclude
    @OneToOne(mappedBy = "lecturer")
    private LinkedIn linkedIn;

    public Lecturer(String name, String designation, String qualifications, LecturerType type, Integer displayOrder) {
        this.name = name;
        this.designation = designation;
        this.qualifications = qualifications;
        this.type = type;
        this.displayOrder = displayOrder;
    }

    public Lecturer(Integer id, String name, String designation, String qualifications, LecturerType type, Integer displayOrder) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.qualifications = qualifications;
        this.type = type;
        this.displayOrder = displayOrder;
    }

    public void setPictureUrl(Picture picture) {
        if (picture != null) picture.setLecturer(this);
        this.picture = picture;
    }

    public void setLinkedIn(LinkedIn linkedIn) {
        if (linkedIn != null) linkedIn.setLecturer(this);
        this.linkedIn = linkedIn;
    }
}
