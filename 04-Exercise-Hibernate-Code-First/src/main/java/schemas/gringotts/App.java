package schemas.gringotts;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_gringotts_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EngineImpl engine = new EngineImpl(entityManager);
        engine.run();

    }
}
