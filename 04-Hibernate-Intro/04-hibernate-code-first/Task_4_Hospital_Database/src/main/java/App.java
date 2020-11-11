import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Pu_hospital_db");
        EntityManager entityManager = emf.createEntityManager();

        Engine engine = new Engine(entityManager);

    }
}
