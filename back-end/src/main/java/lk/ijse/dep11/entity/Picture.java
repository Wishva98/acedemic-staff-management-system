package lk.ijse.dep11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "picture")
public class Picture implements SuperEntity {
    @Id
    @OneToOne
    @JoinColumn(name = "lecturer_id",referencedColumnName = "id")
    private Lecturer lecturer;

    @Column(name = "picture_url",nullable = false,length = 300)
    private String pictureUrl;
}
