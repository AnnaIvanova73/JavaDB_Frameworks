package softuni.gameshop.services;

import softuni.gameshop.domain.entities.User;

public interface EntityManagerUserService {
    public User detachUser(User user);
    public User mergeUser(User user);
    User insertWithTransaction(User user);
}
