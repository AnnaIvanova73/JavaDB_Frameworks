package course.springdata.hibernateintro;

import course.springdata.hibernateintro.entity.Student;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class HibernateIntroMain {
    public static void main(String[] args) {

        //Create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure(); // от org.hibernate.cfg.Configuration;


        //Create SessionFactory

        SessionFactory sf = cfg.buildSessionFactory();


        //Create Hibernate Session --> session as set of objects that session take care of.
        Session session = sf.openSession();// will be open and close,
        // can use get current session, already created session still not close for example we want use another method with session


        //Persist an Entity
        Student student = new Student("Jon Doe");
        session.beginTransaction();
        session.save(student);// --> save is native for hibernate
        session.getTransaction().commit();

        //Read Entity By Id 0.0
        session.beginTransaction();
        Student result = session.get(Student.class, 1L);
        session.getTransaction().commit();
        System.out.printf("Student with ID:%d -> %s%n", result.getId(), result.getName());


        //Read Entity By Id 1
        getStudentByIdV1(session);


        //Read Entity By Id 2
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
        //FlushMode.MANUAL without flush use case read only, faster perform.

        //Student result2 = session.get(Student.class, 1L, LockMode.READ);
        //LockMode share between readers

        // Student result3 = session.byId(Student.class).getReference(1);

        result = session.byId(Student.class).load(1L); // return null

        session.getTransaction().commit();
        System.out.printf("Student with ID:%d -> %s%n", result.getId(), result.getName());


        //List Of Students Using HQL with stream
        printListOfStudentsWithStream(session);



        //QuerySelectByName
        session.beginTransaction();
        session.createQuery("FROM Student WHERE name = ?1", Student.class)
                .setParameter(1,"Jon Doe")
                .stream().forEach(System.out::println);
        session.getTransaction().commit();




        //Type-safe criteria queries
        System.out.println();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();//builder
        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);//query
        Root<Student> rootStudent = query.from(Student.class);// from student entity
        CriteriaQuery<Student> criteriaQueryFromRootStudent = query.select(rootStudent).where(criteriaBuilder.like(rootStudent.get("name"), "J"));
        session.createQuery(criteriaQueryFromRootStudent).getResultStream()
                .forEach(System.out::println);






        //Close session
        session.close();
    }





    private static void printListOfStudentsWithStream(Session session) {
        session.beginTransaction();
        session.createQuery("FROM Student ", Student.class)
                .setFirstResult(5)
                .setMaxResults(10)
                .stream().forEach(System.out::println);
        session.getTransaction().commit();
        System.out.println();
    }

    private static void getStudentByIdV1(Session session) {
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
        long queryId = 100L;
        Optional<Student> result5 = session.byId(Student.class).loadOptional(queryId);
        session.getTransaction().commit();
        System.out.printf("Student: %s%n", result5.orElseGet(() -> new Student("Unknwon")));
        // result5.isPresent if/else
    }
}
