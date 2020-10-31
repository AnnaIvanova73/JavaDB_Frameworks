import core.EngineImpl;
import core.interfaces.Engine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        Engine engine = new EngineImpl();
        engine.run();
    }
}


