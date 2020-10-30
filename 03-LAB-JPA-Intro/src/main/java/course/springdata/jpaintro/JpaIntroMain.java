package course.springdata.jpaintro;

import course.springdata.jpaintro.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JpaIntroMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school_jpa");
        EntityManager em = emf.createEntityManager();
        Student student = new Student("Georgi Pavlov");

        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();

    }
}
