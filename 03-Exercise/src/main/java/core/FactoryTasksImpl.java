package core;

import core.interfaces.FactoryExecuteTasks;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class FactoryTasksImpl implements FactoryExecuteTasks {


    @Override
    public void execute(int numberTask, EntityManager entityManager) {

        switch (numberTask){
            case 2 :
                List<Town> towns = entityManager
                        .createQuery("SELECT t FROM Town t ", Town.class)
                        .getResultList();
                System.out.println();
                break;
            case 3 :
                break;
            case 4 :
                break;
            case 5 :
                break;
            case 6 :
                break;
        }
    }
}
