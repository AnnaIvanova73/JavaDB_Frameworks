package entities;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    private LocalDateTime date;
    private String comments;
    private Patient patients;

    public Visitation() {
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    public  Patient  getPatients() {
        return patients;
    }

    public void setPatients(Patient patients) {
        this.patients = patients;
    }

    @Column(name = "date_of_visitation",nullable = false)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Column(name = "comments",columnDefinition = "TEXT")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
