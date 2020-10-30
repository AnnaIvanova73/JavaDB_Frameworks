package course.springdata.hibernateintro;

import course.springdata.hibernateintro.entity.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        Student student = new Student("Hristo Georgiev");
        session.beginTransaction();
        session.save(student);// --> save is native for hibernate
        session.getTransaction().commit();

        //Read Entity By Id 0.0
        session.beginTransaction();
        Student result = session.get(Student.class, 1L);
        session.getTransaction().commit();
        System.out.printf("Student with ID:%d -> %s", result.getId(), result.getName());

        //Read Entity By Id 0.1
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
        //FlushMode.MANUAL without flush use case read only, faster perform.
        result = session.get(Student.class, 1L, LockMode.READ);
        //LockMode share between readers
        session.getTransaction().commit();
        System.out.printf("Student with ID:%d -> %s", result.getId(), result.getName());

        //Close session
        session.close();
    }
}
