package lk.ijse.dep11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linkedin")
public class LinkedIn implements SuperEntity {

    @Id
    @Column(name = "lecturer_id")
    private Integer lecturerId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "lecturer_id",referencedColumnName = "id")
    private Lecturer lecturer;

    @Column(name = "linkeIn",nullable = false,length = 2000)
    private String url;

    public LinkedIn(Lecturer lecturer, String url) {
        this.lecturer = lecturer;
        this.url = url;
    }
}
