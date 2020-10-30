package course.springdata.jpaintro;

import course.springdata.jpaintro.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JpaIntroMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school_jpa");
        EntityManager entityManager = emf.createEntityManager();
        Student student = new Student("Georgi Pavlov");

        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.flush();
        entityManager.getTransaction().commit();



        entityManager.getTransaction().begin();
        Student found = entityManager.find(Student.class, 1L);
        System.out.printf("Found student: %s",found);
        entityManager.getTransaction().commit();


        entityManager.getTransaction().begin();
        entityManager.createQuery("SELECT s FROM Student s WHERE s.name LIKE :name ORDER BY s.name")
                .setParameter("name", "%")
                .getResultList().forEach(System.out::println);
        entityManager.getTransaction().commit();


        entityManager.getTransaction().begin();
//        em.remove(found);
        entityManager.detach(found);
        found.setName("Atanas Petrov");
        Student managedEntity = entityManager.merge(found);
//        System.out.printf("Same reference: %b", managedEntity == found);
        System.out.printf("!!!  Given student identity: %s, Returned from merge student identity: %s\n",
                Integer.toHexString(System.identityHashCode(found)),
                Integer.toHexString(System.identityHashCode(managedEntity)));
//        Student removed = em.find(Student.class, 1L);
//        System.out.printf("Removed entity: %s",removed );
        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
