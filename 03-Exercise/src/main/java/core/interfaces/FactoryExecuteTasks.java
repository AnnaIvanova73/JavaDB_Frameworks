package core.interfaces;

import javax.persistence.EntityManager;

public interface FactoryExecuteTasks {
    void execute(int numberTask, EntityManager entityManager);
}
