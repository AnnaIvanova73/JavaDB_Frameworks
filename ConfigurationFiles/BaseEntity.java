package softuni.exam.models.entities;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {

    Integer id;

    public BaseEntity() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
