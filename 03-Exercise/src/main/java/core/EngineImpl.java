package core;

import core.interfaces.Engine;

import javax.persistence.EntityManager;

public class EngineImpl implements Engine {

    private final EntityManager entityManager;

    public EngineImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {

    }
}
