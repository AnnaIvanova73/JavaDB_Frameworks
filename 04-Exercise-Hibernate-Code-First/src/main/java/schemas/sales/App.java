package schemas.sales;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        /* XML --> replace in resources.META-INF.persistence.xml

           <persistence-unit name="PU_sales_db">
                <properties>
                    <property name="hibernate.connection.url"
          value="jdbc:mysql://localhost:3306/sales_db?createDatabaseIfNotExist=true&amp;useSSL=false"/> */

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_sales_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EngineImpl engine = new EngineImpl(entityManager);
        engine.run();

        entityManager.close();
        entityManagerFactory.close();

    }
}
