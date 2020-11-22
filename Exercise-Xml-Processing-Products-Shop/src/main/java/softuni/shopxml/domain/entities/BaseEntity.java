package softuni.shopxml.domain.entities;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {

    Long Id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
