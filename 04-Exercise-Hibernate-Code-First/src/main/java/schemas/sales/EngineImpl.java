package schemas.sales;
import interfaces.*;

import javax.persistence.EntityManager;

public class EngineImpl implements EngineInt {

    private final EntityManager entityManager;

    public EngineImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {

    }
}
