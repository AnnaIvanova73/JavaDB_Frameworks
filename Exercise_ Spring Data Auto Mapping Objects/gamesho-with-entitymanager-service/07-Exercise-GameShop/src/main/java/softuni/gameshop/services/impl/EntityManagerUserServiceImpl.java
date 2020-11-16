package softuni.gameshop.services.impl;

import org.springframework.stereotype.Component;
import softuni.gameshop.domain.entities.User;
import softuni.gameshop.services.EntityManagerUserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class EntityManagerUserServiceImpl implements EntityManagerUserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User insertWithTransaction(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User detachUser(User user) {
        entityManager.detach(user);
        return user;
    }

    @Transactional
    public User mergeUser(User user) {
        entityManager.merge(user);
        return user;
    }
}
