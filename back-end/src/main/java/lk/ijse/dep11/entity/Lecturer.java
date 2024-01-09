package lk.ijse.dep11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecturer")
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 300,nullable = false)
    private String name;

    @Column(length = 600,nullable = false)
    private String designation;

    @Column(length = 600,nullable = false)
    private String qualifications;


}
